package Simulacion2doParcial;

import javax.swing.*;
import java.util.ArrayList;

public class CongroencialMultiplicativo {
    public static ArrayList<Double> generar() {
        //generar suficientes numeros con los parametros
        //X, K, G

        //la ecuacion es xi+1 = (a*xi) modulo (M)

        String textoX = JOptionPane.showInputDialog(null, "Ingrese el valor de X (mayor que 0)");
        String textoK = JOptionPane.showInputDialog(null, "Ingrese el valor de K (no negativo)");
        String textoG = JOptionPane.showInputDialog(null, "Ingrese el valor de G (entre 1 y 30)");
        if (textoX == null || textoK == null || textoG == null) return new ArrayList<>();
        int xEntrada;
        int kEntrada;
        int gEntrada;
        try {
            xEntrada = Integer.parseInt(textoX.trim());
            kEntrada = Integer.parseInt(textoK.trim());
            gEntrada = Integer.parseInt(textoG.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese solamente números enteros.");
            return new ArrayList<>();
        }
        if (xEntrada < 1 || kEntrada < 0 || gEntrada < 1 || gEntrada > 30) {
            JOptionPane.showMessageDialog(null,
                    "X debe ser mayor que 0, K no negativo y G entre 1 y 30.");
            return new ArrayList<>();
        }
        int X = xEntrada;
        int K = kEntrada;
        int G = gEntrada;

        int m = (int) Math.pow(2, G);
        int a = 3 + 8 * K;
        if (X >= m) {
            JOptionPane.showMessageDialog(null, "X debe ser menor que m = 2^G.");
            return new ArrayList<>();
        }

        boolean repetido = false;
        String palabrax = " ";
        int contador = 0;
        ArrayList<Integer> numerosGenerados = new ArrayList<>();
        ArrayList<Double> resultados = new ArrayList<>();
        do {
            X = (int) (((long) a * X) % m);
            if (numerosGenerados.contains(X)) {
                repetido = true;
            } else {
                numerosGenerados.add(X);
                double ri = (double) X / m;
                resultados.add(ri);
                palabrax += "X" + contador + " = " + X + " R" + contador + " = " + ri+"\n";
                contador++;
            }

        } while (!repetido);
        JOptionPane.showMessageDialog(null, "Los valores son: " + palabrax);
        return resultados;
    }
}
