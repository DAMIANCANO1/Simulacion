package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class GeneradorProductosMedios extends GeneradorNumeros {
    public ResultadoGeneracion generar(Component propietario) {
        String primera = AyudanteEntrada.texto(propietario, "Ingrese la semilla 1 (2 a 9 digitos)");
        if (primera == null) {
            return null;
        }
        String segunda = AyudanteEntrada.texto(propietario, "Ingrese la semilla 2 (2 a 9 digitos)");
        if (segunda == null) {
            return null;
        }
        primera = primera.trim();
        segunda = segunda.trim();
        if (primera.length() < 2 || primera.length() > 9 || segunda.length() < 2
                || segunda.length() > 9 || primera.charAt(0) == '0'
                || segunda.charAt(0) == '0' || !soloDigitos(primera) || !soloDigitos(segunda)) {
            JOptionPane.showMessageDialog(propietario, "Las semillas deben tener de 2 a 9 digitos.");
            return null;
        }
        if (primera.length() != segunda.length()) {
            JOptionPane.showMessageDialog(propietario, "Las semillas deben ser de la misma longitud.");
            return null;
        }
        int digitos = primera.length();
        long semillaUno = Long.parseLong(primera);
        long semillaDos = Long.parseLong(segunda);
        ArrayList<Double> valores = new ArrayList<Double>();
        StringBuilder reporte = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            long producto = semillaUno * semillaDos;
            String completo = String.valueOf(producto);
            while (completo.length() < 2 * digitos) {
                completo = "0" + completo;
            }
            int inicio = (completo.length() - digitos) / 2;
            String centrales = completo.substring(inicio, inicio + digitos);
            double ri = Double.parseDouble("0." + centrales);
            valores.add(ri);
            reporte.append("ITERACION ").append(i + 1)
                    .append("\nMultiplicacion: ").append(semillaUno).append(" * ").append(semillaDos)
                    .append("\nResultado completo: ").append(completo)
                    .append("\nDigitos centrales: ").append(centrales)
                    .append("\nri: ").append(ri).append("\n\n");
            semillaUno = semillaDos;
            semillaDos = Long.parseLong(centrales);
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


