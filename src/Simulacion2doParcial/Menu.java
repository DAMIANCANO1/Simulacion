package Simulacion2doParcial;

import javax.swing.*;
import java.util.ArrayList;

public class Menu {
    public static void main(String[] args) {
        ArrayList<Double> datos = seleccionarAlgoritmo();
        if (datos == null) {
            return;
        }
        if (datos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se generaron datos. Regresando al menú.");
            return;
        }

        seleccionarPrueba(datos);
    }

    public static ArrayList<Double> seleccionarAlgoritmo() {
        String texto = JOptionPane.showInputDialog(null, "Seleccione el algoritmo que desea realizar" +
                "\n1: Congruencial aditivo\n2: Congruencial multiplicativo" +
                "\n3: Congruencial cuadrático\n4: Productos medios" +
                "\n5: Cuadrados medios\n6: Multiplicador constante" +
                "\n7: Congruencial lineal\n8: Salir");
        if (texto == null) return null;
        int opcion;
        try {
            opcion = Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un número del 1 al 8.");
            return new ArrayList<>();
        }

        if (opcion == 8) {
            return null;
        }

        switch (opcion) {
            case 1:
                return CongroencialAditivo.generar();
            case 2:
                return CongroencialMultiplicativo.generar();
            case 3:
                return CongroencialCuadratico.generar();
            case 4:
                return ProductosMedios.generar();
            case 5:
                return CuadradosMedios.generar();
            case 6:
                return MultiplicadorConstante.generar();
            case 7:
                return lineal.generar();
            default:
                JOptionPane.showMessageDialog(null, "Opción no válida.");
                return null;
        }
    }

    public static void seleccionarPrueba(ArrayList<Double> datos) {
        while (true) {
            String texto = JOptionPane.showInputDialog(null,
                    "Los datos generados se utilizarán en todas las pruebas."
                            + "\nSeleccione la prueba que desea realizar"
                            + "\n1: Prueba de medias"
                            + "\n2: Prueba de varianza"
                            + "\n3: Prueba Chi-cuadrada"
                            + "\n4: Prueba Kolmogorov-Smirnov"
                            + "\n5: Prueba de corridas arriba y abajo"
                            + "\n6: Corridas arriba y abajo de la media"
                            + "\n7: Prueba de Poker"
                            + "\n8: Prueba de huecos"
                            + "\n9: Salir");
            if (texto == null) {
                return;
            }

            int opcion;
            try {
                opcion = Integer.parseInt(texto.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un número del 1 al 9.");
                continue;
            }

            switch (opcion) {
                case 1:
                    PruebaDeMedias.ejecutar(datos);
                    break;
                case 2:
                    Varianza.ejecutar(datos);
                    break;
                case 3:
                    PruebaChi2.ejecutar(datos);
                    break;
                case 4:
                    kolmogorov.ejecutar(datos);
                    break;
                case 5:
                    PruebaCorridas.ejecutar(datos);
                    break;
                case 6:
                    PruebaArribaAbajoMedia.ejecutar(datos);
                    break;
                case 7:
                    PruebaPoker.ejecutar(datos);
                    break;
                case 8:
                    PruebaHuecos.ejecutar(datos);
                    break;
                case 9:
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
            }
        }
    }
}
