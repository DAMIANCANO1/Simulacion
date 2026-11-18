package simulacion;

import java.util.ArrayList;

public class GenerationResult {
    private final ArrayList<Double> values;
    private final String report;

    public GenerationResult(ArrayList<Double> values, String report) {
        this.values = values;
        this.report = report;
    }

    public ArrayList<Double> getValues() {
        return values;
    }

    public String getReport() {
        return report;
    }
}
