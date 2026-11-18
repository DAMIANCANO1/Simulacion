package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MeanTest {
    public static String execute(Component owner, ArrayList<Double> data) {
        if (data == null || data.isEmpty()) {
            JOptionPane.showMessageDialog(owner, "No hay datos para realizar la prueba.");
            return null;
        }
        int confidence = ConfidenceHelper.choose(owner, true);
        if (confidence < 0) {
            return null;
        }
        double sum = 0;
        for (int i = 0; i < data.size(); i++) {
            sum += data.get(i);
        }
        double mean = sum / data.size();
        double[] zValues = {1.28, 1.44, 1.64, 1.96, 2.58};
        String[] labels = {"80%", "85%", "90%", "95%", "99%"};
        double lower = 0.5 - zValues[confidence] / Math.sqrt(12.0 * data.size());
        double upper = 0.5 + zValues[confidence] / Math.sqrt(12.0 * data.size());
        boolean accepted = mean >= lower && mean <= upper;
        StringBuilder report = new StringBuilder();
        report.append("PRUEBA DE MEDIAS\n\nDatos utilizados:\n");
        appendData(report, data);
        report.append("\nMedia = suma(ri) / n = ").append(mean);
        report.append("\nLimite inferior = 0.5 - Z / sqrt(12n) = ").append(lower);
        report.append("\nLimite superior = 0.5 + Z / sqrt(12n) = ").append(upper);
        report.append("\nNivel de confianza = ").append(labels[confidence]);
        report.append("\nZ = ").append(zValues[confidence]);
        report.append("\n\nHipotesis nula: la media es 0.5.");
        report.append("\nDecision: ");
        if (accepted) {
            report.append("No se rechaza H0.");
        } else {
            report.append("Se rechaza H0.");
        }
        return report.toString();
    }

    private static void appendData(StringBuilder report, ArrayList<Double> data) {
        for (int i = 0; i < data.size(); i++) {
            report.append(data.get(i));
            if (i + 1 < data.size()) {
                report.append(", ");
            }
        }
    }
}
