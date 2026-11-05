package Simulacion2doParcial;

import javax.swing.JOptionPane;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class kolmogorov {
    private static final int[] TAMANOS = {
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
            25, 30, 35, 40, 45
    };

    private static final double[][] TABLA = {
            {0.950, 0.975, 0.995},
            {0.776, 0.842, 0.929},
            {0.642, 0.708, 0.828},
            {0.564, 0.624, 0.733},
            {0.510, 0.565, 0.669},
            {0.470, 0.521, 0.618},
            {0.438, 0.486, 0.577},
            {0.411, 0.457, 0.543},
            {0.388, 0.432, 0.514},
            {0.368, 0.410, 0.490},
            {0.352, 0.391, 0.468},
            {0.338, 0.375, 0.450},
            {0.325, 0.361, 0.433},
            {0.314, 0.349, 0.418},
            {0.304, 0.338, 0.404},
            {0.295, 0.328, 0.392},
            {0.286, 0.318, 0.381},
            {0.278, 0.309, 0.371},
            {0.272, 0.301, 0.363},
            {0.264, 0.294, 0.356},
            {0.250, 0.270, 0.320},
            {0.220, 0.240, 0.290},
            {0.210, 0.230, 0.270},
            {0.193, 0.215, 0.258},
            {0.182, 0.203, 0.243}
    };

    public static void ejecutar(ArrayList<Double> datos) {
        if (datos == null || datos.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No hay datos para realizar la prueba.");
            return;
        }

        double[] orden = new double[datos.size()];
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i) < 0 || datos.get(i) > 1) {
                JOptionPane.showMessageDialog(null,
                        "Todos los datos deben estar entre 0 y 1.");
                return;
            }
            orden[i] = datos.get(i);
        }

        ordenar(orden);

        int confianza = elegirConfianza();
        if (confianza == -1) {
            return;
        }

        int n = orden.length;
        DecimalFormat formato = new DecimalFormat("0.000");
        double dMas = 0;
        double dMenos = 0;
        StringBuilder tabla = new StringBuilder();

        tabla.append("PRUEBA DE KOLMOGOROV-SMIRNOV\n\n");
        tabla.append("i\ti/n\tri\t(i-1)/n\tD+\tD-\n");
        tabla.append("----------------------------------------------------------------\n");

        for (int i = 0; i < n; i++) {
            double iEntreN = (double) (i + 1) / n;
            double anteriorEntreN = (double) i / n;
            double dPositivo = iEntreN - orden[i];
            double dNegativo = orden[i] - anteriorEntreN;

            if (dPositivo > dMas) {
                dMas = dPositivo;
            }
            if (dNegativo > dMenos) {
                dMenos = dNegativo;
            }

            tabla.append(i + 1).append("\t")
                    .append(formato.format(iEntreN)).append("\t")
                    .append(formato.format(orden[i])).append("\t")
                    .append(formato.format(anteriorEntreN)).append("\t")
                    .append(formato.format(dPositivo)).append("\t")
                    .append(formato.format(dNegativo)).append("\n");
        }

        double dCalculada = dMas;
        if (dMenos > dMas) {
            dCalculada = dMenos;
        }
        double dTabla = obtenerValorTabla(n, confianza);

        double alfa = obtenerAlfa(confianza);
        tabla.append("\nD+ = ").append(formato.format(dMas))
                .append("\nD- = ").append(formato.format(dMenos))
                .append("\nD = max(D+, D-) = ").append(formato.format(dCalculada))
                .append("\nn = ").append(n)
                .append("\nNivel de confianza = ").append(confianzaTexto(confianza))
                .append("\nAlfa = 1 - confianza = ").append(formato.format(alfa))
                .append("\nD de tabla = ").append(formato.format(dTabla))
                .append("\n\nH0: los números siguen una distribución uniforme.");

        if (dCalculada < dTabla) {
            tabla.append("\nNo se rechaza H0.");
        } else {
            tabla.append("\nSe rechaza H0.");
        }

        JOptionPane.showMessageDialog(null, tabla.toString(),
                "Kolmogorov-Smirnov", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void ordenar(double[] numeros) {
        for (int i = 0; i < numeros.length - 1; i++) {
            for (int j = 0; j < numeros.length - 1 - i; j++) {
                if (numeros[j] > numeros[j + 1]) {
                    double auxiliar = numeros[j];
                    numeros[j] = numeros[j + 1];
                    numeros[j + 1] = auxiliar;
                }
            }
        }
    }

    public static int elegirConfianza() {
        String texto = JOptionPane.showInputDialog(null,
                "Seleccione el nivel de confianza:"
                        + "\n1: 80% (alfa = 0.20)"
                        + "\n2: 85% (alfa = 0.15)"
                        + "\n3: 90% (alfa = 0.10)"
                        + "\n4: 95% (alfa = 0.05)"
                        + "\n5: 99% (alfa = 0.01)");
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

    public static double obtenerValorTabla(int n, int columna) {
        // Las columnas 90%, 95% y 99% corresponden a la imagen proporcionada.
        if (columna >= 2) {
            for (int i = 0; i < TAMANOS.length; i++) {
                if (TAMANOS[i] == n) {
                    return TABLA[i][columna - 2];
                }
            }
        }

        // Las columnas 80% y 85% no aparecen en la imagen; se usa su
        // constante estándar para tamaños de muestra no incluidos en la tabla.
        double[] constantes = {1.07, 1.14, 1.22, 1.36, 1.63};
        return constantes[columna] / Math.sqrt(n);
    }

    public static String confianzaTexto(int confianza) {
        if (confianza == 0) {
            return "80%";
        }
        if (confianza == 1) {
            return "85%";
        }
        if (confianza == 2) {
            return "90%";
        }
        if (confianza == 3) {
            return "95%";
        }
        return "99%";
    }

    public static double obtenerAlfa(int confianza) {
        double[] alfas = {0.20, 0.15, 0.10, 0.05, 0.01};
        return alfas[confianza];
    }
}
