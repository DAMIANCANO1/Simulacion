package simulacion;

import java.awt.Component;

import javax.swing.JOptionPane;

public class AyudanteConfianza {
    public static int elegir(Component propietario, boolean include85) {
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
        String texto = JOptionPane.showInputDialog(propietario,
                "Seleccione el nivel de confianza:\n" + options);
        if (texto == null) {
            return -1;
        }
        try {
            int opcion = Integer.parseInt(texto.trim());
            int max = include85 ? 5 : 4;
            if (opcion >= 1 && opcion <= max) {
                return opcion - 1;
            }
        } catch (NumberFormatException excepcion) {
        }
        JOptionPane.showMessageDialog(propietario, "Ingrese una opcion valida.");
        return -1;
    }
}


