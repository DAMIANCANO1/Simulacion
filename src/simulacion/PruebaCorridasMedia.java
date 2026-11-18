package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PruebaCorridasMedia {
    public static String ejecutar(Component propietario, ArrayList<Double> datos) {
        if (datos == null || datos.isEmpty()) {
            JOptionPane.showMessageDialog(propietario, "No hay datos para realizar la prueba.");
            return null;
        }
        ArrayList<Double> utilizados = new ArrayList<Double>();
        for (int i = 0; i < datos.size(); i++) {
            double valor = datos.get(i);
            if (valor < 0 || valor > 1) {
                JOptionPane.showMessageDialog(propietario, "Todos los datos deben estar entre 0 y 1.");
                return null;
            }
            if (valor != 0.5) utilizados.add(valor);
        }
        if (utilizados.size() < 3) {
            JOptionPane.showMessageDialog(propietario, "Se necesitan al menos 3 datos diferentes de 0.5.");
            return null;
        }
        int arriba = 0;
        int abajo = 0;
        StringBuilder secuencia = new StringBuilder();
        for (int i = 0; i < utilizados.size(); i++) {
            if (utilizados.get(i) > 0.5) {
                secuencia.append("1");
                arriba++;
            } else {
                secuencia.append("0");
                abajo++;
            }
        }
        if (arriba == 0 || abajo == 0) {
            JOptionPane.showMessageDialog(propietario, "Debe existir al menos un numero arriba y uno abajo de 0.5.");
            return null;
        }
        int nivelConfianza = AyudanteConfianza.elegir(propietario, true);
        if (nivelConfianza < 0) return null;
        int corridas = 1;
        for (int i = 1; i < secuencia.length(); i++) {
            if (secuencia.charAt(i) != secuencia.charAt(i - 1)) corridas++;
        }
        int cantidadDatos = arriba + abajo;
        double media = (2.0 * arriba * abajo) / cantidadDatos + 0.5;
        double varianza = (2.0 * arriba * abajo * (2.0 * arriba * abajo - cantidadDatos))
                / (cantidadDatos * cantidadDatos * (cantidadDatos - 1));
        double z = (corridas - media) / Math.sqrt(varianza);
        double[] valoresCriticos = {1.282,1.440,1.645,1.960,2.576};
        String[] etiquetas = {"80%","85%","90%","95%","99%"};
        StringBuilder reporte = new StringBuilder();
        reporte.append("CORRIDAS ARRIBA Y ABAJO DE LA MEDIA\n\nDatos originales:\n");
        agregarDatos(reporte, datos);
        reporte.append("\nDatos utilizados:\n");
        agregarDatos(reporte, utilizados);
        reporte.append("\nSecuencia: ").append(secuencia);
        reporte.append("\nN1 = ").append(arriba).append("\nN0 = ").append(abajo);
        reporte.append("\nCo = ").append(corridas);
        reporte.append("\nMedia = (2*N1*N0)/cantidadDatos + 0.5 = ").append(media);
        reporte.append("\nVarianza = ").append(varianza);
        reporte.append("\nZ calculada = ").append(z);
        reporte.append("\nLimites = [-").append(valoresCriticos[nivelConfianza]).append(", ")
                .append(valoresCriticos[nivelConfianza]).append("]");
        reporte.append("\nNivel de confianza = ").append(etiquetas[nivelConfianza]);
        reporte.append("\n\nHipotesis nula: los numeros son independientes.");
        if (z >= -valoresCriticos[nivelConfianza] && z <= valoresCriticos[nivelConfianza]) {
            reporte.append("\nDecision: No se rechaza H0.");
        } else {
            reporte.append("\nDecision: Se rechaza H0.");
        }
        return reporte.toString();
    }

    private static void agregarDatos(StringBuilder reporte, ArrayList<Double> datos) {
        for (int i = 0; i < datos.size(); i++) {
            reporte.append(datos.get(i));
            if (i + 1 < datos.size()) reporte.append(", ");
        }
    }
}


