package Simulacion2doParcial;

import javax.swing.JOptionPane;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PruebaArribaAbajoMedia {
    public static void ejecutar(ArrayList<Double> datos) {
        if (datos == null || datos.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No hay datos para realizar la prueba.");
            return;
        }

        ArrayList<Double> datosValidos = new ArrayList<>();
        for (double numero : datos) {
            if (numero < 0 || numero > 1) {
                JOptionPane.showMessageDialog(null,
                        "Todos los datos deben estar entre 0 y 1.");
                return;
            }
            if (numero != 0.5) {
                datosValidos.add(numero);
            }
        }

        if (datosValidos.size() < 3) {
            JOptionPane.showMessageDialog(null,
                    "Se necesitan al menos 3 datos diferentes de 0.5.");
            return;
        }

        StringBuilder secuencia = new StringBuilder();
        int arriba = 0;
        int abajo = 0;

        for (double numero : datosValidos) {
            if (numero > 0.5) {
                secuencia.append("1");
                arriba++;
            } else {
                secuencia.append("0");
                abajo++;
            }
        }

        if (arriba == 0 || abajo == 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe existir al menos un número arriba y uno abajo de 0.5.");
            return;
        }

        int n = arriba + abajo;
        int corridas = 1;
        for (int i = 1; i < secuencia.length(); i++) {
            if (secuencia.charAt(i) != secuencia.charAt(i - 1)) {
                corridas++;
            }
        }

        double mediaCorridas = (2.0 * arriba * abajo) / n + 0.5;
        double varianzaCorridas =
                (2.0 * arriba * abajo * (2.0 * arriba * abajo - n))
                        / (n * n * (n - 1));
        double desviacionCorridas = Math.sqrt(varianzaCorridas);
        double zCalculada = (corridas - mediaCorridas) / desviacionCorridas;

        int confianza = elegirConfianza();
        if (confianza == -1) {
            return;
        }

        double zTabla = obtenerZ(confianza);
        DecimalFormat formato = new DecimalFormat("0.000");
        StringBuilder resultado = new StringBuilder();

        resultado.append("PRUEBA DE CORRIDAS ARRIBA Y ABAJO DE LA MEDIA\n\n");
        resultado.append("Media de referencia = 0.500\n");
        resultado.append("1 = ri mayor que 0.5\n");
        resultado.append("0 = ri menor que 0.5\n");
        resultado.append("Los valores ri = 0.5 se eliminan.\n\n");
        resultado.append("Datos originales:\n");
        for (double numero : datos) {
            resultado.append(formato.format(numero)).append(" ");
        }
        resultado.append("\nDatos utilizados:\n");
        for (double numero : datosValidos) {
            resultado.append(formato.format(numero)).append(" ");
        }
        resultado.append("\nSecuencia: ").append(secuencia);
        resultado.append("\n\nN1 (arriba) = ").append(arriba);
        resultado.append("\nN0 (abajo) = ").append(abajo);
        resultado.append("\nCo (corridas) = ").append(corridas);
        resultado.append("\nMco = ").append(formato.format(mediaCorridas));
        resultado.append("\nVarianza de Co = ").append(formato.format(varianzaCorridas));
        resultado.append("\nZo = ").append(formato.format(zCalculada));
        resultado.append("\nNivel de confianza = ").append(confianzaTexto(confianza));
        resultado.append("\n-Z alfa/2 = -").append(formato.format(zTabla));
        resultado.append("\nZ alfa/2 = ").append(formato.format(zTabla));
        resultado.append("\n\nH0: los números son independientes.");

        if (zCalculada >= -zTabla && zCalculada <= zTabla) {
            resultado.append("\nNo se rechaza H0.");
        } else {
            resultado.append("\nSe rechaza H0.");
        }

        JOptionPane.showMessageDialog(null, resultado.toString(),
                "Corridas arriba y abajo de la media",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static int elegirConfianza() {
        String texto = JOptionPane.showInputDialog(null,
                "Seleccione el nivel de confianza:"
                        + "\n1: 80%"
                        + "\n2: 85%"
                        + "\n3: 90%"
                        + "\n4: 95%"
                        + "\n5: 99%");
        if (texto == null) {
            return -1;
        }

        try {
            int opcion = Integer.parseInt(texto.trim());
            if (opcion >= 1 && opcion <= 5) {
                return opcion - 1;
            }
        } catch (NumberFormatException ignored) {
        }

        JOptionPane.showMessageDialog(null,
                "Seleccione una opción entre 1 y 5.");
        return -1;
    }

    public static double obtenerZ(int confianza) {
        double[] valores = {1.282, 1.440, 1.645, 1.960, 2.576};
        return valores[confianza];
    }

    public static String confianzaTexto(int confianza) {
        String[] valores = {"80%", "85%", "90%", "95%", "99%"};
        return valores[confianza];
    }
}
