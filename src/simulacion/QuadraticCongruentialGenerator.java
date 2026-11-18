package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class QuadraticCongruentialGenerator extends NumberGenerator {
    public GenerationResult generate(Component owner) {
        Integer x = InputHelper.integer(owner, "Ingrese el valor de X0");
        if (x == null) return null;
        Integer m = InputHelper.integer(owner, "Ingrese el valor de m (entre 2 y 100000)");
        if (m == null) return null;
        Integer a = InputHelper.integer(owner, "Ingrese el valor de a (par)");
        if (a == null) return null;
        Integer b = InputHelper.integer(owner, "Ingrese el valor de b");
        if (b == null) return null;
        Integer c = InputHelper.integer(owner, "Ingrese el valor de c (impar)");
        if (c == null) return null;
        if (x < 0 || m < 2 || m > 100000 || a < 0 || b < 0 || c < 1
                || x >= m || a % 2 != 0 || c % 2 != 1) {
            JOptionPane.showMessageDialog(owner, "Verifique X0 < m, m entre 2 y 100000, a par, b no negativo y c impar.");
            return null;
        }
        long seed = x;
        ArrayList<Double> values = new ArrayList<Double>();
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < m; i++) {
            long squarePart = ((long) a % m) * (seed % m) % m;
            squarePart = squarePart * (seed % m) % m;
            long linearPart = ((long) b % m) * (seed % m) % m;
            long constantPart = (long) c % m;
            seed = (squarePart + linearPart + constantPart) % m;
            double ri = (double) seed / m;
            values.add(ri);
            report.append("X").append(i + 1).append(" = ").append(seed)
                    .append("    r").append(i + 1).append(" = ").append(ri).append("\n");
        }
        report.append("\nDatos generados: ").append(values.size());
        return new GenerationResult(values, report.toString());
    }
}
