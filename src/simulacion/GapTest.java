package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class GapTest {
    public static String execute(Component owner, ArrayList<Double> data) {
        if (data == null || data.size() < 3) {
            JOptionPane.showMessageDialog(owner, "Se necesitan al menos 3 datos.");
            return null;
        }
        Double alpha = InputHelper.decimal(owner, "Ingrese el limite inferior alfa");
        if (alpha == null) return null;
        Double beta = InputHelper.decimal(owner, "Ingrese el limite superior beta");
        if (beta == null) return null;
        if (alpha < 0 || alpha >= beta || beta > 1) {
            JOptionPane.showMessageDialog(owner, "El intervalo debe cumplir 0 <= alfa < beta <= 1.");
            return null;
        }
        int confidence = ConfidenceHelper.choose(owner, false);
        if (confidence < 0) return null;
        ArrayList<Integer> sequence = new ArrayList<Integer>();
        for (int i = 0; i < data.size(); i++) {
            double value = data.get(i);
            if (value < 0 || value > 1) {
                JOptionPane.showMessageDialog(owner, "Todos los datos deben estar entre 0 y 1.");
                return null;
            }
            if (value > alpha && value < beta) sequence.add(1);
            else sequence.add(0);
        }
        ArrayList<Integer> gaps = new ArrayList<Integer>();
        int previous = -1;
        for (int i = 0; i < sequence.size(); i++) {
            if (sequence.get(i) == 1) {
                if (previous >= 0) gaps.add(i - previous - 1);
                previous = i;
            }
        }
        if (gaps.isEmpty()) {
            JOptionPane.showMessageDialog(owner, "No se encontraron dos unos dentro del intervalo.");
            return null;
        }
        int[] observed = new int[6];
        for (int i = 0; i < gaps.size(); i++) {
            int gap = gaps.get(i);
            if (gap >= 5) observed[5]++;
            else observed[gap]++;
        }
        int count = gaps.size();
        double p = beta - alpha;
        double q = 1 - p;
        double chi = 0;
        double[] expected = new double[6];
        StringBuilder report = new StringBuilder();
        report.append("PRUEBA DE HUECOS\n\nDatos utilizados:\n");
        appendData(report, data);
        report.append("\nIntervalo abierto: (").append(alpha).append(", ").append(beta).append(")");
        report.append("\nProbabilidad p = beta-alfa = ").append(p);
        report.append("\nSecuencia: ");
        for (int i = 0; i < sequence.size(); i++) report.append(sequence.get(i));
        report.append("\nHuecos encontrados: ");
        appendIntegerData(report, gaps);
        report.append("\n\nTamano  Oi  Ei  (Oi-Ei)^2/Ei\n");
        for (int i = 0; i < 6; i++) {
            if (i < 5) {
                expected[i] = count * p;
                for (int j = 0; j < i; j++) expected[i] *= q;
            } else {
                expected[i] = count;
                for (int j = 0; j < 5; j++) expected[i] *= q;
            }
            double term = (observed[i] - expected[i]) * (observed[i] - expected[i]) / expected[i];
            chi += term;
            report.append(i < 5 ? String.valueOf(i) : "5 o mas").append("  ")
                    .append(observed[i]).append("  ").append(expected[i]).append("  ").append(term).append("\n");
        }
        int df = 5;
        int row = ChiSquareTest.findRow(df);
        if (row < 0) {
            JOptionPane.showMessageDialog(owner, "No existe una fila para 5 grados de libertad.");
            return null;
        }
        double critical = gapCritical(confidence);
        String[] labels = {"80%","90%","95%","99%"};
        report.append("\nGrados de libertad = ").append(df);
        report.append("\nChi calculada = ").append(chi);
        report.append("\nChi critica = ").append(critical);
        report.append("\nNivel de confianza = ").append(labels[confidence]);
        report.append("\n\nHipotesis nula: los numeros son independientes.");
        if (chi < critical) report.append("\nDecision: No se rechaza H0.");
        else report.append("\nDecision: Se rechaza H0.");
        return report.toString();
    }

    private static double gapCritical(int confidence) {
        double[] values80 = {1.642,3.219,4.642,5.989,7.289,8.558,9.803,11.030,12.242,13.442};
        double[] values90 = {2.706,4.605,6.251,7.779,9.236,10.645,12.017,13.362,14.684,15.987};
        double[] values95 = {3.841,5.991,7.815,9.488,11.070,12.592,14.067,15.507,16.919,18.307};
        double[] values99 = {6.635,9.210,11.345,13.277,15.086,16.812,18.475,20.090,21.666,23.209};
        if (confidence == 0) return values80[5];
        if (confidence == 1) return values90[5];
        if (confidence == 2) return values95[5];
        return values99[5];
    }

    private static void appendData(StringBuilder report, ArrayList<Double> data) {
        for (int i = 0; i < data.size(); i++) {
            report.append(data.get(i));
            if (i + 1 < data.size()) report.append(", ");
        }
    }

    private static void appendIntegerData(StringBuilder report, ArrayList<Integer> data) {
        for (int i = 0; i < data.size(); i++) {
            report.append(data.get(i));
            if (i + 1 < data.size()) report.append(", ");
        }
    }
}
