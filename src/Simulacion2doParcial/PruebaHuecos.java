package Simulacion2doParcial;

import javax.swing.JOptionPane;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PruebaHuecos {
    public static void ejecutar(ArrayList<Double> datos) {
        if (datos == null || datos.size() < 3) {
            JOptionPane.showMessageDialog(null,
                    "Se necesitan al menos 3 datos para realizar la prueba.");
            return;
        }

        double alfa = pedirNumero("Ingrese el límite inferior del intervalo (alfa):");
        if (alfa == -1) {
            return;
        }

        double beta = pedirNumero("Ingrese el límite superior del intervalo (beta):");
        if (beta == -1) {
            return;
        }

        if (alfa < 0 || beta > 1 || alfa >= beta) {
            JOptionPane.showMessageDialog(null,
                    "El intervalo debe cumplir 0 <= alfa < beta <= 1.");
            return;
        }

        int confianza = elegirConfianza();
        if (confianza == -1) {
            return;
        }

        double probabilidad = beta - alfa;
        ArrayList<Integer> secuencia = new ArrayList<>();

        for (double numero : datos) {
            if (numero < 0 || numero > 1) {
                JOptionPane.showMessageDialog(null,
                        "Todos los datos deben estar entre 0 y 1.");
                return;
            }

            if (numero > alfa && numero < beta) {
                secuencia.add(1);
            } else {
                secuencia.add(0);
            }
        }

        ArrayList<Integer> huecos = obtenerHuecos(secuencia);
        if (huecos.size() == 0) {
            JOptionPane.showMessageDialog(null,
                    "No se encontraron dos números dentro del intervalo.");
            return;
        }

        int[] observados = new int[6];

        for (int hueco : huecos) {
            if (hueco >= 5) {
                observados[5]++;
            } else {
                observados[hueco]++;
            }
        }

        int cantidadHuecos = huecos.size();
        double chiCalculada = 0;
        double[] esperados = new double[observados.length];
        DecimalFormat formato = new DecimalFormat("0.000");
        StringBuilder resultado = new StringBuilder();

        resultado.append("PRUEBA DE HUECOS\n\n");
        resultado.append("Intervalo: (").append(formato.format(alfa))
                .append(", ").append(formato.format(beta)).append(")\n");
        resultado.append("Secuencia: ");
        for (int numero : secuencia) {
            resultado.append(numero);
        }
        resultado.append("\n\nHuecos encontrados: ").append(cantidadHuecos);
        resultado.append("\n\nTamaño\tOi\tEi\tChi cuadrada\n");

        for (int i = 0; i < observados.length; i++) {
            esperados[i] = cantidadHuecos * probabilidad;
            double probabilidadCero = 1 - probabilidad;
            int veces = i;
            if (i == 5) {
                veces = 5;
                esperados[i] = cantidadHuecos;
            }
            for (int j = 0; j < veces; j++) {
                esperados[i] = esperados[i] * probabilidadCero;
            }
            double diferencia = observados[i] - esperados[i];
            double termino = diferencia * diferencia / esperados[i];
            chiCalculada += termino;

            if (i < 5) {
                resultado.append(i);
            } else {
                resultado.append("5 o más");
            }
            resultado.append("\t")
                    .append(observados[i]).append("\t")
                    .append(formato.format(esperados[i])).append("\t")
                    .append(formato.format(termino)).append("\n");
        }

        int gradosLibertad = observados.length - 1;
        int fila = PruebaChi2.buscarFila(gradosLibertad);
        if (fila == -1 || gradosLibertad > 10) {
            JOptionPane.showMessageDialog(null,
                    "No existe una fila para " + gradosLibertad
                            + " grados de libertad.");
            return;
        }

        double chiTabla = obtenerChiTabla(fila, confianza);
        resultado.append("\nGrados de libertad = ").append(gradosLibertad);
        resultado.append("\nChi cuadrada calculada = ")
                .append(formato.format(chiCalculada));
        resultado.append("\nChi cuadrada de tabla = ")
                .append(formato.format(chiTabla));
        resultado.append("\n\nH0: los números son independientes.");

        if (chiCalculada < chiTabla) {
            resultado.append("\nNo se rechaza H0.");
        } else {
            resultado.append("\nSe rechaza H0.");
        }

        JOptionPane.showMessageDialog(null, resultado.toString(),
                "Prueba de huecos", JOptionPane.INFORMATION_MESSAGE);
    }

    public static double pedirNumero(String mensaje) {
        String texto = JOptionPane.showInputDialog(null, mensaje);
        if (texto == null) {
            return -1;
        }

        try {
            return Double.parseDouble(texto.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un número válido.");
            return -1;
        }
    }

    public static ArrayList<Integer> obtenerHuecos(ArrayList<Integer> secuencia) {
        ArrayList<Integer> huecos = new ArrayList<>();
        int posicionAnterior = -1;

        for (int i = 0; i < secuencia.size(); i++) {
            if (secuencia.get(i) == 1) {
                if (posicionAnterior != -1) {
                    int cantidadCeros = i - posicionAnterior - 1;
                    huecos.add(cantidadCeros);
                }
                posicionAnterior = i;
            }
        }

        return huecos;
    }

    public static int elegirConfianza() {
        String texto = JOptionPane.showInputDialog(null,
                "Seleccione el nivel de confianza:"
                        + "\n1: 80%"
                        + "\n2: 90%"
                        + "\n3: 95%"
                        + "\n4: 99%");
        if (texto == null) {
            return -1;
        }

        try {
            int opcion = Integer.parseInt(texto.trim());
            if (opcion >= 1 && opcion <= 4) {
                return opcion - 1;
            }
        } catch (NumberFormatException e) {
        }

        JOptionPane.showMessageDialog(null,
                "Seleccione una opción entre 1 y 4.");
        return -1;
    }

    public static double obtenerChiTabla(int fila, int confianza) {
        double[] valores80 = {1.642, 3.219, 4.642, 5.989, 7.289,
                8.558, 9.803, 11.030, 12.242, 13.442};
        double[] valores90 = {2.706, 4.605, 6.251, 7.779, 9.236,
                10.645, 12.017, 13.362, 14.684, 15.987};
        double[] valores95 = {3.841, 5.991, 7.815, 9.488, 11.070,
                12.592, 14.067, 15.507, 16.919, 18.307};
        double[] valores99 = {6.635, 9.210, 11.345, 13.277, 15.086,
                16.812, 18.475, 20.090, 21.666, 23.209};

        if (confianza == 0) {
            return valores80[fila];
        }
        if (confianza == 1) {
            return valores90[fila];
        }
        if (confianza == 2) {
            return valores95[fila];
        }
        return valores99[fila];
    }
}
