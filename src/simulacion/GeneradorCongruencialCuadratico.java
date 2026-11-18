package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class GeneradorCongruencialCuadratico extends GeneradorNumeros {
    public ResultadoGeneracion generar(Component propietario) {
        Integer x = AyudanteEntrada.entero(propietario, "Ingrese el valor de X0");
        if (x == null) return null;
        Integer m = AyudanteEntrada.entero(propietario, "Ingrese el valor de m (entre 2 y 100000)");
        if (m == null) return null;
        Integer a = AyudanteEntrada.entero(propietario, "Ingrese el valor de a (par)");
        if (a == null) return null;
        Integer b = AyudanteEntrada.entero(propietario, "Ingrese el valor de b");
        if (b == null) return null;
        Integer c = AyudanteEntrada.entero(propietario, "Ingrese el valor de c (impar)");
        if (c == null) return null;
        if (x < 0 || m < 2 || m > 100000 || a < 0 || b < 0 || c < 1
                || x >= m || a % 2 != 0 || c % 2 != 1) {
            JOptionPane.showMessageDialog(propietario, "Verifique X0 < m, m entre 2 y 100000, a par, b no negativo y c impar.");
            return null;
        }
        long semilla = x;
        ArrayList<Double> valores = new ArrayList<Double>();
        StringBuilder reporte = new StringBuilder();
        for (int i = 0; i < m; i++) {
            long squarePart = ((long) a % m) * (semilla % m) % m;
            squarePart = squarePart * (semilla % m) % m;
            long linearPart = ((long) b % m) * (semilla % m) % m;
            long constantPart = (long) c % m;
            semilla = (squarePart + linearPart + constantPart) % m;
            double ri = (double) semilla / m;
            valores.add(ri);
            reporte.append("X").append(i + 1).append(" = ").append(semilla)
                    .append("    r").append(i + 1).append(" = ").append(ri).append("\n");
        }
        reporte.append("\nDatos generados: ").append(valores.size());
        return new ResultadoGeneracion(valores, reporte.toString());
    }
}


