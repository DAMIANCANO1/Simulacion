package simulacion;

import java.util.ArrayList;

public class ResultadoGeneracion {
    private final ArrayList<Double> valores;
    private final String reporte;

    public ResultadoGeneracion(ArrayList<Double> valores, String reporte) {
        this.valores = valores;
        this.reporte = reporte;
    }

    public ArrayList<Double> obtenerValores() {
        return valores;
    }

    public String obtenerReporte() {
        return reporte;
    }
}


