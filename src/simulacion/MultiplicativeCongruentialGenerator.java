package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MultiplicativeCongruentialGenerator extends NumberGenerator {
    public GenerationResult generate(Component owner) {
        Integer x = InputHelper.integer(owner, "Ingrese el valor de X (mayor que 0)");
        if (x == null) return null;
        Integer k = InputHelper.integer(owner, "Ingrese el valor de K (no negativo)");
        if (k == null) return null;
        Integer g = InputHelper.integer(owner, "Ingrese el valor de G (entre 1 y 30)");
        if (g == null) return null;
        if (x < 1 || k < 0 || g < 1 || g > 30) {
            JOptionPane.showMessageDialog(owner, "X debe ser mayor que 0, K no negativo y G entre 1 y 30.");
            return null;
        }
        long m = 1L << g;
        if (x >= m) {
            JOptionPane.showMessageDialog(owner, "X debe ser menor que m = 2^G.");
            return null;
        }
        long a = (8L * k) % m;
        a = (a + 3L) % m;
        ArrayList<Long> seen = new ArrayList<Long>();
        ArrayList<Double> values = new ArrayList<Double>();
        StringBuilder report = new StringBuilder();
        long seed = x;
        int generated = 0;
        while (true) {
            seed = (a * seed) % m;
            if (seen.contains(seed)) {
                break;
            }
            seen.add(seed);
            double ri = (double) seed / m;
            values.add(ri);
            report.append("X").append(generated + 1).append(" = ").append(seed)
                    .append("    r").append(generated + 1).append(" = ").append(ri).append("\n");
            generated++;
        }
        report.append("\nDatos generados: ").append(values.size());
        return new GenerationResult(values, report.toString());
    }
}
