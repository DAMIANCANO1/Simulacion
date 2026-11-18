package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PruebaPoker {
    public static String ejecutar(Component propietario, ArrayList<Double> datos) {
        if (datos == null || datos.isEmpty()) {
            JOptionPane.showMessageDialog(propietario, "No hay datos para realizar la prueba.");
            return null;
        }
        Integer decimales = elegirDecimales(propietario);
        if (decimales == null) return null;
        int nivelConfianza = AyudanteConfianza.elegir(propietario, false);
        if (nivelConfianza < 0) return null;
        String[] categorias = categorias(decimales);
        double[] probabilidades = probabilidades(decimales);
        int[] observados = new int[categorias.length];
        StringBuilder detalleIndividual = new StringBuilder();
        for (int i = 0; i < datos.size(); i++) {
            double numero = datos.get(i);
            if (numero < 0 || numero >= 1) {
                JOptionPane.showMessageDialog(propietario, "Todos los datos deben estar entre 0 y 1.");
                return null;
            }
            String digitos = digitos(numero, decimales);
            int categoria = categoria(digitos, decimales);
            observados[categoria]++;
            detalleIndividual.append(formatearNumero(numero, decimales)).append(" = ")
                    .append(categorias[categoria]).append("\n");
        }
        double chiCalculada = 0;
        StringBuilder reporte = new StringBuilder();
        reporte.append("PRUEBA DE POKER\n\nDecimales: ").append(decimales);
        reporte.append("\nClasificacion detalleIndividual:\n").append(detalleIndividual);
        reporte.append("\nCategoria  Oi  Ei  (Ei-Oi)^2/Ei\n");
        for (int i = 0; i < categorias.length; i++) {
            double esperado = probabilidades[i] * datos.size();
            double termino = (esperado - observados[i]) * (esperado - observados[i]) / esperado;
            chiCalculada += termino;
            reporte.append(categorias[i]).append("  ").append(observados[i]).append("  ")
                    .append(esperado).append("  ").append(termino).append("\n");
        }
        int gradosLibertad = categorias.length - 1;
        double criticoValor = criticoChi(gradosLibertad, nivelConfianza);
        if (criticoValor < 0) {
            JOptionPane.showMessageDialog(propietario, "No existe un valor critico para " + gradosLibertad + " grados de libertad.");
            return null;
        }
        String[] etiquetas = {"80%","90%","95%","99%"};
        reporte.append("\nGrados de libertad = ").append(gradosLibertad);
        reporte.append("\nChi calculada = ").append(chiCalculada);
        reporte.append("\nChi critica = ").append(criticoValor);
        reporte.append("\nNivel de confianza = ").append(etiquetas[nivelConfianza]);
        reporte.append("\n\nHipotesis nula: los numeros son independientes.");
        if (chiCalculada < criticoValor) reporte.append("\nDecision: No se rechaza H0.");
        else reporte.append("\nDecision: Se rechaza H0.");
        return reporte.toString();
    }

    private static Integer elegirDecimales(Component propietario) {
        String texto = javax.swing.JOptionPane.showInputDialog(propietario,
                "Seleccione cantidad de decimales:\n1: 3\n2: 4\n3: 5");
        if (texto == null) return null;
        try {
            int opcion = Integer.parseInt(texto.trim());
            if (opcion == 1) return 3;
            if (opcion == 2) return 4;
            if (opcion == 3) return 5;
        } catch (NumberFormatException excepcion) {
        }
        javax.swing.JOptionPane.showMessageDialog(propietario, "Seleccione una opcion entre 1 y 3.");
        return null;
    }

    private static String digitos(double numero, int decimales) {
        String texto = formatearNumero(numero, decimales);
        int dot = texto.indexOf('.');
        if (dot >= 0) return texto.substring(dot + 1);
        return "";
    }

    private static String formatearNumero(double numero, int decimales) {
        java.text.DecimalFormat formateador;
        if (decimales == 3) formateador = new java.text.DecimalFormat("0.000");
        else if (decimales == 4) formateador = new java.text.DecimalFormat("0.0000");
        else formateador = new java.text.DecimalFormat("0.00000");
        return formateador.format(numero);
    }

    private static int categoria(String digitos, int decimales) {
        int[] counts = new int[10];
        for (int i = 0; i < digitos.length(); i++) counts[digitos.charAt(i) - '0']++;
        int pairs = 0;
        int max = 0;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] == 2) pairs++;
            if (counts[i] > max) max = counts[i];
        }
        if (max == 1) return 0;
        if (max == 2 && pairs == 1) return 1;
        if (max == 2 && pairs == 2) return 2;
        if (max == 3 && pairs == 0) return decimales == 3 ? 2 : 3;
        if (max == 3 && pairs == 1) return 4;
        if (max == 4) return decimales == 4 ? 4 : 5;
        if (max == 5) return 6;
        return 0;
    }

    private static String[] categorias(int decimales) {
        if (decimales == 3) return new String[]{"Todos diferentes","Un par","Tercia"};
        if (decimales == 4) return new String[]{"Todos diferentes","Un par","Dos pares","Tercia","Poker"};
        return new String[]{"Todos diferentes","Un par","Dos pares","Tercia","Tercia y par","Poker","Quintilla"};
    }

    private static double[] probabilidades(int decimales) {
        if (decimales == 3) return new double[]{0.72,0.27,0.01};
        if (decimales == 4) return new double[]{0.5040,0.4320,0.0270,0.0360,0.0016};
        return new double[]{0.3024,0.5040,0.1080,0.0720,0.0090,0.0045,0.0001};
    }

    private static double criticoChi(int gradosLibertad, int nivelConfianza) {
        if (gradosLibertad == 2) {
            double[] valores = {3.219,4.605,5.991,9.210};
            return valores[nivelConfianza];
        }
        if (gradosLibertad == 4) {
            double[] valores = {5.989,7.779,9.488,13.277};
            return valores[nivelConfianza];
        }
        if (gradosLibertad == 6) {
            double[] valores = {8.558,10.645,12.592,16.812};
            return valores[nivelConfianza];
        }
        return -1;
    }
}


