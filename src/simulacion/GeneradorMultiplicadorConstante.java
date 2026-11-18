package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class GeneradorMultiplicadorConstante extends GeneradorNumeros {
    public ResultadoGeneracion generar(Component propietario) {
        String textoConstante = AyudanteEntrada.texto(propietario, "Ingrese la semilla constante (2 a 9 digitos)");
        if (textoConstante == null) {
            return null;
        }
        String textoVariable = AyudanteEntrada.texto(propietario, "Ingrese la semilla variable (2 a 9 digitos)");
        if (textoVariable == null) {
            return null;
        }
        textoConstante = textoConstante.trim();
        textoVariable = textoVariable.trim();
        if (textoConstante.length() < 2 || textoConstante.length() > 9
                || textoVariable.length() < 2 || textoVariable.length() > 9
                || textoConstante.charAt(0) == '0' || textoVariable.charAt(0) == '0'
                || !soloDigitos(textoConstante) || !soloDigitos(textoVariable)) {
            JOptionPane.showMessageDialog(propietario, "Las semillas deben tener de 2 a 9 digitos.");
            return null;
        }
        if (textoConstante.length() != textoVariable.length()) {
            JOptionPane.showMessageDialog(propietario, "Las semillas deben ser de la misma longitud.");
            return null;
        }
        int digitos = textoConstante.length();
        long constante = Long.parseLong(textoConstante);
        long variable = Long.parseLong(textoVariable);
        ArrayList<Double> valores = new ArrayList<Double>();
        StringBuilder reporte = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            long producto = constante * variable;
            String completo = String.valueOf(producto);
            while (completo.length() < digitos || (completo.length() - digitos) % 2 != 0) {
                completo = "0" + completo;
            }
            int inicio = (completo.length() - digitos) / 2;
            String centrales = completo.substring(inicio, inicio + digitos);
            double ri = Double.parseDouble("0." + centrales);
            valores.add(ri);
            reporte.append("ITERACION ").append(i + 1)
                    .append("\nMultiplicacion: ").append(constante).append(" * ").append(variable)
                    .append("\nResultado completo: ").append(completo)
                    .append("\nDigitos centrales: ").append(centrales)
                    .append("\nri: ").append(ri).append("\n\n");
            variable = Long.parseLong(centrales);
        }
        return new ResultadoGeneracion(valores, reporte.toString());
    }

    private boolean soloDigitos(String texto) {
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) < '0' || texto.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
}


