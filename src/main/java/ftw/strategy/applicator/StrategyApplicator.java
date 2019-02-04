package ftw.strategy.applicator;

import ftw.simulation.model.SimulationResult;
import ftw.stock.ExchangeRate;
import ftw.strategy.DecisionType;
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

    private SimulationResult result;

    public StrategyApplicator(List<ExchangeRate> data, List<Strategy> strategies)
            throws InvalidSimulationInitialValuesException {
        this.data = new ArrayList<>();
        this.data = data;
        this.strategies = strategies;
    }

    @Override
    public void applyStrategies() {
        List<BigDecimal> results = new ArrayList<>();

        List<Strategy> sortedStrategies = sortStrategiesByApplicationDate(strategies);
        BigDecimal budgetMainInitial = new BigDecimal(1);
        BigDecimal budgetOther = new BigDecimal(0);
        BigDecimal moneyToChange;

        for(int i= 0; i<data.size();i++){
            BigDecimal currentBudget= budgetMainInitial;
            for(Strategy strategy :strategies){
                if( i >= strategy.getCheckInterval() && (i - strategy.getCheckInterval()>= 0)){
                    BigDecimal startValue = data.get(i - strategy.getCheckInterval()).getValue();
                    BigDecimal endValue = data.get(i).getValue();
                    BigDecimal change = endValue.subtract(startValue).divide(startValue, BigDecimal.ROUND_HALF_EVEN);
                    if(strategy.getDecisionType().equals(DecisionType.SELL)){
                        if(change.multiply(strategy.getChange()).compareTo(BigDecimal.ZERO) > 0 && change.abs().compareTo(strategy.getChange()) > 0){
                            //results = updateResultsAfterStrategyApplication(results, strategy.apply(results.get(i), change), i);
                            moneyToChange = budgetMainInitial.multiply(strategy.getInvestmentPercentage());
                            budgetMainInitial =budgetMainInitial.subtract(moneyToChange);
                            budgetOther = moneyToChange.multiply(data.get(i).getValue());
                            currentBudget = budgetMainInitial;
                        }
                    }

                    if(strategy.getDecisionType().equals(DecisionType.BUY)){
                        if(change.multiply(strategy.getChange()).compareTo(BigDecimal.ZERO) > 0 && change.abs().compareTo(strategy.getChange()) > 0){
                            //results = updateResultsAfterStrategyApplication(results, strategy.apply(results.get(i), change), i);
                            moneyToChange = budgetOther.multiply(strategy.getInvestmentPercentage());
                            budgetOther =budgetOther.subtract(moneyToChange);
                            budgetMainInitial = moneyToChange.multiply(data.get(i).getValue());

                            currentBudget = budgetMainInitial;
                        }
                    }
                }
            }

            if(i==data.size()-1){
                currentBudget = budgetMainInitial.add(budgetOther.multiply(data.get(i).getValue()));
            }

            results.add(currentBudget);
        }

       // budgetMainInitial = budgetMainInitial.add(budgetOther.multiply(data.get(data.size()-1).getValue()));

        List<Date> dates = new LinkedList<>();
        for (ExchangeRate rates : data) {
            dates.add(rates.getDate());
        }
        this.result = new SimulationResult(dates, results);
    }

    private List<Strategy> sortStrategiesByApplicationDate(List<Strategy> strategies) {

        List<List<Strategy>> strategyDateBuckets = new LinkedList<>();
        for (ExchangeRate d: this.data) {
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
