package ftw.strategy.model;

import ftw.strategy.DecisionType;
import ftw.strategy.model.exception.InvalidStrategyValuesException;

import java.math.BigDecimal;

public class SellStrategy extends Strategy {

    public SellStrategy(Integer checkInterval, BigDecimal change, BigDecimal investmentPercentage) throws InvalidStrategyValuesException {
        super(checkInterval, change, DecisionType.SELL, investmentPercentage);
    }

    @Override
    public BigDecimal apply(BigDecimal initial, BigDecimal change) {
        return initial.add(initial.multiply(getInvestmentPercentage().multiply(change)));
    }
}
