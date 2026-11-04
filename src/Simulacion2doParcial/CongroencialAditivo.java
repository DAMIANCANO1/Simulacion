package Simulacion2doParcial;

import javax.swing.*;
import java.util.ArrayList;

public class CongroencialAditivo {
    public static ArrayList<Double> generar() {
        //ecuacion es xi = (xi-1 + xi-n) modulo (m)
        //minimo se requieren minimo 5 datos de indices Xi

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

        //se genera un bucle infinito ya que no sabemos hasta cuando el usuario dejara de
        //ingresar numeros y el usuario tiene el poder de decidir que ya no desea ingresar mas
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
                    //continue sirve para que el ciclo while continue y no se salga del ciclo
                    //si en caso aun no se completa nos numeros minimos requeridos
                    continue;
                }
                //si se completan los numeros minimos requeridos se sale del ciclo while
                //asumiendo que el usuario ingreso mas de 5 numeros obligatorios para funcionar
                break;
            } else if (numero >= 0 && numero < m) {
                numerosIngresados.add(numero);
                contador++;
            } else {
                JOptionPane.showMessageDialog(null, "X debe estar entre 0 y m - 1.");
            }

        }
        //declaramos la longitud de nuestra ARRAYL tomando como variable N
        int n = numerosIngresados.size();

        String palabrax = "";
        ArrayList<Double> resultados = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            //el contador sigue subiendo unicamente para mejor comprension al
            //momento de imprimir en pantalla
            contador++;
            //la ecuacion dice que queremos obtener una posicion antes de la actual
            //luego le sumamos la cantidad de numeros que hay en el arraylist y le
            //restamos N
            int posicionAnterior = numerosIngresados.size() - 1;
            int posicionHaceN = numerosIngresados.size() - n;
            int x = (numerosIngresados.get(posicionAnterior)
                    + numerosIngresados.get(posicionHaceN)) % m;
            //creamos la variable r para tener nuestros decimales
            //donde x se divide entre la variable m que ingresa el usuario y se le resta 1
            double r = (double) x / (m - 1);
            resultados.add(r);
            //el String unicamente es para ir guardando los resultados y al final mostrarlos en un JOptionPane
            palabrax += "X" + contador + " = " + x + " r" + contador + " = " + r + "\n";
            //agregamos x a nuestro array para la siguiente iteracion
            numerosIngresados.add(x);

        }
        JOptionPane.showMessageDialog(null, palabrax);
        return resultados;
    }
}
