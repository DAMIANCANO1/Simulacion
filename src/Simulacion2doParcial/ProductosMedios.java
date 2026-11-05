package Simulacion2doParcial;

import javax.swing.*;
import java.util.ArrayList;

public class ProductosMedios {

    public static ArrayList<Double> generar() {

        String semilla1 = JOptionPane.showInputDialog(null, "Ingrese la semilla 1 (2 a 9 dígitos)");
        String semilla2 = JOptionPane.showInputDialog(null, "Ingrese la semilla 2 (2 a 9 dígitos)");
        if (semilla1 == null || semilla2 == null) {
            return new ArrayList<>();
        }
        semilla1 = semilla1.trim();
        semilla2 = semilla2.trim();
        boolean semilla1Correcta = semilla1.length() >= 2 && semilla1.length() <= 9;
        boolean semilla2Correcta = semilla2.length() >= 2 && semilla2.length() <= 9;
        for (int i = 0; i < semilla1.length(); i++) {
            char caracter = semilla1.charAt(i);
            if (caracter < '0' || caracter > '9') {
                semilla1Correcta = false;
            }
        }
        for (int i = 0; i < semilla2.length(); i++) {
            char caracter = semilla2.charAt(i);
            if (caracter < '0' || caracter > '9') {
                semilla2Correcta = false;
            }
        }
        if (semilla1.length() == 0 || semilla1.charAt(0) == '0') {
            semilla1Correcta = false;
        }
        if (semilla2.length() == 0 || semilla2.charAt(0) == '0') {
            semilla2Correcta = false;
        }
        if (!semilla1Correcta || !semilla2Correcta) {
            JOptionPane.showMessageDialog(null, "Las semillas deben tener de 2 a 9 dígitos.");
            return new ArrayList<>();
        }

        if (semilla1.length() != semilla2.length()) {
            JOptionPane.showMessageDialog(null, "LAS SEMILLAS INGRESADAS DEBEN SER DE MISMA LONGITUD");
            return new ArrayList<>();

        }

        long s1 = Long.parseLong(semilla1);
        long s2 = Long.parseLong(semilla2);

        long paso1 = 0;
        int d = semilla1.length();
        ArrayList<Double> resultados = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            paso1 = s1 * s2;
            String multi = String.valueOf(paso1);

            while (multi.length() < (d * 2)) {
                multi = "0" + multi;

            }

            int inicio = ((multi.length() - d) / 2);
            int fin = (inicio + d);

            String recorte = multi.substring(inicio, fin);
            resultados.add(Double.parseDouble("0." + recorte));
            JOptionPane.showMessageDialog(null, "NUMERO A MULTIPLICAR" + s1 + "*" + s2
                    + "\n RESULTADO: " + multi
                    + "\n DIGITOS MEDIOS: " + recorte
                    + "\n ri: 0." + recorte);

            s1 = s2;
            s2 = Long.parseLong(recorte);

        }
        return resultados;
    }
}
