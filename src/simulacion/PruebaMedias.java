package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PruebaMedias {
    public static String ejecutar(Component propietario, ArrayList<Double> datos) {
        if (datos == null || datos.isEmpty()) {
            JOptionPane.showMessageDialog(propietario, "No hay datos para realizar la prueba.");
            return null;
        }
        int nivelConfianza = AyudanteConfianza.elegir(propietario, true);
        if (nivelConfianza < 0) {
            return null;
        }
        double suma = 0;
        for (int i = 0; i < datos.size(); i++) {
            suma += datos.get(i);
        }
        double media = suma / datos.size();
        double[] valoresZ = {1.28, 1.44, 1.64, 1.96, 2.58};
        String[] etiquetas = {"80%", "85%", "90%", "95%", "99%"};
        double limiteInferior = 0.5 - valoresZ[nivelConfianza] / Math.sqrt(12.0 * datos.size());
        double limiteSuperior = 0.5 + valoresZ[nivelConfianza] / Math.sqrt(12.0 * datos.size());
        boolean aceptada = media >= limiteInferior && media <= limiteSuperior;
        StringBuilder reporte = new StringBuilder();
        reporte.append("PRUEBA DE MEDIAS\n\nDatos utilizados:\n");
        agregarDatos(reporte, datos);
        reporte.append("\nMedia = suma(ri) / cantidadDatos = ").append(media);
        reporte.append("\nLimite inferior = 0.5 - Z / sqrt(12n) = ").append(limiteInferior);
        reporte.append("\nLimite superior = 0.5 + Z / sqrt(12n) = ").append(limiteSuperior);
        reporte.append("\nNivel de confianza = ").append(etiquetas[nivelConfianza]);
        reporte.append("\nZ = ").append(valoresZ[nivelConfianza]);
        reporte.append("\n\nHipotesis nula: la media es 0.5.");
        reporte.append("\nDecision: ");
        if (aceptada) {
            reporte.append("No se rechaza H0.");
        } else {
            reporte.append("Se rechaza H0.");
        }
        return reporte.toString();
    }

    private static void agregarDatos(StringBuilder reporte, ArrayList<Double> datos) {
        for (int i = 0; i < datos.size(); i++) {
            reporte.append(datos.get(i));
            if (i + 1 < datos.size()) {
                reporte.append(", ");
            }
        }
    }
}


