package ftw.strategy.applicator;

import ftw.simulation.model.SimulationResult;
import ftw.stock.ExchangeRate;
import ftw.strategy.model.Strategy;
import ftw.simulation.model.SimulationInitialValues;
import ftw.strategy.model.exception.InvalidSimulationInitialValuesException;
import ftw.simulation.validator.SimulationInitialValuesValidator;
import javafx.beans.property.IntegerProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class StrategyApplicator implements IStrategyApplicator {

    private List<ExchangeRate> data;

    private List<Strategy> strategies;

    private SimulationInitialValues simulationInitialValues;

    private SimulationResult result;

    public StrategyApplicator(List<ExchangeRate> data, List<Strategy> strategies, SimulationInitialValues simulationInitialValues)
            throws InvalidSimulationInitialValuesException {
        SimulationInitialValuesValidator.validateSimulationForData(data, simulationInitialValues);
        this.data = new ArrayList<>();
        for (int i = simulationInitialValues.getStart(); i <= simulationInitialValues.getEnd(); i++) {
            this.data.add(new ExchangeRate(data.get(i).getValue(), data.get(i).getDate()));
        }
        this.strategies = strategies;
        this.simulationInitialValues = simulationInitialValues;
    }

    @Override
    public void applyStrategies() {
        List<BigDecimal> results = new ArrayList<>();
        for (int i = simulationInitialValues.getStart(); i <= simulationInitialValues.getEnd(); i++) {
            results.add(new BigDecimal(1.0));
        }

        List<Strategy> sortedStrategies = sortStrategiesByApplicationDate(strategies);

        for (Strategy strategy : sortedStrategies) {
            Integer checkInterval = strategy.getCheckInterval();
            for (int i = checkInterval; i < data.size(); i++) {
                BigDecimal startValue = data.get(i - checkInterval).getValue();
                BigDecimal endValue = data.get(i).getValue();
                BigDecimal change = endValue.subtract(startValue).divide(startValue, BigDecimal.ROUND_HALF_EVEN);
                if (change.compareTo(strategy.getChange()) > 0) {
                    results = updateResultsAfterStrategyApplication(results, strategy.apply(results.get(i), change), i);
                    break;
                }
            }
        }

        List<Date> dates = new LinkedList<>();
        for (ExchangeRate rates : data) {
            dates.add(rates.getDate());
        }
        this.result = new SimulationResult(dates, results);
    }

    private List<Strategy> sortStrategiesByApplicationDate(List<Strategy> strategies) {

        List<List<Strategy>> strategyDateBuckets = new LinkedList<>();
        for (int i = simulationInitialValues.getStart(); i <= simulationInitialValues.getEnd(); i++) {
            strategyDateBuckets.add(new LinkedList<>());
        }

        for (Strategy strategy : strategies) {
            Integer checkInterval = strategy.getCheckInterval();
            for (int i = checkInterval; i < data.size(); i++) {
                BigDecimal startValue = data.get(i - checkInterval).getValue();
                BigDecimal endValue = data.get(i).getValue();
                BigDecimal change = endValue.subtract(startValue).divide(startValue, BigDecimal.ROUND_HALF_EVEN);
                if (change.compareTo(strategy.getChange()) > 0) {
                    strategyDateBuckets.get(i).add(strategy);
                    break;
                }
            }
        }

        List<Strategy> sortedStrategies = new LinkedList<>();
        for (List<Strategy> bucket : strategyDateBuckets) {
            sortedStrategies.addAll(bucket);
        }

        return sortedStrategies;
    }

    private List<BigDecimal> updateResultsAfterStrategyApplication(List<BigDecimal> currentResults,
                                                                   BigDecimal strategyApplicationResult,
                                                                   int strategyApplicationPosition) {
        for (int i = strategyApplicationPosition; i < currentResults.size(); i++) {
            currentResults.set(i, strategyApplicationResult);
        }

        return currentResults;
    }

    @Override
    public SimulationResult getSimulationResult() {
        return result;
    }
}
