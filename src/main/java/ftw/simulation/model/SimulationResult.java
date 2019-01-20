package ftw.simulation.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationResult {

    private Map<Date, BigDecimal> resultsMap;

    private List<BigDecimal> results;

    public SimulationResult(List<Date> dates, List<BigDecimal> results) {
        this.results = results;
        resultsMap = new HashMap<>();
        for (int i = 0; i < dates.size(); i++) {
            resultsMap.put(dates.get(i), results.get(i));
        }
    }

    public Map<Date, BigDecimal> getResults() {
        return resultsMap;
    }

    public BigDecimal getResult(int position) {
        return results.get(position);
    }

    public BigDecimal getFinalResult() {
        return results.get(results.size() - 1);
    }
}
