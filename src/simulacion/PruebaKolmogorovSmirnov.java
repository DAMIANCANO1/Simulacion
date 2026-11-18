package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PruebaKolmogorovSmirnov {
    private static final int[] TAMANOS = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,25,30,35,40,45};
    private static final double[][] TABLA = {
        {0.950,0.975,0.995},{0.776,0.842,0.929},{0.642,0.708,0.828},{0.564,0.624,0.733},
        {0.510,0.565,0.669},{0.470,0.521,0.618},{0.438,0.486,0.577},{0.411,0.457,0.543},
        {0.388,0.432,0.514},{0.368,0.410,0.490},{0.352,0.391,0.468},{0.338,0.375,0.450},
        {0.325,0.361,0.433},{0.314,0.349,0.418},{0.304,0.338,0.404},{0.295,0.328,0.392},
        {0.286,0.318,0.381},{0.278,0.309,0.371},{0.272,0.301,0.363},{0.264,0.294,0.356},
        {0.250,0.270,0.320},{0.220,0.240,0.290},{0.210,0.230,0.270},
        {0.193,0.215,0.258},{0.182,0.203,0.243}
    };

    public static String ejecutar(Component propietario, ArrayList<Double> datos) {
        if (datos == null || datos.isEmpty()) {
            JOptionPane.showMessageDialog(propietario, "No hay datos para realizar la prueba.");
            return null;
        }
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i) < 0 || datos.get(i) > 1) {
                JOptionPane.showMessageDialog(propietario, "Todos los datos deben estar entre 0 y 1.");
                return null;
            }
        }
        int nivelConfianza = AyudanteConfianza.elegir(propietario, true);
        if (nivelConfianza < 0) return null;
        double[] ordenados = new double[datos.size()];
        for (int i = 0; i < datos.size(); i++) ordenados[i] = datos.get(i);
        ordenarBurbuja(ordenados);
        double dMas = 0;
        double dMenos = 0;
        StringBuilder reporte = new StringBuilder();
        reporte.append("PRUEBA KOLMOGOROV-SMIRNOV\n\nDatos utilizados:\n");
        agregarDatos(reporte, datos);
        reporte.append("\n\ni  i/cantidadDatos  ri  (i-1)/cantidadDatos  D+  D-\n");
        for (int i = 0; i < ordenados.length; i++) {
            double iEntreN = (double) (i + 1) / ordenados.length;
            double anteriorEntreN = (double) i / ordenados.length;
            double plus = iEntreN - ordenados[i];
            double minus = ordenados[i] - anteriorEntreN;
            if (plus > dMas) dMas = plus;
            if (minus > dMenos) dMenos = minus;
            reporte.append(i + 1).append("  ").append(iEntreN).append("  ").append(ordenados[i])
                    .append("  ").append(anteriorEntreN).append("  ").append(plus)
                    .append("  ").append(minus).append("\n");
        }
        double calculada = dMas > dMenos ? dMas : dMenos;
        double criticoValor = criticoValor(ordenados.length, nivelConfianza);
        String[] etiquetas = {"80%", "85%", "90%", "95%", "99%"};
        reporte.append("\nD+ = ").append(dMas);
        reporte.append("\nD- = ").append(dMenos);
        reporte.append("\nD calculada = ").append(calculada);
        reporte.append("\nValor critico = ").append(criticoValor);
        reporte.append("\nNivel de confianza = ").append(etiquetas[nivelConfianza]);
        reporte.append("\n\nHipotesis nula: los numeros siguen una distribucion uniforme.");
        if (calculada < criticoValor) reporte.append("\nDecision: No se rechaza H0.");
        else reporte.append("\nDecision: Se rechaza H0.");
        return reporte.toString();
    }

    private static double criticoValor(int cantidadDatos, int nivelConfianza) {
        if (nivelConfianza >= 2) {
            for (int i = 0; i < TAMANOS.length; i++) {
                if (TAMANOS[i] == cantidadDatos) return TABLA[i][nivelConfianza - 2];
            }
        }
        double[] constants = {1.07,1.14,1.22,1.36,1.63};
        return constants[nivelConfianza] / Math.sqrt(cantidadDatos);
    }

    private static void ordenarBurbuja(double[] valores) {
        for (int i = 0; i < valores.length - 1; i++) {
            for (int j = 0; j < valores.length - 1 - i; j++) {
                if (valores[j] > valores[j + 1]) {
                    double temp = valores[j];
                    valores[j] = valores[j + 1];
                    valores[j + 1] = temp;
                }
            }
        }
    }

    private static void agregarDatos(StringBuilder reporte, ArrayList<Double> datos) {
        for (int i = 0; i < datos.size(); i++) {
            reporte.append(datos.get(i));
            if (i + 1 < datos.size()) reporte.append(", ");
        }
    }
}


