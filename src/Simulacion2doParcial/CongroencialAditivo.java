package Simulacion2doParcial;

import javax.swing.*;
import java.util.ArrayList;

public class CongroencialAditivo {
    public static ArrayList<Double> generar() {
        // Fórmula: Xi = (Xi-1 + Xi-n) mod m.
        // Se necesitan por lo menos 5 valores iniciales.

        ArrayList<Integer> numerosIngresados = new ArrayList<>();
        String textoM = JOptionPane.showInputDialog(null, "Ingrese el valor de m (mayor que 1)");
        if (textoM == null) return new ArrayList<>();
        int mEntrada;
        try {
            mEntrada = Integer.parseInt(textoM.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un entero válido.");
            return new ArrayList<>();
        }
        if (mEntrada <= 1) {
            JOptionPane.showMessageDialog(null, "m debe ser mayor que 1.");
            return new ArrayList<>();
        }
        int m = mEntrada;

        int contador = 0;

        while (true) {
            String texto = JOptionPane.showInputDialog(null, "Ingrese un número para X" + contador
                    + "\n o ingrese -1 para terminar de ingresar números");
            if (texto == null) {
                return new ArrayList<>();
            }
            int numero;
            try {
                numero = Integer.parseInt(texto.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un entero válido.");
                continue;
            }
            if (numero == -1) {
                if (numerosIngresados.size() < 5) {

                    JOptionPane.showMessageDialog(null, "Debe ingresar al menos 5 numeros");
                    continue;
                }
                break;
            } else if (numero >= 0 && numero < m) {
                numerosIngresados.add(numero);
                contador++;
            } else {
                JOptionPane.showMessageDialog(null, "X debe estar entre 0 y m - 1.");
            }

        }
        int n = numerosIngresados.size();

        String palabrax = "";
        ArrayList<Double> resultados = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            contador++;
            int posicionAnterior = numerosIngresados.size() - 1;
            int posicionHaceN = numerosIngresados.size() - n;
            int x = (numerosIngresados.get(posicionAnterior)
                    + numerosIngresados.get(posicionHaceN)) % m;
            //Convertimos x en un numero entre 0 y menor que 1
            double r = (double) x / m;
            resultados.add(r);
            palabrax += "X" + contador + " = " + x + " r" + contador + " = " + r + "\n";
            numerosIngresados.add(x);

        }
        JOptionPane.showMessageDialog(null, palabrax);
        return resultados;
    }
}
