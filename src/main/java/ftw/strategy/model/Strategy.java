package ftw.strategy.model;

import ftw.strategy.DecisionType;
import ftw.strategy.model.exception.InvalidStrategyValuesException;
import ftw.strategy.model.validator.StrategyValidator;

import java.math.BigDecimal;

public class Strategy {

    private Integer checkInterval;

    private BigDecimal change;

    private DecisionType decisionType;

    private BigDecimal investmentPercentage;

    public Strategy(Integer checkInterval, BigDecimal change, DecisionType decisionType, BigDecimal investmentPercentage) throws InvalidStrategyValuesException {
        this.checkInterval = checkInterval;
        this.change = change;
        this.decisionType = decisionType;
        this.investmentPercentage = investmentPercentage;
        StrategyValidator.validate(this);
    }

    public Integer getCheckInterval() {
        return checkInterval;
    }

    public BigDecimal getChange() {
        return change;
    }

    public DecisionType getDecisionType() {
        return decisionType;
    }

    public BigDecimal getInvestmentPercentage() {
        return investmentPercentage;
    }
}
