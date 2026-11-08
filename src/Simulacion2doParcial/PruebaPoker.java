package Simulacion2doParcial;

import javax.swing.JOptionPane;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PruebaPoker {
    public static void ejecutar(ArrayList<Double> datos) {
        if (datos == null || datos.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No hay datos para realizar la prueba.");
            return;
        }

        int decimales = elegirDecimales();
        if (decimales == -1) {
            return;
        }

        int nivelConfianza = elegirConfianza();
        if (nivelConfianza == -1) {
            return;
        }

        double[] probabilidades = obtenerProbabilidades(decimales);
        String[] categorias = obtenerCategorias(decimales);
        int[] observados = new int[categorias.length];
        DecimalFormat formatoNumero = new DecimalFormat("0.000");
        if (decimales == 4) {
            formatoNumero = new DecimalFormat("0.0000");
        }
        if (decimales == 5) {
            formatoNumero = new DecimalFormat("0.00000");
        }

        StringBuilder detalle = new StringBuilder();

        for (double numero : datos) {
            if (numero < 0 || numero >= 1) {
                JOptionPane.showMessageDialog(null,
                        "Todos los datos deben estar entre 0 y 1.");
                return;
            }

            String texto = formatoNumero.format(numero);
            String digitos = texto.substring(2);
            int categoria = obtenerCategoria(digitos, decimales);
            observados[categoria]++;
            detalle.append(texto).append(" = ")
                    .append(categorias[categoria]).append("\n");
        }

        int n = datos.size();
        double chiCalculada = 0;
        DecimalFormat formato = new DecimalFormat("0.000");
        StringBuilder resultado = new StringBuilder();

        resultado.append("PRUEBA DE POKER\n\n");
        resultado.append("Cantidad de decimales: ").append(decimales);
        resultado.append("\nNivel de confianza: ")
                .append(confianzaTexto(nivelConfianza));
        resultado.append("\n\nClasificación de los datos:\n")
                .append(detalle);
        resultado.append("\nCategoría                Oi       Ei       Chi cuadrada\n");

        for (int i = 0; i < categorias.length; i++) {
            double esperado = probabilidades[i] * n;
            double diferencia = esperado - observados[i];
            double termino = diferencia * diferencia / esperado;
            chiCalculada += termino;

            resultado.append(categorias[i]).append("\t")
                    .append(observados[i]).append("\t")
                    .append(formato.format(esperado)).append("\t")
                    .append(formato.format(termino)).append("\n");
        }

        int gradosLibertad = categorias.length - 1;
        double chiTabla = obtenerChiTabla(gradosLibertad, nivelConfianza);
        if (chiTabla == -1) {
            JOptionPane.showMessageDialog(null,
                    "No existe una fila para " + gradosLibertad
                            + " grados de libertad.");
            return;
        }
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
                "Prueba de Poker", JOptionPane.INFORMATION_MESSAGE);
    }

    public static int elegirDecimales() {
        String texto = JOptionPane.showInputDialog(null,
                "Seleccione la cantidad de decimales:"
                        + "\n1: 3 decimales"
                        + "\n2: 4 decimales"
                        + "\n3: 5 decimales");
        if (texto == null) {
            return -1;
        }

        try {
            int opcion = Integer.parseInt(texto.trim());
            if (opcion == 1) {
                return 3;
            }
            if (opcion == 2) {
                return 4;
            }
            if (opcion == 3) {
                return 5;
            }
        } catch (NumberFormatException ignored) {
        }

        JOptionPane.showMessageDialog(null,
                "Seleccione una opción entre 1 y 3.");
        return -1;
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
        } catch (NumberFormatException ignored) {
        }

        JOptionPane.showMessageDialog(null,
                "Seleccione una opción entre 1 y 4.");
        return -1;
    }

    public static int obtenerCategoria(String digitos, int decimales) {
        int[] cantidades = new int[10];
        for (int i = 0; i < digitos.length(); i++) {
            int digito = digitos.charAt(i) - '0';
            cantidades[digito]++;
        }

        int pares = 0;
        int mayorCantidad = 0;
        for (int cantidad : cantidades) {
            if (cantidad == 2) {
                pares++;
            }
            if (cantidad > mayorCantidad) {
                mayorCantidad = cantidad;
            }
        }

        if (mayorCantidad == 1) {
            return 0;
        }
        if (mayorCantidad == 2 && pares == 1) {
            return 1;
        }
        if (mayorCantidad == 2 && pares == 2) {
            return 2;
        }
        if (mayorCantidad == 3 && pares == 0) {
            if (decimales == 3) {
                return 2;
            }
            return 3;
        }
        if (mayorCantidad == 3 && pares == 1) {
            return 4;
        }
        if (mayorCantidad == 4) {
            if (decimales == 4) {
                return 4;
            }
            return 5;
        }
        if (decimales == 4) {
            return 4;
        }
        return 6;
    }

    public static String[] obtenerCategorias(int decimales) {
        if (decimales == 3) {
            return new String[]{"Todos diferentes", "Un par", "Tercia"};
        }
        if (decimales == 4) {
            return new String[]{"Todos diferentes", "Un par", "Dos pares",
                    "Tercia", "Poker"};
        }
        return new String[]{"Todos diferentes", "Un par", "Dos pares",
                "Tercia", "Tercia y par", "Poker", "Quintilla"};
    }

    public static double[] obtenerProbabilidades(int decimales) {
        if (decimales == 3) {
            return new double[]{0.72, 0.27, 0.01};
        }
        if (decimales == 4) {
            return new double[]{0.5040, 0.4320, 0.0270, 0.0360, 0.0016};
        }
        if (decimales == 5) {
            return new double[]{0.3024, 0.5040, 0.1080, 0.0720,
                    0.0090, 0.0045, 0.0001};
        }
        return new double[]{0.3024, 0.5040, 0.1080, 0.0720,
                0.0090, 0.0045, 0.0001};
    }

    public static double obtenerChiTabla(int gradosLibertad, int confianza) {
        if (gradosLibertad == 2) {
            double[] valores = {3.219, 4.605, 5.991, 9.210};
            return valores[confianza];
        }
        if (gradosLibertad == 4) {
            double[] valores = {5.989, 7.779, 9.488, 13.277};
            return valores[confianza];
        }
        if (gradosLibertad == 6) {
            double[] valores = {8.558, 10.645, 12.592, 16.812};
            return valores[confianza];
        }
        return -1;
    }

    public static String confianzaTexto(int confianza) {
        String[] valores = {"80%", "90%", "95%", "99%"};
        return valores[confianza];

    }
}
