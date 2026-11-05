package Simulacion2doParcial;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PruebaCorridas {
    public static void ejecutar(ArrayList<Double> datos) {
        if (datos == null || datos.size() < 2) {
            JOptionPane.showMessageDialog(null,
                    "Se necesitan al menos 2 datos para realizar la prueba.");
            return;
        }

        for (double numero : datos) {
            if (numero < 0 || numero > 1) {
                JOptionPane.showMessageDialog(null,
                        "Todos los datos deben estar entre 0 y 1.");
                return;
            }
        }

        StringBuilder secuencia = new StringBuilder();
        int corridas = 1;

        for (int i = 1; i < datos.size(); i++) {
            if (datos.get(i) > datos.get(i - 1)) {
                secuencia.append("1");
            } else {
                secuencia.append("0");
            }

            if (i > 1 && secuencia.charAt(i - 1) != secuencia.charAt(i - 2)) {
                corridas++;
            }
        }

        int n = datos.size();
        double mediaCorridas = (2.0 * n - 1) / 3.0;
        double varianzaCorridas = (16.0 * n - 29) / 90.0;
        double desviacionCorridas = Math.sqrt(varianzaCorridas);
        double zCalculada = Math.abs(corridas - mediaCorridas) / desviacionCorridas;

        int confianza = elegirConfianza();
        if (confianza == -1) {
            return;
        }

        double zTabla = obtenerZ(confianza);
        DecimalFormat formato = new DecimalFormat("0.000");
        String resultado = "";

        resultado += "PRUEBA DE CORRIDAS ARRIBA Y ABAJO\n\n";
        resultado += "Numeros originales:\n";
        for (double numero : datos) {
            resultado += formato.format(numero) + " ";
        }
        resultado += "\n\n1 = el numero actual es mayor al anterior";
        resultado += "\n0 = el numero actual es menor o igual al anterior";
        resultado += "\n\nSecuencia: " + secuencia;
        resultado += "\n\nCo = numero de corridas = " + corridas;
        resultado += "\nn = " + n;
        resultado += "\nMco = (2n - 1) / 3 = " + formato.format(mediaCorridas);
        resultado += "\nZo = " + formato.format(zCalculada);
        resultado += "\nNivel de confianza = " + confianzaTexto(confianza);
        resultado += "\nAlfa = " + formato.format(1 - confianzaValor(confianza));
        resultado += "\nZ alfa/2 de tabla = " + formato.format(zTabla);
        resultado += "\n\nH0: los numeros son independientes.";

        if (zCalculada < zTabla) {
            resultado += "\nNo se rechaza H0.";
        } else {
            resultado += "\nSe rechaza H0.";
        }

        JOptionPane.showMessageDialog(null, resultado,
                "Prueba de corridas", JOptionPane.INFORMATION_MESSAGE);
    }

    public static int elegirConfianza() {
        String texto = JOptionPane.showInputDialog(null,
                "Seleccione el nivel de confianza:"
                        + "\n1: 80%"
                        + "\n2: 85%"
                        + "\n3: 90%"
                        + "\n4: 95%"
                        + "\n5: 99%");
        if (texto == null) {
            return -1;
        }

        try {
            int opcion = Integer.parseInt(texto.trim());
            if (opcion >= 1 && opcion <= 5) {
                return opcion - 1;
            }
        } catch (NumberFormatException ignored) {
        }

        JOptionPane.showMessageDialog(null, "Seleccione una opcion entre 1 y 5.");
        return -1;
    }

    public static double obtenerZ(int confianza) {
        double[] valores = {1.282, 1.440, 1.645, 1.960, 2.576};
        return valores[confianza];
    }

    public static double confianzaValor(int confianza) {
        double[] valores = {0.80, 0.85, 0.90, 0.95, 0.99};
        return valores[confianza];
    }

    public static String confianzaTexto(int confianza) {
        String[] valores = {"80%", "85%", "90%", "95%", "99%"};
        return valores[confianza];
    }
}
