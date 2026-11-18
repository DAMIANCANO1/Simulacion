package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class GeneradorCongruencialAditivo extends GeneradorNumeros {
    public ResultadoGeneracion generar(Component propietario) {
        Integer m = AyudanteEntrada.entero(propietario, "Ingrese el valor de m (mayor que 1)");
        if (m == null) return null;
        if (m <= 1) {
            JOptionPane.showMessageDialog(propietario, "m debe ser mayor que 1.");
            return null;
        }
        ArrayList<Integer> iniciales = new ArrayList<Integer>();
        int indice = 0;
        while (true) {
            Integer valor = AyudanteEntrada.entero(propietario, "Ingrese X" + indice + " o -1 para terminar");
            if (valor == null) return null;
            if (valor == -1) {
                if (iniciales.size() < 5) {
                    JOptionPane.showMessageDialog(propietario, "Debe ingresar al menos 5 numeros.");
                    continue;
                }
                break;
            }
            if (valor < 0 || valor >= m) {
                JOptionPane.showMessageDialog(propietario, "X debe estar entre 0 y m - 1.");
                continue;
            }
            iniciales.add(valor);
            indice++;
        }
        int cantidadDatos = iniciales.size();
        ArrayList<Double> valores = new ArrayList<Double>();
        StringBuilder reporte = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int anterior = iniciales.get(iniciales.size() - 1);
            int haceN = iniciales.get(iniciales.size() - cantidadDatos);
            int x = (anterior + haceN) % m;
            double ri = (double) x / m;
            valores.add(ri);
            reporte.append("X").append(indice + i + 1).append(" = ").append(x)
                    .append("    r").append(indice + i + 1).append(" = ").append(ri).append("\n");
            iniciales.add(x);
        }
        return new ResultadoGeneracion(valores, reporte.toString());
    }
}


