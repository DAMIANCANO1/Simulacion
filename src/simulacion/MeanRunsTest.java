package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MeanRunsTest {
    public static String execute(Component owner, ArrayList<Double> data) {
        if (data == null || data.isEmpty()) {
            JOptionPane.showMessageDialog(owner, "No hay datos para realizar la prueba.");
            return null;
        }
        ArrayList<Double> used = new ArrayList<Double>();
        for (int i = 0; i < data.size(); i++) {
            double value = data.get(i);
            if (value < 0 || value > 1) {
                JOptionPane.showMessageDialog(owner, "Todos los datos deben estar entre 0 y 1.");
                return null;
            }
            if (value != 0.5) used.add(value);
        }
        if (used.size() < 3) {
            JOptionPane.showMessageDialog(owner, "Se necesitan al menos 3 datos diferentes de 0.5.");
            return null;
        }
        int above = 0;
        int below = 0;
        StringBuilder sequence = new StringBuilder();
        for (int i = 0; i < used.size(); i++) {
            if (used.get(i) > 0.5) {
                sequence.append("1");
                above++;
            } else {
                sequence.append("0");
                below++;
            }
        }
        if (above == 0 || below == 0) {
            JOptionPane.showMessageDialog(owner, "Debe existir al menos un numero arriba y uno abajo de 0.5.");
            return null;
        }
        int confidence = ConfidenceHelper.choose(owner, true);
        if (confidence < 0) return null;
        int runs = 1;
        for (int i = 1; i < sequence.length(); i++) {
            if (sequence.charAt(i) != sequence.charAt(i - 1)) runs++;
        }
        int n = above + below;
        double mean = (2.0 * above * below) / n + 0.5;
        double variance = (2.0 * above * below * (2.0 * above * below - n))
                / (n * n * (n - 1));
        double z = (runs - mean) / Math.sqrt(variance);
        double[] criticalValues = {1.282,1.440,1.645,1.960,2.576};
        String[] labels = {"80%","85%","90%","95%","99%"};
        StringBuilder report = new StringBuilder();
        report.append("CORRIDAS ARRIBA Y ABAJO DE LA MEDIA\n\nDatos originales:\n");
        appendData(report, data);
        report.append("\nDatos utilizados:\n");
        appendData(report, used);
        report.append("\nSecuencia: ").append(sequence);
        report.append("\nN1 = ").append(above).append("\nN0 = ").append(below);
        report.append("\nCo = ").append(runs);
        report.append("\nMedia = (2*N1*N0)/n + 0.5 = ").append(mean);
        report.append("\nVarianza = ").append(variance);
        report.append("\nZ calculada = ").append(z);
        report.append("\nLimites = [-").append(criticalValues[confidence]).append(", ")
                .append(criticalValues[confidence]).append("]");
        report.append("\nNivel de confianza = ").append(labels[confidence]);
        report.append("\n\nHipotesis nula: los numeros son independientes.");
        if (z >= -criticalValues[confidence] && z <= criticalValues[confidence]) {
            report.append("\nDecision: No se rechaza H0.");
        } else {
            report.append("\nDecision: Se rechaza H0.");
        }
        return report.toString();
    }

    private static void appendData(StringBuilder report, ArrayList<Double> data) {
        for (int i = 0; i < data.size(); i++) {
            report.append(data.get(i));
            if (i + 1 < data.size()) report.append(", ");
        }
    }
}
