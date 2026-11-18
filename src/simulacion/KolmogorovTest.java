package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class KolmogorovTest {
    private static final int[] SIZES = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,25,30,35,40,45};
    private static final double[][] TABLE = {
        {0.950,0.975,0.995},{0.776,0.842,0.929},{0.642,0.708,0.828},{0.564,0.624,0.733},
        {0.510,0.565,0.669},{0.470,0.521,0.618},{0.438,0.486,0.577},{0.411,0.457,0.543},
        {0.388,0.432,0.514},{0.368,0.410,0.490},{0.352,0.391,0.468},{0.338,0.375,0.450},
        {0.325,0.361,0.433},{0.314,0.349,0.418},{0.304,0.338,0.404},{0.295,0.328,0.392},
        {0.286,0.318,0.381},{0.278,0.309,0.371},{0.272,0.301,0.363},{0.264,0.294,0.356},
        {0.250,0.270,0.320},{0.220,0.240,0.290},{0.210,0.230,0.270},
        {0.193,0.215,0.258},{0.182,0.203,0.243}
    };

    public static String execute(Component owner, ArrayList<Double> data) {
        if (data == null || data.isEmpty()) {
            JOptionPane.showMessageDialog(owner, "No hay datos para realizar la prueba.");
            return null;
        }
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) < 0 || data.get(i) > 1) {
                JOptionPane.showMessageDialog(owner, "Todos los datos deben estar entre 0 y 1.");
                return null;
            }
        }
        int confidence = ConfidenceHelper.choose(owner, true);
        if (confidence < 0) return null;
        double[] sorted = new double[data.size()];
        for (int i = 0; i < data.size(); i++) sorted[i] = data.get(i);
        bubbleSort(sorted);
        double dPlus = 0;
        double dMinus = 0;
        StringBuilder report = new StringBuilder();
        report.append("PRUEBA KOLMOGOROV-SMIRNOV\n\nDatos utilizados:\n");
        appendData(report, data);
        report.append("\n\ni  i/n  ri  (i-1)/n  D+  D-\n");
        for (int i = 0; i < sorted.length; i++) {
            double iOverN = (double) (i + 1) / sorted.length;
            double previousOverN = (double) i / sorted.length;
            double plus = iOverN - sorted[i];
            double minus = sorted[i] - previousOverN;
            if (plus > dPlus) dPlus = plus;
            if (minus > dMinus) dMinus = minus;
            report.append(i + 1).append("  ").append(iOverN).append("  ").append(sorted[i])
                    .append("  ").append(previousOverN).append("  ").append(plus)
                    .append("  ").append(minus).append("\n");
        }
        double calculated = dPlus > dMinus ? dPlus : dMinus;
        double critical = critical(sorted.length, confidence);
        String[] labels = {"80%", "85%", "90%", "95%", "99%"};
        report.append("\nD+ = ").append(dPlus);
        report.append("\nD- = ").append(dMinus);
        report.append("\nD calculada = ").append(calculated);
        report.append("\nValor critico = ").append(critical);
        report.append("\nNivel de confianza = ").append(labels[confidence]);
        report.append("\n\nHipotesis nula: los numeros siguen una distribucion uniforme.");
        if (calculated < critical) report.append("\nDecision: No se rechaza H0.");
        else report.append("\nDecision: Se rechaza H0.");
        return report.toString();
    }

    private static double critical(int n, int confidence) {
        if (confidence >= 2) {
            for (int i = 0; i < SIZES.length; i++) {
                if (SIZES[i] == n) return TABLE[i][confidence - 2];
            }
        }
        double[] constants = {1.07,1.14,1.22,1.36,1.63};
        return constants[confidence] / Math.sqrt(n);
    }

    private static void bubbleSort(double[] values) {
        for (int i = 0; i < values.length - 1; i++) {
            for (int j = 0; j < values.length - 1 - i; j++) {
                if (values[j] > values[j + 1]) {
                    double temp = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = temp;
                }
            }
        }
    }

    private static void appendData(StringBuilder report, ArrayList<Double> data) {
        for (int i = 0; i < data.size(); i++) {
            report.append(data.get(i));
            if (i + 1 < data.size()) report.append(", ");
        }
    }
}
