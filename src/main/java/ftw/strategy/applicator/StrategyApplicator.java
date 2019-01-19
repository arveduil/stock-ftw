package ftw.strategy.applicator;

import ftw.stock.ExchangeRate;
import ftw.strategy.DecisionType;
import ftw.strategy.model.Strategy;
import ftw.strategy.model.SimulationInitialValues;
import ftw.strategy.model.StrategyResult;
import ftw.strategy.model.exception.InvalidSimulationInitialValuesException;
import ftw.strategy.model.validator.SimulationInitialValuesValidator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrategyApplicator implements IStrategyApplicator {

    private List<ExchangeRate> data;

    private List<Strategy> strategies;

    private Map<Strategy, StrategyResult> strategyResults = new HashMap<Strategy, StrategyResult>();

    private BigDecimal budget;

    private SimulationInitialValues simulationInitialValues;

    public StrategyApplicator(List<ExchangeRate> data, List<Strategy> strategies, BigDecimal budget) {
        this.data = data;
        this.strategies = strategies;
       //this.simulationInitialValues = simulationInitialValues;
        this.budget = budget;//simulationInitialValues.getBudget();
       // SimulationInitialValuesValidator.validateSimulationForData(data,simulationInitialValues);
    }

    public void setData(List<ExchangeRate> data) {
        this.data = data;
    }

    public void setStrategies(List<Strategy> strategies) {
        this.strategies = strategies;
    }

    public void setInitialBudget(BigDecimal initialBudget) {
        this.budget = initialBudget;
    }

    public void applyStrategies() {
        for (Strategy strategy : strategies) {
            strategyResults.put(strategy, applyStrategy(strategy));
        }
    }

    public Map<Strategy, StrategyResult> getStrategyResults() {
        return strategyResults;
    }

    private StrategyResult applyStrategy(Strategy strategy) {
        StrategyResult strategyResult = null;
        Integer checkInterval = strategy.getCheckInterval();
        for (int i = 0; i < data.size() - checkInterval; i++) {
            BigDecimal startValue = data.get(i).getValue();
            BigDecimal endValue = data.get(i + checkInterval).getValue();
            BigDecimal change = endValue.subtract(startValue).divide(startValue, BigDecimal.ROUND_HALF_EVEN);
            if (change.compareTo(strategy.getChange()) > 0) {
                if (strategy.getDecisionType() == DecisionType.BUY) {
                    budget = budget.subtract(budget.multiply(strategy.getInvestmentPercentage().multiply(endValue)));
                } else if (strategy.getDecisionType() == DecisionType.SELL) {
                    budget = budget.add(budget.multiply(strategy.getInvestmentPercentage().multiply(endValue)));
                }

                strategyResult = new StrategyResult(budget);
            }
        }

        return strategyResult;
    }
}
