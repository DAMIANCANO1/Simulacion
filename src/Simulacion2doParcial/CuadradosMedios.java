package Simulacion2doParcial;

import javax.swing.*;
import java.util.ArrayList;

public class CuadradosMedios {

    public static ArrayList<Double> generar() {

        String semilla = JOptionPane.showInputDialog(null, "Ingrese la semilla X0 (2 a 9 dígitos)");
        if (semilla == null) {
            return new ArrayList<>();
        }
        semilla = semilla.trim();
        boolean formatoCorrecto = semilla.length() >= 2 && semilla.length() <= 9;
        for (int i = 0; i < semilla.length(); i++) {
            char caracter = semilla.charAt(i);
            if (caracter < '0' || caracter > '9') {
                formatoCorrecto = false;
            }
        }
        if (semilla.length() == 0 || semilla.charAt(0) == '0') {
            formatoCorrecto = false;
        }
        if (!formatoCorrecto) {
            JOptionPane.showMessageDialog(null, "La semilla debe tener de 2 a 9 dígitos.");
            return new ArrayList<>();
        }
        long s1 = Long.parseLong(semilla);
        int d = semilla.length();
        ArrayList<Double> resultados = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            long cuadrado = s1 * s1;
            String resultado = String.valueOf(cuadrado);

            while (resultado.length() < (d * 2)) {
                resultado = "0" + resultado;
            }

            int inicio = ((resultado.length() - d) / 2);
            int fin = (inicio + d);

            String recorte = resultado.substring(inicio, fin);
            resultados.add(Double.parseDouble("0." + recorte));

            JOptionPane.showMessageDialog(null, "ITERACIÓN " + (i + 1)
                    + "\nSEMILLA AL CUADRADO: " + s1 + "²"
                    + "\nRESULTADO: " + resultado
                    + "\nDÍGITOS MEDIOS: " + recorte
                    + "\nri: 0." + recorte);

            s1 = Long.parseLong(recorte);
        }
        return resultados;
    }
}
