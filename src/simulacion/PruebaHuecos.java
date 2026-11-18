package simulacion;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PruebaHuecos {
    public static String ejecutar(Component propietario, ArrayList<Double> datos) {
        if (datos == null || datos.size() < 3) {
            JOptionPane.showMessageDialog(propietario, "Se necesitan al menos 3 datos.");
            return null;
        }
        Double alfa = AyudanteEntrada.decimal(propietario, "Ingrese el limite inferior alfa");
        if (alfa == null) return null;
        Double beta = AyudanteEntrada.decimal(propietario, "Ingrese el limite superior beta");
        if (beta == null) return null;
        if (alfa < 0 || alfa >= beta || beta > 1) {
            JOptionPane.showMessageDialog(propietario, "El intervalo debe cumplir 0 <= alfa < beta <= 1.");
            return null;
        }
        int nivelConfianza = AyudanteConfianza.elegir(propietario, false);
        if (nivelConfianza < 0) return null;
        ArrayList<Integer> secuencia = new ArrayList<Integer>();
        for (int i = 0; i < datos.size(); i++) {
            double valor = datos.get(i);
            if (valor < 0 || valor > 1) {
                JOptionPane.showMessageDialog(propietario, "Todos los datos deben estar entre 0 y 1.");
                return null;
            }
            if (valor > alfa && valor < beta) secuencia.add(1);
            else secuencia.add(0);
        }
        ArrayList<Integer> huecos = new ArrayList<Integer>();
        int anterior = -1;
        for (int i = 0; i < secuencia.size(); i++) {
            if (secuencia.get(i) == 1) {
                if (anterior >= 0) huecos.add(i - anterior - 1);
                anterior = i;
            }
        }
        if (huecos.isEmpty()) {
            JOptionPane.showMessageDialog(propietario, "No se encontraron dos unos dentro del intervalo.");
            return null;
        }
        int[] observados = new int[6];
        for (int i = 0; i < huecos.size(); i++) {
            int hueco = huecos.get(i);
            if (hueco >= 5) observados[5]++;
            else observados[hueco]++;
        }
        int cantidad = huecos.size();
        double probabilidad = beta - alfa;
        double probabilidadComplementaria = 1 - probabilidad;
        double chiCalculada = 0;
        double[] esperado = new double[6];
        StringBuilder reporte = new StringBuilder();
        reporte.append("PRUEBA DE HUECOS\n\nDatos utilizados:\n");
        agregarDatos(reporte, datos);
        reporte.append("\nIntervalo abierto: (").append(alfa).append(", ").append(beta).append(")");
        reporte.append("\nProbabilidad probabilidad = beta-alfa = ").append(probabilidad);
        reporte.append("\nSecuencia: ");
        for (int i = 0; i < secuencia.size(); i++) reporte.append(secuencia.get(i));
        reporte.append("\nHuecos encontrados: ");
        agregarEnteros(reporte, huecos);
        reporte.append("\n\nTamano  Oi  Ei  (Oi-Ei)^2/Ei\n");
        for (int i = 0; i < 6; i++) {
            if (i < 5) {
                esperado[i] = cantidad * probabilidad;
                for (int j = 0; j < i; j++) esperado[i] *= probabilidadComplementaria;
            } else {
                esperado[i] = cantidad;
                for (int j = 0; j < 5; j++) esperado[i] *= probabilidadComplementaria;
            }
            double termino = (observados[i] - esperado[i]) * (observados[i] - esperado[i]) / esperado[i];
            chiCalculada += termino;
            reporte.append(i < 5 ? String.valueOf(i) : "5 o mas").append("  ")
                    .append(observados[i]).append("  ").append(esperado[i]).append("  ").append(termino).append("\n");
        }
        int gradosLibertad = 5;
        int fila = PruebaChiCuadrada.buscarFila(gradosLibertad);
        if (fila < 0) {
            JOptionPane.showMessageDialog(propietario, "No existe una fila para 5 grados de libertad.");
            return null;
        }
        double criticoValor = criticoHuecos(nivelConfianza);
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

    private static double criticoHuecos(int nivelConfianza) {
        double[] valoresOchenta = {1.642,3.219,4.642,5.989,7.289,8.558,9.803,11.030,12.242,13.442};
        double[] valoresNoventa = {2.706,4.605,6.251,7.779,9.236,10.645,12.017,13.362,14.684,15.987};
        double[] valoresNoventaCinco = {3.841,5.991,7.815,9.488,11.070,12.592,14.067,15.507,16.919,18.307};
        double[] valoresNoventaNueve = {6.635,9.210,11.345,13.277,15.086,16.812,18.475,20.090,21.666,23.209};
        if (nivelConfianza == 0) return valoresOchenta[5];
        if (nivelConfianza == 1) return valoresNoventa[5];
        if (nivelConfianza == 2) return valoresNoventaCinco[5];
        return valoresNoventaNueve[5];
    }

    private static void agregarDatos(StringBuilder reporte, ArrayList<Double> datos) {
        for (int i = 0; i < datos.size(); i++) {
            reporte.append(datos.get(i));
            if (i + 1 < datos.size()) reporte.append(", ");
        }
    }

    private static void agregarEnteros(StringBuilder reporte, ArrayList<Integer> datos) {
        for (int i = 0; i < datos.size(); i++) {
            reporte.append(datos.get(i));
            if (i + 1 < datos.size()) reporte.append(", ");
        }
    }
}


