package simulacion;

import javax.swing.JOptionPane;
import java.awt.Component;

public class AyudanteEntrada {
    public static Integer entero(Component propietario, String mensaje) {
        String texto = JOptionPane.showInputDialog(propietario, mensaje);
        if (texto == null) {
            return null;
        }
        try {
            return Integer.valueOf(texto.trim());
        } catch (NumberFormatException excepcion) {
            JOptionPane.showMessageDialog(propietario, "Ingrese un entero valido.");
            return null;
        }
    }

    public static Double decimal(Component propietario, String mensaje) {
        String texto = JOptionPane.showInputDialog(propietario, mensaje);
        if (texto == null) {
            return null;
        }
        try {
            return Double.valueOf(texto.trim());
        } catch (NumberFormatException excepcion) {
            JOptionPane.showMessageDialog(propietario, "Ingrese un numero valido.");
            return null;
        }
    }

    public static String texto(Component propietario, String mensaje) {
        return JOptionPane.showInputDialog(propietario, mensaje);
    }
}


