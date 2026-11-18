package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MidProductGenerator extends NumberGenerator {
    public GenerationResult generate(Component owner) {
        String first = InputHelper.text(owner, "Ingrese la semilla 1 (2 a 9 digitos)");
        if (first == null) {
            return null;
        }
        String second = InputHelper.text(owner, "Ingrese la semilla 2 (2 a 9 digitos)");
        if (second == null) {
            return null;
        }
        first = first.trim();
        second = second.trim();
        if (first.length() < 2 || first.length() > 9 || second.length() < 2
                || second.length() > 9 || first.charAt(0) == '0'
                || second.charAt(0) == '0' || !digitsOnly(first) || !digitsOnly(second)) {
            JOptionPane.showMessageDialog(owner, "Las semillas deben tener de 2 a 9 digitos.");
            return null;
        }
        if (first.length() != second.length()) {
            JOptionPane.showMessageDialog(owner, "Las semillas deben ser de la misma longitud.");
            return null;
        }
        int digits = first.length();
        long seedOne = Long.parseLong(first);
        long seedTwo = Long.parseLong(second);
        ArrayList<Double> values = new ArrayList<Double>();
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            long product = seedOne * seedTwo;
            String padded = String.valueOf(product);
            while (padded.length() < 2 * digits) {
                padded = "0" + padded;
            }
            int start = (padded.length() - digits) / 2;
            String middle = padded.substring(start, start + digits);
            double ri = Double.parseDouble("0." + middle);
            values.add(ri);
            report.append("ITERACION ").append(i + 1)
                    .append("\nMultiplicacion: ").append(seedOne).append(" * ").append(seedTwo)
                    .append("\nResultado completo: ").append(padded)
                    .append("\nDigitos centrales: ").append(middle)
                    .append("\nri: ").append(ri).append("\n\n");
            seedOne = seedTwo;
            seedTwo = Long.parseLong(middle);
        }
        return new GenerationResult(values, report.toString());
    }

    private boolean digitsOnly(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) < '0' || text.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
}
