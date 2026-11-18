package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class LinearCongruentialGenerator extends NumberGenerator {
    public GenerationResult generate(Component owner) {
        Integer x0 = InputHelper.integer(owner, "Ingrese el valor de X0");
        if (x0 == null) return null;
        Integer a = InputHelper.integer(owner, "Ingrese el valor de a");
        if (a == null) return null;
        Integer c = InputHelper.integer(owner, "Ingrese el valor de c");
        if (c == null) return null;
        Integer m = InputHelper.integer(owner, "Ingrese el valor de m (mayor que 1)");
        if (m == null) return null;
        if (x0 < 0 || a < 0 || c < 0 || m <= 1 || x0 >= m) {
            JOptionPane.showMessageDialog(owner, "X0, a y c no pueden ser negativos; m debe ser mayor que 1 y X0 menor que m.");
            return null;
        }
        long x = x0.longValue();
        ArrayList<Double> values = new ArrayList<Double>();
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            long aPart = ((long) a % m) * (x % m);
            long cPart = (long) c % m;
            x = (aPart + cPart) % m;
            double ri = (double) x / m;
            values.add(ri);
            report.append("X").append(i + 1).append(" = ").append(x)
                    .append("    r").append(i + 1).append(" = ").append(ri).append("\n");
        }
        return new GenerationResult(values, report.toString());
    }
}
