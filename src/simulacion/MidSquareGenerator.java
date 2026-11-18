package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MidSquareGenerator extends NumberGenerator {
    public GenerationResult generate(Component owner) {
        String text = InputHelper.text(owner, "Ingrese la semilla X0 (2 a 9 digitos)");
        if (text == null) {
            return null;
        }
        text = text.trim();
        if (text.length() < 2 || text.length() > 9 || text.charAt(0) == '0'
                || !digitsOnly(text)) {
            JOptionPane.showMessageDialog(owner, "La semilla debe tener de 2 a 9 digitos.");
            return null;
        }
        int digits = text.length();
        long seed = Long.parseLong(text);
        ArrayList<Double> values = new ArrayList<Double>();
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            long square = seed * seed;
            String padded = String.valueOf(square);
            while (padded.length() < 2 * digits) {
                padded = "0" + padded;
            }
            int start = (padded.length() - digits) / 2;
            String middle = padded.substring(start, start + digits);
            double ri = Double.parseDouble("0." + middle);
            values.add(ri);
            report.append("ITERACION ").append(i + 1)
                    .append("\nSemilla al cuadrado: ").append(seed).append("^2")
                    .append("\nCuadrado: ").append(square)
                    .append("\nResultado completo: ").append(padded)
                    .append("\nDigitos centrales: ").append(middle)
                    .append("\nri: ").append(ri).append("\n\n");
            seed = Long.parseLong(middle);
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
