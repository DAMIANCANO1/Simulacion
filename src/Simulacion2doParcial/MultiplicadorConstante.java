package Simulacion2doParcial;

import javax.swing.*;
import java.util.ArrayList;

public class MultiplicadorConstante {

    public static ArrayList<Double> generar() {

        String semillaConstante = JOptionPane.showInputDialog(null,
                "Ingrese la semilla constante (2 a 9 dígitos)");
        String semillaVariable = JOptionPane.showInputDialog(null,
                "Ingrese la semilla variable (2 a 9 dígitos)");
        if (semillaConstante == null || semillaVariable == null) {
            return new ArrayList<>();
        }
        semillaConstante = semillaConstante.trim();
        semillaVariable = semillaVariable.trim();
        boolean constanteCorrecta = semillaConstante.length() >= 2
                && semillaConstante.length() <= 9;
        boolean variableCorrecta = semillaVariable.length() >= 2
                && semillaVariable.length() <= 9;
        for (int i = 0; i < semillaConstante.length(); i++) {
            char caracter = semillaConstante.charAt(i);
            if (caracter < '0' || caracter > '9') {
                constanteCorrecta = false;
            }
        }
        for (int i = 0; i < semillaVariable.length(); i++) {
            char caracter = semillaVariable.charAt(i);
            if (caracter < '0' || caracter > '9') {
                variableCorrecta = false;
            }
        }
        if (semillaConstante.length() == 0 || semillaConstante.charAt(0) == '0') {
            constanteCorrecta = false;
        }
        if (semillaVariable.length() == 0 || semillaVariable.charAt(0) == '0') {
            variableCorrecta = false;
        }
        if (!constanteCorrecta || !variableCorrecta) {
            JOptionPane.showMessageDialog(null, "Las semillas deben tener de 2 a 9 dígitos.");
            return new ArrayList<>();
        }

        if (semillaConstante.length() != semillaVariable.length()) {
            JOptionPane.showMessageDialog(null, "LAS SEMILLAS INGRESADAS DEBEN SER DE MISMA LONGITUD");
            return new ArrayList<>();

        }

        long constante = Long.parseLong(semillaConstante);
        long semilla = Long.parseLong(semillaVariable);
        ArrayList<Double> resultados = new ArrayList<>();

        int d = semillaConstante.length();

        for (int i = 0; i < 10; i++) {
            long multiplicacion = constante * semilla;
            String resultado = String.valueOf(multiplicacion);

            while (resultado.length() < d || (resultado.length() - d) % 2 != 0) {
                resultado = "0" + resultado;
            }

            int inicio = ((resultado.length() - d) / 2);
            int fin = (inicio + d);

            String recorte = resultado.substring(inicio, fin);
            resultados.add(Double.parseDouble("0." + recorte));

            JOptionPane.showMessageDialog(null, "ITERACIÓN " + (i + 1)
                    + "\nMULTIPLICACIÓN: " + constante + " * " + semilla
                    + "\nRESULTADO: " + resultado
                    + "\nDÍGITOS MEDIOS: " + recorte
                    + "\nri: 0." + recorte);

            semilla = Long.parseLong(recorte);
        }
        return resultados;
    }
}
