package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class AdditiveCongruentialGenerator extends NumberGenerator {
    public GenerationResult generate(Component owner) {
        Integer m = InputHelper.integer(owner, "Ingrese el valor de m (mayor que 1)");
        if (m == null) return null;
        if (m <= 1) {
            JOptionPane.showMessageDialog(owner, "m debe ser mayor que 1.");
            return null;
        }
        ArrayList<Integer> initial = new ArrayList<Integer>();
        int index = 0;
        while (true) {
            Integer value = InputHelper.integer(owner, "Ingrese X" + index + " o -1 para terminar");
            if (value == null) return null;
            if (value == -1) {
                if (initial.size() < 5) {
                    JOptionPane.showMessageDialog(owner, "Debe ingresar al menos 5 numeros.");
                    continue;
                }
                break;
            }
            if (value < 0 || value >= m) {
                JOptionPane.showMessageDialog(owner, "X debe estar entre 0 y m - 1.");
                continue;
            }
            initial.add(value);
            index++;
        }
        int n = initial.size();
        ArrayList<Double> values = new ArrayList<Double>();
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int previous = initial.get(initial.size() - 1);
            int old = initial.get(initial.size() - n);
            int x = (previous + old) % m;
            double ri = (double) x / m;
            values.add(ri);
            report.append("X").append(index + i + 1).append(" = ").append(x)
                    .append("    r").append(index + i + 1).append(" = ").append(ri).append("\n");
            initial.add(x);
        }
        return new GenerationResult(values, report.toString());
    }
}
