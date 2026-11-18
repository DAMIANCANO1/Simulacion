package simulacion;

import java.awt.Component;

import javax.swing.JOptionPane;

public class ConfidenceHelper {
    public static int choose(Component owner, boolean include85) {
        String options = "1: 80%";
        if (include85) {
            options += "\n2: 85%";
            options += "\n3: 90%";
            options += "\n4: 95%";
            options += "\n5: 99%";
        } else {
            options += "\n2: 90%";
            options += "\n3: 95%";
            options += "\n4: 99%";
        }
        String text = JOptionPane.showInputDialog(owner,
                "Seleccione el nivel de confianza:\n" + options);
        if (text == null) {
            return -1;
        }
        try {
            int choice = Integer.parseInt(text.trim());
            int max = include85 ? 5 : 4;
            if (choice >= 1 && choice <= max) {
                return choice - 1;
            }
        } catch (NumberFormatException error) {
        }
        JOptionPane.showMessageDialog(owner, "Ingrese una opcion valida.");
        return -1;
    }
}
