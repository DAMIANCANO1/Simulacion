package Simulacion2doParcial;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class lineal {
    public static ArrayList<Double> generar() {
        //se generan 10 numeros aleatorios entre 0 y 1
        //con parametros ingresados por el usuario
        String textoX = JOptionPane.showInputDialog(null, "Ingrese el valor de X0");
        String textoA = JOptionPane.showInputDialog(null, "Ingrese el valor de a");
        String textoC = JOptionPane.showInputDialog(null, "Ingrese el valor de c");
        String textoM = JOptionPane.showInputDialog(null, "Ingrese el valor de m (mayor que 1)");
        if (textoX == null || textoA == null || textoC == null || textoM == null) {
            return new ArrayList<>();
        }
        int xEntrada;
        int aEntrada;
        int cEntrada;
        int mEntrada;
        try {
            xEntrada = Integer.parseInt(textoX.trim());
            aEntrada = Integer.parseInt(textoA.trim());
            cEntrada = Integer.parseInt(textoC.trim());
            mEntrada = Integer.parseInt(textoM.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese solamente números enteros.");
            return new ArrayList<>();
        }
        int X = xEntrada;
        int a = aEntrada;
        int c = cEntrada;
        int m = mEntrada;
        if (X < 0 || a < 0 || c < 0 || m <= 1 || X >= m) {
            JOptionPane.showMessageDialog(null,
                    "X0, a y c no pueden ser negativos; m debe ser mayor que 1 y X0 menor que m.");
            return new ArrayList<>();
        }

        //la ecuacion es xi+1 = (a*x+c) modulo de (m)
        //la variable a es constante nunca cambia
        //la variable c es constante aditiva
        //la variable m es el modulo

        String palabrax = "";
        double ri = 0.0;
        ArrayList<Double> resultados = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            X = (int) (((long) a * X + c) % m);
            ri = (double) X / m;
            resultados.add(ri);
            palabrax = palabrax + "X" + i + " " + X + " R" + i + " " + ri + "\n";
        }
        JOptionPane.showMessageDialog(null, "Los valores de X son: " + palabrax);
        return resultados;
    }
}