package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class RunsTest {
    public static String execute(Component owner, ArrayList<Double> data) {
        if (data == null || data.size() < 2) {
            JOptionPane.showMessageDialog(owner, "Se necesitan al menos 2 datos.");
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
        StringBuilder sequence = new StringBuilder();
        int runs = 1;
        for (int i = 1; i < data.size(); i++) {
            char symbol = data.get(i) > data.get(i - 1) ? '1' : '0';
            sequence.append(symbol);
            if (i > 1 && symbol != sequence.charAt(i - 2)) runs++;
        }
        int n = data.size();
        double mean = (2.0 * n - 1) / 3.0;
        double variance = (16.0 * n - 29) / 90.0;
        double z = Math.abs(runs - mean) / Math.sqrt(variance);
        double[] criticalValues = {1.282,1.440,1.645,1.960,2.576};
        String[] labels = {"80%","85%","90%","95%","99%"};
        StringBuilder report = new StringBuilder();
        report.append("CORRIDAS ARRIBA Y ABAJO\n\nDatos utilizados:\n");
        appendData(report, data);
        report.append("\nSecuencia 1 si ri > r(i-1), 0 si ri <= r(i-1): ").append(sequence);
        report.append("\nCo = ").append(runs);
        report.append("\nMco = (2n-1)/3 = ").append(mean);
        report.append("\nVarianza = (16n-29)/90 = ").append(variance);
        report.append("\nZ calculada = ").append(z);
        report.append("\nZ critica = ").append(criticalValues[confidence]);
        report.append("\nNivel de confianza = ").append(labels[confidence]);
        report.append("\n\nHipotesis nula: los numeros son independientes.");
        if (z < criticalValues[confidence]) report.append("\nDecision: No se rechaza H0.");
        else report.append("\nDecision: Se rechaza H0.");
        return report.toString();
    }

    private static void appendData(StringBuilder report, ArrayList<Double> data) {
        for (int i = 0; i < data.size(); i++) {
            report.append(data.get(i));
            if (i + 1 < data.size()) report.append(", ");
        }
    }
}
