package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PruebaCorridas {
    public static String ejecutar(Component propietario, ArrayList<Double> datos) {
        if (datos == null || datos.size() < 2) {
            JOptionPane.showMessageDialog(propietario, "Se necesitan al menos 2 datos.");
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
        StringBuilder secuencia = new StringBuilder();
        int corridas = 1;
        for (int i = 1; i < datos.size(); i++) {
            char symbol = datos.get(i) > datos.get(i - 1) ? '1' : '0';
            secuencia.append(symbol);
            if (i > 1 && symbol != secuencia.charAt(i - 2)) corridas++;
        }
        int cantidadDatos = datos.size();
        double media = (2.0 * cantidadDatos - 1) / 3.0;
        double varianza = (16.0 * cantidadDatos - 29) / 90.0;
        double z = Math.abs(corridas - media) / Math.sqrt(varianza);
        double[] valoresCriticos = {1.282,1.440,1.645,1.960,2.576};
        String[] etiquetas = {"80%","85%","90%","95%","99%"};
        StringBuilder reporte = new StringBuilder();
        reporte.append("CORRIDAS ARRIBA Y ABAJO\n\nDatos utilizados:\n");
        agregarDatos(reporte, datos);
        reporte.append("\nSecuencia 1 si ri > r(i-1), 0 si ri <= r(i-1): ").append(secuencia);
        reporte.append("\nCo = ").append(corridas);
        reporte.append("\nMco = (2n-1)/3 = ").append(media);
        reporte.append("\nVarianza = (16n-29)/90 = ").append(varianza);
        reporte.append("\nZ calculada = ").append(z);
        reporte.append("\nZ critica = ").append(valoresCriticos[nivelConfianza]);
        reporte.append("\nNivel de confianza = ").append(etiquetas[nivelConfianza]);
        reporte.append("\n\nHipotesis nula: los numeros son independientes.");
        if (z < valoresCriticos[nivelConfianza]) reporte.append("\nDecision: No se rechaza H0.");
        else reporte.append("\nDecision: Se rechaza H0.");
        return reporte.toString();
    }

    private static void agregarDatos(StringBuilder reporte, ArrayList<Double> datos) {
        for (int i = 0; i < datos.size(); i++) {
            reporte.append(datos.get(i));
            if (i + 1 < datos.size()) reporte.append(", ");
        }
    }
}


