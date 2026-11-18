package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class GeneradorCongruencialMultiplicativo extends GeneradorNumeros {
    public ResultadoGeneracion generar(Component propietario) {
        Integer x = AyudanteEntrada.entero(propietario, "Ingrese el valor de X (mayor que 0)");
        if (x == null) return null;
        Integer k = AyudanteEntrada.entero(propietario, "Ingrese el valor de K (no negativo)");
        if (k == null) return null;
        Integer g = AyudanteEntrada.entero(propietario, "Ingrese el valor de G (entre 1 y 30)");
        if (g == null) return null;
        if (x < 1 || k < 0 || g < 1 || g > 30) {
            JOptionPane.showMessageDialog(propietario, "X debe ser mayor que 0, K no negativo y G entre 1 y 30.");
            return null;
        }
        long m = 1L << g;
        if (x >= m) {
            JOptionPane.showMessageDialog(propietario, "X debe ser menor que m = 2^G.");
            return null;
        }
        long a = (8L * k) % m;
        a = (a + 3L) % m;
        ArrayList<Long> vistos = new ArrayList<Long>();
        ArrayList<Double> valores = new ArrayList<Double>();
        StringBuilder reporte = new StringBuilder();
        long semilla = x;
        int generados = 0;
        while (true) {
            semilla = (a * semilla) % m;
            if (vistos.contains(semilla)) {
                break;
            }
            vistos.add(semilla);
            double ri = (double) semilla / m;
            valores.add(ri);
            reporte.append("X").append(generados + 1).append(" = ").append(semilla)
                    .append("    r").append(generados + 1).append(" = ").append(ri).append("\n");
            generados++;
        }
        reporte.append("\nDatos generados: ").append(valores.size());
        return new ResultadoGeneracion(valores, reporte.toString());
    }
}


