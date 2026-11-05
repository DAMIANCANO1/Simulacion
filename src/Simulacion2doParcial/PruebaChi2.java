package Simulacion2doParcial;

import javax.swing.JOptionPane;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PruebaChi2 {
    private static final int[] GRADOS_LIBERTAD = {
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
            21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
            40, 50, 60, 70, 80, 90, 100
    };

    // Columnas: alfa 0.10, 0.05, 0.025, 0.01, 0.005 y 0.001.
    private static final double[][] TABLA = {
            {2.706, 3.841, 5.024, 6.635, 7.879, 10.828},
            {4.605, 5.991, 7.378, 9.210, 10.597, 13.816},
            {6.251, 7.815, 9.348, 11.345, 12.838, 16.266},
            {7.779, 9.488, 11.143, 13.277, 14.860, 18.467},
            {9.236, 11.070, 12.833, 15.086, 16.750, 20.515},
            {10.645, 12.592, 14.449, 16.812, 18.548, 22.458},
            {12.017, 14.067, 16.013, 18.475, 20.278, 24.322},
            {13.362, 15.507, 17.535, 20.090, 21.955, 26.124},
            {14.684, 16.919, 19.023, 21.666, 23.589, 27.877},
            {15.987, 18.307, 20.483, 23.209, 25.188, 29.588},
            {17.275, 19.675, 21.920, 24.725, 26.757, 31.264},
            {18.549, 21.026, 23.337, 26.217, 28.300, 32.909},
            {19.812, 22.362, 24.736, 27.688, 29.819, 34.528},
            {21.064, 23.685, 26.119, 29.141, 31.319, 36.123},
            {22.307, 24.996, 27.488, 30.578, 32.801, 37.697},
            {23.542, 26.296, 28.845, 32.000, 34.267, 39.252},
            {24.769, 27.587, 30.191, 33.409, 35.718, 40.790},
            {25.989, 28.869, 31.526, 34.805, 37.156, 42.312},
            {27.204, 30.144, 32.852, 36.191, 38.582, 43.820},
            {28.412, 31.410, 34.170, 37.566, 39.997, 45.315},
            {29.615, 32.671, 35.479, 38.932, 41.401, 46.797},
            {30.813, 33.924, 36.781, 40.289, 42.796, 48.268},
            {32.007, 35.172, 38.076, 41.638, 44.181, 49.728},
            {33.196, 36.415, 39.364, 42.980, 45.559, 51.179},
            {34.382, 37.652, 40.646, 44.314, 46.928, 52.620},
            {35.563, 38.885, 41.923, 45.642, 48.290, 54.052},
            {36.741, 40.113, 43.195, 46.963, 49.645, 55.476},
            {37.916, 41.337, 44.461, 48.278, 50.993, 56.892},
            {39.087, 42.557, 45.722, 49.588, 52.336, 58.301},
            {40.256, 43.773, 46.979, 50.892, 53.672, 59.703},
            {51.805, 55.758, 59.342, 63.691, 66.766, 73.402},
            {63.167, 67.505, 71.420, 76.154, 79.490, 86.661},
            {74.397, 79.082, 83.998, 88.379, 91.952, 99.607},
            {85.527, 90.531, 95.023, 100.425, 104.215, 112.317},
            {96.578, 101.879, 106.629, 112.325, 116.321, 124.839},
            {107.565, 113.145, 118.136, 124.116, 128.299, 137.708},
            {118.498, 124.342, 129.561, 135.807, 140.169, 149.449}
    };

    public static void ejecutar(ArrayList<Double> datos) {
        if (datos == null || datos.size() < 2) {
            JOptionPane.showMessageDialog(null, "Se necesitan al menos 2 datos.");
            return;
        }

        int n = datos.size();
        int intervalos = (int) Math.sqrt(n);
        if (intervalos < 2) {
            intervalos = 2;
        }

        int gradosLibertad = intervalos - 1;
        int fila = buscarFila(gradosLibertad);
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "No hay tabla para esos grados de libertad.");
            return;
        }

        int columna = elegirNivel();
        if (columna == -1) {
            return;
        }

        int[] observados = new int[intervalos];
        for (double numero : datos) {
            if (numero < 0 || numero > 1) {
                JOptionPane.showMessageDialog(null, "Los datos deben estar entre 0 y 1.");
                return;
            }

            int intervalo = (int) (numero * intervalos);
            if (intervalo == intervalos) {
                intervalo = intervalos - 1;
            }
            observados[intervalo]++;
        }

        double esperados = (double) n / intervalos;
        double chiCalculada = 0;
        DecimalFormat formato = new DecimalFormat("0.000");
        StringBuilder tabla = new StringBuilder();
        tabla.append("PRUEBA CHI-CUADRADA DE UNIFORMIDAD\n\n");
        tabla.append("Intervalo\tOi\tG = n/m\t(Ei - Oi)^2 / Ei\n");
        tabla.append("------------------------------------------------------------\n");

        for (int i = 0; i < intervalos; i++) {
            double inicio = (double) i / intervalos;
            double fin = (double) (i + 1) / intervalos;
            double diferencia = observados[i] - esperados;
            double termino = diferencia * diferencia / esperados;
            chiCalculada += termino;

            String intervaloTexto = "[" + formato.format(inicio)
                    + " - " + formato.format(fin) + "]";
            tabla.append(intervaloTexto).append("\t")
                    .append(observados[i]).append("\t")
                    .append(formato.format(esperados)).append("\t")
                    .append(formato.format(termino)).append("\n");
        }

        double chiTabla = TABLA[fila][columna];
        tabla.append("\nDatos de la prueba\n")
                .append("n = ").append(n)
                .append("\nm = √n = ").append(intervalos)
                .append("\nG = n/m = ").append(formato.format(esperados))
                .append("\nGrados de libertad = m - 1 = ").append(gradosLibertad)
                .append("\nNivel de confianza = ").append(confianzaTexto(columna))
                .append("\nχ² calculada = ").append(formato.format(chiCalculada))
                .append("\nχ² de tabla = ").append(formato.format(chiTabla))
                .append("\n\nH0: los números son uniformes.");

        if (chiCalculada < chiTabla) {
            tabla.append("\nNo se rechaza H0.");
        } else {
            tabla.append("\nSe rechaza H0.");
        }

        JOptionPane.showMessageDialog(null, tabla.toString(),
                "Prueba Chi2", JOptionPane.INFORMATION_MESSAGE);
    }

    public static int elegirNivel() {
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
                // Para una prueba de dos colas se usa alfa/2 en la tabla.
                if (opcion == 1) {
                    return 0; // 80%: alfa/2 = 0.10
                }
                if (opcion == 2) {
                    return 1; // 90%: alfa/2 = 0.05
                }
                if (opcion == 3) {
                    return 2; // 95%: alfa/2 = 0.025
                }
                return 4; // 99%: alfa/2 = 0.005
            }
        } catch (NumberFormatException ignored) {
        }

        JOptionPane.showMessageDialog(null, "Seleccione una opción entre 1 y 4.");
        return -1;
    }

    public static int buscarFila(int gradosLibertad) {
        for (int i = 0; i < GRADOS_LIBERTAD.length; i++) {
            if (GRADOS_LIBERTAD[i] == gradosLibertad) {
                return i;
            }
        }
        return -1;
    }

    public static String confianzaTexto(int columna) {
        if (columna == 0) {
            return "80%";
        }
        if (columna == 1) {
            return "90%";
        }
        if (columna == 2) {
            return "95%";
        }
        return "99%";
    }
}
