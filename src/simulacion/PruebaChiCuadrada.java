package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PruebaChiCuadrada {
    private static final int[] GRADOSLIBERTAD = {
        1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,
        21,22,23,24,25,26,27,28,29,30,40,50,60,70,80,90,100
    };
    private static final double[][] TABLA = {
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

    public static String ejecutar(Component propietario, ArrayList<Double> datos) {
        if (datos == null || datos.size() < 2) {
            JOptionPane.showMessageDialog(propietario, "Se necesitan al menos 2 datos.");
            return null;
        }
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i) < 0 || datos.get(i) > 1) {
                JOptionPane.showMessageDialog(propietario, "Los datos deben estar entre 0 y 1.");
                return null;
            }
        }
        int nivelConfianza = AyudanteConfianza.elegir(propietario, false);
        if (nivelConfianza < 0) return null;
        int cantidadDatos = datos.size();
        int intervalos = (int) Math.sqrt(cantidadDatos);
        if (intervalos < 2) intervalos = 2;
        int gradosLibertad = intervalos - 1;
        int fila = buscarFila(gradosLibertad);
        if (fila < 0) {
            JOptionPane.showMessageDialog(propietario, "No existe una fila para " + gradosLibertad + " grados de libertad.");
            return null;
        }
        int[] observados = new int[intervalos];
        for (int i = 0; i < datos.size(); i++) {
            int intervalo = (int) (datos.get(i) * intervalos);
            if (intervalo >= intervalos) intervalo = intervalos - 1;
            observados[intervalo]++;
        }
        double esperado = (double) cantidadDatos / intervalos;
        double chiCalculada = 0;
        StringBuilder reporte = new StringBuilder();
        reporte.append("PRUEBA CHI-CUADRADA\n\nDatos utilizados:\n");
        agregarDatos(reporte, datos);
        reporte.append("\nIntervalos = sqrt(cantidadDatos) = ").append(intervalos);
        reporte.append("\nEsperado por intervalo = ").append(esperado);
        reporte.append("\nGrados de libertad = ").append(gradosLibertad);
        reporte.append("\n\nIntervalo  Oi  Ei  (Oi-Ei)^2/Ei\n");
        for (int i = 0; i < intervalos; i++) {
            double limiteInferior = (double) i / intervalos;
            double limiteSuperior = (double) (i + 1) / intervalos;
            double termino = (observados[i] - esperado) * (observados[i] - esperado) / esperado;
            chiCalculada += termino;
            reporte.append("[").append(limiteInferior).append(", ").append(limiteSuperior).append(")  ")
                    .append(observados[i]).append("  ").append(esperado).append("  ").append(termino).append("\n");
        }
        int[] columnas = {0, 1, 2, 4};
        double criticoValor = TABLA[fila][columnas[nivelConfianza]];
        String[] etiquetas = {"80%", "90%", "95%", "99%"};
        reporte.append("\nChi calculada = ").append(chiCalculada);
        reporte.append("\nValor critico = ").append(criticoValor);
        reporte.append("\nNivel de confianza = ").append(etiquetas[nivelConfianza]);
        reporte.append("\n\nHipotesis nula: los numeros son uniformes.");
        if (chiCalculada < criticoValor) reporte.append("\nDecision: No se rechaza H0.");
        else reporte.append("\nDecision: Se rechaza H0.");
        return reporte.toString();
    }

    public static int buscarFila(int gradosLibertad) {
        for (int i = 0; i < GRADOSLIBERTAD.length; i++) {
            if (GRADOSLIBERTAD[i] == gradosLibertad) return i;
        }
        return -1;
    }

    private static void agregarDatos(StringBuilder reporte, ArrayList<Double> datos) {
        for (int i = 0; i < datos.size(); i++) {
            reporte.append(datos.get(i));
            if (i + 1 < datos.size()) reporte.append(", ");
        }
    }
}


