package simulacion;

import javax.swing.JOptionPane;
import java.awt.Component;

public class InputHelper {
    public static Integer integer(Component owner, String message) {
        String text = JOptionPane.showInputDialog(owner, message);
        if (text == null) {
            return null;
        }
        try {
            return Integer.valueOf(text.trim());
        } catch (NumberFormatException error) {
            JOptionPane.showMessageDialog(owner, "Ingrese un entero valido.");
            return null;
        }
    }

    public static Double decimal(Component owner, String message) {
        String text = JOptionPane.showInputDialog(owner, message);
        if (text == null) {
            return null;
        }
        try {
            return Double.valueOf(text.trim());
        } catch (NumberFormatException error) {
            JOptionPane.showMessageDialog(owner, "Ingrese un numero valido.");
            return null;
        }
    }

    public static String text(Component owner, String message) {
        return JOptionPane.showInputDialog(owner, message);
    }
}
