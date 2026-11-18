package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ConstantMultiplierGenerator extends NumberGenerator {
    public GenerationResult generate(Component owner) {
        String constantText = InputHelper.text(owner, "Ingrese la semilla constante (2 a 9 digitos)");
        if (constantText == null) {
            return null;
        }
        String variableText = InputHelper.text(owner, "Ingrese la semilla variable (2 a 9 digitos)");
        if (variableText == null) {
            return null;
        }
        constantText = constantText.trim();
        variableText = variableText.trim();
        if (constantText.length() < 2 || constantText.length() > 9
                || variableText.length() < 2 || variableText.length() > 9
                || constantText.charAt(0) == '0' || variableText.charAt(0) == '0'
                || !digitsOnly(constantText) || !digitsOnly(variableText)) {
            JOptionPane.showMessageDialog(owner, "Las semillas deben tener de 2 a 9 digitos.");
            return null;
        }
        if (constantText.length() != variableText.length()) {
            JOptionPane.showMessageDialog(owner, "Las semillas deben ser de la misma longitud.");
            return null;
        }
        int digits = constantText.length();
        long constant = Long.parseLong(constantText);
        long variable = Long.parseLong(variableText);
        ArrayList<Double> values = new ArrayList<Double>();
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            long product = constant * variable;
            String padded = String.valueOf(product);
            while (padded.length() < digits || (padded.length() - digits) % 2 != 0) {
                padded = "0" + padded;
            }
            int start = (padded.length() - digits) / 2;
            String middle = padded.substring(start, start + digits);
            double ri = Double.parseDouble("0." + middle);
            values.add(ri);
            report.append("ITERACION ").append(i + 1)
                    .append("\nMultiplicacion: ").append(constant).append(" * ").append(variable)
                    .append("\nResultado completo: ").append(padded)
                    .append("\nDigitos centrales: ").append(middle)
                    .append("\nri: ").append(ri).append("\n\n");
            variable = Long.parseLong(middle);
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
