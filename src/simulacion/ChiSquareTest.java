package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ChiSquareTest {
    private static final int[] DEGREES = {
        1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,
        21,22,23,24,25,26,27,28,29,30,40,50,60,70,80,90,100
    };
    private static final double[][] TABLE = {
        {2.706,3.841,5.024,6.635,7.879,10.828},{4.605,5.991,7.378,9.210,10.597,13.816},
        {6.251,7.815,9.348,11.345,12.838,16.266},{7.779,9.488,11.143,13.277,14.860,18.467},
        {9.236,11.070,12.833,15.086,16.750,20.515},{10.645,12.592,14.449,16.812,18.548,22.458},
        {12.017,14.067,16.013,18.475,20.278,24.322},{13.362,15.507,17.535,20.090,21.955,26.124},
        {14.684,16.919,19.023,21.666,23.589,27.877},{15.987,18.307,20.483,23.209,25.188,29.588},
        {17.275,19.675,21.920,24.725,26.757,31.264},{18.549,21.026,23.337,26.217,28.300,32.909},
        {19.812,22.362,24.736,27.688,29.819,34.528},{21.064,23.685,26.119,29.141,31.319,36.123},
        {22.307,24.996,27.488,30.578,32.801,37.697},{23.542,26.296,28.845,32.000,34.267,39.252},
        {24.769,27.587,30.191,33.409,35.718,40.790},{25.989,28.869,31.526,34.805,37.156,42.312},
        {27.204,30.144,32.852,36.191,38.582,43.820},{28.412,31.410,34.170,37.566,39.997,45.315},
        {29.615,32.671,35.479,38.932,41.401,46.797},{30.813,33.924,36.781,40.289,42.796,48.268},
        {32.007,35.172,38.076,41.638,44.181,49.728},{33.196,36.415,39.364,42.980,45.559,51.179},
        {34.382,37.652,40.646,44.314,46.928,52.620},{35.563,38.885,41.923,45.642,48.290,54.052},
        {36.741,40.113,43.195,46.963,49.645,55.476},{37.916,41.337,44.461,48.278,50.993,56.892},
        {39.087,42.557,45.722,49.588,52.336,58.301},{40.256,43.773,46.979,50.892,53.672,59.703},
        {51.805,55.758,59.342,63.691,66.766,73.402},{63.167,67.505,71.420,76.154,79.490,86.661},
        {74.397,79.082,83.998,88.379,91.952,99.607},{85.527,90.531,95.023,100.425,104.215,112.317},
        {96.578,101.879,106.629,112.325,116.321,124.839},{107.565,113.145,118.136,124.116,128.299,137.708},
        {118.498,124.342,129.561,135.807,140.169,149.449}
    };

    public static String execute(Component owner, ArrayList<Double> data) {
        if (data == null || data.size() < 2) {
            JOptionPane.showMessageDialog(owner, "Se necesitan al menos 2 datos.");
            return null;
        }
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) < 0 || data.get(i) > 1) {
                JOptionPane.showMessageDialog(owner, "Los datos deben estar entre 0 y 1.");
                return null;
            }
        }
        int confidence = ConfidenceHelper.choose(owner, false);
        if (confidence < 0) return null;
        int n = data.size();
        int intervals = (int) Math.sqrt(n);
        if (intervals < 2) intervals = 2;
        int df = intervals - 1;
        int row = findRow(df);
        if (row < 0) {
            JOptionPane.showMessageDialog(owner, "No existe una fila para " + df + " grados de libertad.");
            return null;
        }
        int[] observed = new int[intervals];
        for (int i = 0; i < data.size(); i++) {
            int interval = (int) (data.get(i) * intervals);
            if (interval >= intervals) interval = intervals - 1;
            observed[interval]++;
        }
        double expected = (double) n / intervals;
        double chi = 0;
        StringBuilder report = new StringBuilder();
        report.append("PRUEBA CHI-CUADRADA\n\nDatos utilizados:\n");
        appendData(report, data);
        report.append("\nIntervalos = sqrt(n) = ").append(intervals);
        report.append("\nEsperado por intervalo = ").append(expected);
        report.append("\nGrados de libertad = ").append(df);
        report.append("\n\nIntervalo  Oi  Ei  (Oi-Ei)^2/Ei\n");
        for (int i = 0; i < intervals; i++) {
            double lower = (double) i / intervals;
            double upper = (double) (i + 1) / intervals;
            double term = (observed[i] - expected) * (observed[i] - expected) / expected;
            chi += term;
            report.append("[").append(lower).append(", ").append(upper).append(")  ")
                    .append(observed[i]).append("  ").append(expected).append("  ").append(term).append("\n");
        }
        int[] columns = {0, 1, 2, 4};
        double critical = TABLE[row][columns[confidence]];
        String[] labels = {"80%", "90%", "95%", "99%"};
        report.append("\nChi calculada = ").append(chi);
        report.append("\nValor critico = ").append(critical);
        report.append("\nNivel de confianza = ").append(labels[confidence]);
        report.append("\n\nHipotesis nula: los numeros son uniformes.");
        if (chi < critical) report.append("\nDecision: No se rechaza H0.");
        else report.append("\nDecision: Se rechaza H0.");
        return report.toString();
    }

    public static int findRow(int df) {
        for (int i = 0; i < DEGREES.length; i++) {
            if (DEGREES[i] == df) return i;
        }
        return -1;
    }

    private static void appendData(StringBuilder report, ArrayList<Double> data) {
        for (int i = 0; i < data.size(); i++) {
            report.append(data.get(i));
            if (i + 1 < data.size()) report.append(", ");
        }
    }
}
