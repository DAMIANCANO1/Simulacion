package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PokerTest {
    public static String execute(Component owner, ArrayList<Double> data) {
        if (data == null || data.isEmpty()) {
            JOptionPane.showMessageDialog(owner, "No hay datos para realizar la prueba.");
            return null;
        }
        Integer decimals = chooseDecimals(owner);
        if (decimals == null) return null;
        int confidence = ConfidenceHelper.choose(owner, false);
        if (confidence < 0) return null;
        String[] categories = categories(decimals);
        double[] probabilities = probabilities(decimals);
        int[] observed = new int[categories.length];
        StringBuilder individual = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            double number = data.get(i);
            if (number < 0 || number >= 1) {
                JOptionPane.showMessageDialog(owner, "Todos los datos deben estar entre 0 y 1.");
                return null;
            }
            String digits = digits(number, decimals);
            int category = category(digits, decimals);
            observed[category]++;
            individual.append(formatNumber(number, decimals)).append(" = ")
                    .append(categories[category]).append("\n");
        }
        double chi = 0;
        StringBuilder report = new StringBuilder();
        report.append("PRUEBA DE POKER\n\nDecimales: ").append(decimals);
        report.append("\nClasificacion individual:\n").append(individual);
        report.append("\nCategoria  Oi  Ei  (Ei-Oi)^2/Ei\n");
        for (int i = 0; i < categories.length; i++) {
            double expected = probabilities[i] * data.size();
            double term = (expected - observed[i]) * (expected - observed[i]) / expected;
            chi += term;
            report.append(categories[i]).append("  ").append(observed[i]).append("  ")
                    .append(expected).append("  ").append(term).append("\n");
        }
        int df = categories.length - 1;
        double critical = chiCritical(df, confidence);
        if (critical < 0) {
            JOptionPane.showMessageDialog(owner, "No existe un valor critico para " + df + " grados de libertad.");
            return null;
        }
        String[] labels = {"80%","90%","95%","99%"};
        report.append("\nGrados de libertad = ").append(df);
        report.append("\nChi calculada = ").append(chi);
        report.append("\nChi critica = ").append(critical);
        report.append("\nNivel de confianza = ").append(labels[confidence]);
        report.append("\n\nHipotesis nula: los numeros son independientes.");
        if (chi < critical) report.append("\nDecision: No se rechaza H0.");
        else report.append("\nDecision: Se rechaza H0.");
        return report.toString();
    }

    private static Integer chooseDecimals(Component owner) {
        String text = javax.swing.JOptionPane.showInputDialog(owner,
                "Seleccione cantidad de decimales:\n1: 3\n2: 4\n3: 5");
        if (text == null) return null;
        try {
            int choice = Integer.parseInt(text.trim());
            if (choice == 1) return 3;
            if (choice == 2) return 4;
            if (choice == 3) return 5;
        } catch (NumberFormatException error) {
        }
        javax.swing.JOptionPane.showMessageDialog(owner, "Seleccione una opcion entre 1 y 3.");
        return null;
    }

    private static String digits(double number, int decimals) {
        String text = formatNumber(number, decimals);
        int dot = text.indexOf('.');
        if (dot >= 0) return text.substring(dot + 1);
        return "";
    }

    private static String formatNumber(double number, int decimals) {
        java.text.DecimalFormat formatter;
        if (decimals == 3) formatter = new java.text.DecimalFormat("0.000");
        else if (decimals == 4) formatter = new java.text.DecimalFormat("0.0000");
        else formatter = new java.text.DecimalFormat("0.00000");
        return formatter.format(number);
    }

    private static int category(String digits, int decimals) {
        int[] counts = new int[10];
        for (int i = 0; i < digits.length(); i++) counts[digits.charAt(i) - '0']++;
        int pairs = 0;
        int max = 0;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] == 2) pairs++;
            if (counts[i] > max) max = counts[i];
        }
        if (max == 1) return 0;
        if (max == 2 && pairs == 1) return 1;
        if (max == 2 && pairs == 2) return 2;
        if (max == 3 && pairs == 0) return decimals == 3 ? 2 : 3;
        if (max == 3 && pairs == 1) return 4;
        if (max == 4) return decimals == 4 ? 4 : 5;
        if (max == 5) return 6;
        return 0;
    }

    private static String[] categories(int decimals) {
        if (decimals == 3) return new String[]{"Todos diferentes","Un par","Tercia"};
        if (decimals == 4) return new String[]{"Todos diferentes","Un par","Dos pares","Tercia","Poker"};
        return new String[]{"Todos diferentes","Un par","Dos pares","Tercia","Tercia y par","Poker","Quintilla"};
    }

    private static double[] probabilities(int decimals) {
        if (decimals == 3) return new double[]{0.72,0.27,0.01};
        if (decimals == 4) return new double[]{0.5040,0.4320,0.0270,0.0360,0.0016};
        return new double[]{0.3024,0.5040,0.1080,0.0720,0.0090,0.0045,0.0001};
    }

    private static double chiCritical(int df, int confidence) {
        if (df == 2) {
            double[] values = {3.219,4.605,5.991,9.210};
            return values[confidence];
        }
        if (df == 4) {
            double[] values = {5.989,7.779,9.488,13.277};
            return values[confidence];
        }
        if (df == 6) {
            double[] values = {8.558,10.645,12.592,16.812};
            return values[confidence];
        }
        return -1;
    }
}
