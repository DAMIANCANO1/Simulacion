package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class GeneradorCuadradosMedios extends GeneradorNumeros {
    public ResultadoGeneracion generar(Component propietario) {
        String texto = AyudanteEntrada.texto(propietario, "Ingrese la semilla X0 (2 a 9 digitos)");
        if (texto == null) {
            return null;
        }
        texto = texto.trim();
        if (texto.length() < 2 || texto.length() > 9 || texto.charAt(0) == '0'
                || !soloDigitos(texto)) {
            JOptionPane.showMessageDialog(propietario, "La semilla debe tener de 2 a 9 digitos.");
            return null;
        }
        int digitos = texto.length();
        long semilla = Long.parseLong(texto);
        ArrayList<Double> valores = new ArrayList<Double>();
        StringBuilder reporte = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            long cuadrado = semilla * semilla;
            String completo = String.valueOf(cuadrado);
            while (completo.length() < 2 * digitos) {
                completo = "0" + completo;
            }
            int inicio = (completo.length() - digitos) / 2;
            String centrales = completo.substring(inicio, inicio + digitos);
            double ri = Double.parseDouble("0." + centrales);
            valores.add(ri);
            reporte.append("ITERACION ").append(i + 1)
                    .append("\nSemilla al cuadrado: ").append(semilla).append("^2")
                    .append("\nCuadrado: ").append(cuadrado)
                    .append("\nResultado completo: ").append(completo)
                    .append("\nDigitos centrales: ").append(centrales)
                    .append("\nri: ").append(ri).append("\n\n");
            semilla = Long.parseLong(centrales);
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


