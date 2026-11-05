package Simulacion2doParcial;

import javax.swing.*;
import java.util.ArrayList;

public class PruebaDeMedias {
    public static void ejecutar(ArrayList<Double> numerosIngresados) {
        if (numerosIngresados == null || numerosIngresados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay datos para realizar la prueba.");
            return;
        }

        double suma = 0.0;
        for (double numero : numerosIngresados) {
            suma += numero;
        }

        int n = numerosIngresados.size();
        double media = suma / n;
        JOptionPane.showMessageDialog(null, "La media es: " + media);

        int opcion;
        while (true) {
            String texto = JOptionPane.showInputDialog(null,
                    "Ingrese el nivel de confianza"
                            + "\n1: 80%"
                            + "\n2: 85%"
                            + "\n3: 90%"
                            + "\n4: 95%"
                            + "\n5: 99%");
            if (texto == null) {
                return;
            }
            try {
                opcion = Integer.parseInt(texto.trim());
                if (opcion >= 1 && opcion <= 5) {
                    break;
                }
            } catch (NumberFormatException e) {
                // Se muestra el mensaje de error debajo.
            }
            JOptionPane.showMessageDialog(null, "Ingrese un número del 1 al 5.");
        }

        double z;
        if (opcion == 1) {
            z = 1.28;
        } else if (opcion == 2) {
            z = 1.44;
        } else if (opcion == 3) {
            z = 1.64;
        } else if (opcion == 4) {
            z = 1.96;
        } else {
            z = 2.58;
        }
        double multi = 1.0 / Math.sqrt(12 * n);
        double limiteInferior = 0.5 - (z * multi);
        double limiteSuperior = 0.5 + (z * multi);

        JOptionPane.showMessageDialog(null, "Límite inferior: " + limiteInferior +
                "\nLímite superior: " + limiteSuperior);

        if (media >= limiteInferior && media <= limiteSuperior) {
            JOptionPane.showMessageDialog(null, "HIPÓTESIS NULA");
        } else {
            JOptionPane.showMessageDialog(null, "HIPÓTESIS ALTERNATIVA");
        }
    }

}
