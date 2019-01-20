package ftw.strategy.model;

import ftw.strategy.DecisionType;
import ftw.strategy.model.exception.InvalidStrategyValuesException;

import java.math.BigDecimal;

public class BuyStrategy extends Strategy {

    public BuyStrategy(Integer checkInterval, BigDecimal change, BigDecimal investmentPercentage) throws InvalidStrategyValuesException {
        super(checkInterval, change, DecisionType.BUY, investmentPercentage);
    }

    @Override
    public BigDecimal apply(BigDecimal initial, BigDecimal change) {
        return initial.subtract(initial.multiply(getInvestmentPercentage()).multiply(change));
    }
}
