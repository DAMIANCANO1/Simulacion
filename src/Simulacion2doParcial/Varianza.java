package Simulacion2doParcial;

import javax.swing.*;
import java.util.ArrayList;

public class Varianza {
    private static final int[] GRADOS_LIBERTAD = {
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
            21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
            40, 50, 60, 70, 80, 90, 100
    };

    private static final double[][] TABLA_SUPERIOR = {
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

    private static final double[][] TABLA_INFERIOR = {
            {0.016, 0.004, 0.001, 0.000, 0.000, 0.000},
            {0.211, 0.103, 0.051, 0.020, 0.010, 0.002},
            {0.584, 0.352, 0.216, 0.115, 0.072, 0.024},
            {1.064, 0.711, 0.484, 0.297, 0.207, 0.091},
            {1.610, 1.145, 0.831, 0.554, 0.412, 0.210},
            {2.204, 1.635, 1.237, 0.872, 0.676, 0.381},
            {2.833, 2.167, 1.690, 1.239, 0.989, 0.598},
            {3.490, 2.733, 2.180, 1.646, 1.344, 0.857},
            {4.168, 3.325, 2.700, 2.088, 1.735, 1.152},
            {4.865, 3.940, 3.247, 2.558, 2.156, 1.479},
            {5.578, 4.575, 3.816, 3.053, 2.603, 1.834},
            {6.304, 5.226, 4.404, 3.571, 3.074, 2.214},
            {7.042, 5.892, 5.009, 4.107, 3.565, 2.617},
            {7.790, 6.571, 5.629, 4.660, 4.075, 3.041},
            {8.547, 7.261, 6.262, 5.229, 4.601, 3.483},
            {9.312, 7.962, 6.908, 5.812, 5.142, 3.942},
            {10.085, 8.672, 7.564, 6.408, 5.697, 4.416},
            {10.865, 9.390, 8.231, 7.015, 6.265, 4.905},
            {11.651, 10.117, 8.907, 7.633, 6.844, 5.407},
            {12.443, 10.851, 9.591, 8.260, 7.434, 5.921},
            {13.240, 11.591, 10.283, 8.897, 8.034, 6.447},
            {14.041, 12.338, 10.982, 9.542, 8.643, 6.983},
            {14.848, 13.091, 11.689, 10.196, 9.260, 7.529},
            {15.659, 13.848, 12.401, 10.856, 9.886, 8.085},
            {16.473, 14.611, 13.120, 11.524, 10.520, 8.649},
            {17.292, 15.379, 13.844, 12.198, 11.160, 9.222},
            {18.114, 16.151, 14.573, 12.879, 11.808, 9.803},
            {18.939, 16.928, 15.308, 13.565, 12.461, 10.391},
            {19.768, 17.708, 16.047, 14.256, 13.121, 10.986},
            {20.599, 18.493, 16.791, 14.953, 13.787, 11.588},
            {29.051, 26.509, 24.433, 22.164, 20.707, 17.916},
            {37.689, 34.764, 32.357, 29.707, 27.991, 24.674},
            {46.459, 43.188, 40.482, 37.485, 35.534, 31.738},
            {55.329, 51.739, 48.758, 45.442, 43.275, 39.036},
            {64.278, 60.391, 57.153, 53.540, 51.172, 46.520},
            {73.291, 69.126, 65.647, 61.754, 59.196, 54.155},
            {82.358, 77.929, 74.222, 70.065, 67.328, 61.918}
    };

    public static void ejecutar(ArrayList<Double> datos) {
        if (datos == null || datos.size() < 2) {
            JOptionPane.showMessageDialog(null, "Se necesitan al menos 2 datos para realizar la prueba.");
            return;
        }

        double varianzaEsperada = 1.0 / 12.0;

        // Cantidad de datos y grados de libertad.
        int cantidadDatos = datos.size();
        int gradosLibertad = cantidadDatos - 1;
        int filaTabla = buscarFila(gradosLibertad);
        if (filaTabla == -1) {
            JOptionPane.showMessageDialog(null,
                    "La tabla proporcionada no tiene " + gradosLibertad
                            + " grados de libertad. Use una cantidad de datos que produzca "
                            + "1-30, 40, 50, 60, 70, 80, 90 o 100 grados de libertad.");
            return;
        }

        // Media = suma de los datos / cantidad de datos.
        double suma = 0.0;
        for (int i = 0; i < cantidadDatos; i++) {
            suma = suma + datos.get(i);
        }
        double media = suma / cantidadDatos;

        // Sumamos las diferencias al cuadrado.
        double sumaDiferencias = 0.0;
        for (int i = 0; i < cantidadDatos; i++) {
            double diferencia = datos.get(i) - media;
            double diferenciaAlCuadrado = diferencia * diferencia;
            sumaDiferencias = sumaDiferencias + diferenciaAlCuadrado;
        }

        // Varianza = suma de diferencias / (n - 1).
        double varianza = sumaDiferencias / gradosLibertad;

        // Fórmula de la prueba de varianza.
        double resultadoFormula = (gradosLibertad * varianza) / varianzaEsperada;
        int nivelConfianza;
        while (true) {
            String texto = JOptionPane.showInputDialog(null,
                    "Seleccione el nivel de confianza de la prueba:"
                            + "\n1: 80%"
                            + "\n2: 90%"
                            + "\n3: 95%"
                            + "\n4: 99%");

            if (texto == null) {
                return;
            }

            try {
                nivelConfianza = Integer.parseInt(texto.trim());
                if (nivelConfianza >= 1 && nivelConfianza <= 4) {
                    break;
                }
            } catch (NumberFormatException e) {
            }

            JOptionPane.showMessageDialog(null,
                    "Ingrese solamente un número entre 1 y 4.");
        }

        int columnaTabla = -1;
        if (nivelConfianza == 1) {
            columnaTabla = 0;
        }
        if (nivelConfianza == 2) {
            columnaTabla = 1;
        }
        if (nivelConfianza == 3) {
            columnaTabla = 2;
        }
        if (nivelConfianza == 4) {
            columnaTabla = 4;
        }
        if (columnaTabla == -1) {
            JOptionPane.showMessageDialog(null, "Nivel de confianza no válido.");
            return;
        }

        // Valores de la tabla para comparar el resultado.
        double valorInferior = TABLA_INFERIOR[filaTabla][columnaTabla];
        double valorSuperior = TABLA_SUPERIOR[filaTabla][columnaTabla];
        boolean resultadoAceptaH0 = true;
        if (resultadoFormula < valorInferior || resultadoFormula > valorSuperior) {
            resultadoAceptaH0 = false;
        }
        String resultado = "RESULTADOS DE LA PRUEBA DE VARIANZA\n"
                + "\nV(ri) = " + varianza
                + "\nn = " + cantidadDatos
                + "\nGrados de libertad = " + gradosLibertad
                + "\nResultado de la fórmula = " + resultadoFormula
                + "\nLímite inferior de la tabla = " + valorInferior
                + "\nLímite superior de la tabla = " + valorSuperior
                + "\n\nH0: σ² = 1/12";

        if (resultadoAceptaH0) {
            resultado += "\nNo se rechaza H0.";
        } else {
            resultado += "\nSe rechaza H0.";
        }

        JOptionPane.showMessageDialog(null, resultado);
    }

    public static int buscarFila(int gradosLibertad) {
        for (int i = 0; i < GRADOS_LIBERTAD.length; i++) {
            if (GRADOS_LIBERTAD[i] == gradosLibertad) {
                return i;
            }
        }
        return -1;
    }
}
