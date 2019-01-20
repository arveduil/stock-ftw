package ftw.strategy.model;

import ftw.strategy.DecisionType;
import ftw.strategy.model.exception.InvalidStrategyValuesException;
import ftw.strategy.model.exception.UnknownStrategyDecisionTypeException;

import java.math.BigDecimal;

public class StrategyFactory {

    public static Strategy createStrategy(Integer checkInterval, BigDecimal change, DecisionType decisionType, BigDecimal investmentPercentage)
            throws InvalidStrategyValuesException, UnknownStrategyDecisionTypeException {
        if (decisionType == DecisionType.BUY) {
            return new BuyStrategy(checkInterval, change, investmentPercentage);
        } else if (decisionType == DecisionType.SELL) {
            return new SellStrategy(checkInterval, change, investmentPercentage);
        }

        throw new UnknownStrategyDecisionTypeException();
    }
}
