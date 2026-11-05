package Simulacion2doParcial;

import javax.swing.*;
import java.util.ArrayList;

public class CongroencialCuadratico {
    public static ArrayList<Double> generar() {
        //la ecuacion es xi+1 = (a*xi^2 + b*xi + c) modulo (m)
        String textoX = JOptionPane.showInputDialog(null, "Ingrese el valor de X0");
        String textoM = JOptionPane.showInputDialog(null, "Ingrese el valor de m (entre 2 y 100000)");
        String textoA = JOptionPane.showInputDialog(null, "Ingrese el valor de a (par)");
        String textoB = JOptionPane.showInputDialog(null, "Ingrese el valor de b");
        String textoC = JOptionPane.showInputDialog(null, "Ingrese el valor de c (impar)");
        if (textoX == null || textoM == null || textoA == null || textoB == null || textoC == null) {
            return new ArrayList<>();
        }
        int xEntrada;
        int mEntrada;
        int aEntrada;
        int bEntrada;
        int cEntrada;
        try {
            xEntrada = Integer.parseInt(textoX.trim());
            mEntrada = Integer.parseInt(textoM.trim());
            aEntrada = Integer.parseInt(textoA.trim());
            bEntrada = Integer.parseInt(textoB.trim());
            cEntrada = Integer.parseInt(textoC.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese solamente números enteros.");
            return new ArrayList<>();
        }
        if (xEntrada < 0 || mEntrada < 2 || mEntrada > 100000
                || aEntrada < 0 || bEntrada < 0 || cEntrada < 1) {
            JOptionPane.showMessageDialog(null, "Los valores ingresados están fuera de rango.");
            return new ArrayList<>();
        }
        int x = xEntrada;
        int m = mEntrada;
        int a = aEntrada;
        int b = bEntrada;
        int c = cEntrada;

        int N = m;

        int par = a % 2;
        int impar = c % 2;

        String palabrax = "";
        ArrayList<Double> resultados = new ArrayList<>();

        if (x < m && par == 0 && impar == 1) {
            for (int i = 0; i < N; i++) {
                int xi = (int) (((long) a * x * x + (long) b * x + c) % m);
                double ri = (double) xi / m;
                resultados.add(ri);
                palabrax += "X" + (i + 1) + " = " + xi + ", R" + (i + 1) + " = " + ri + "\n";
                x = xi;
            }
            JOptionPane.showMessageDialog(null, palabrax);
            return resultados;
        } else {
            JOptionPane.showMessageDialog(null, "No se cumple la condición para generar números aleatorios.");
            return resultados;
        }
    }
}
