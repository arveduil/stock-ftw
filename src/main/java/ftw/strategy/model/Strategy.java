package ftw.strategy.model;

import ftw.strategy.DecisionType;

import java.math.BigDecimal;

public class Strategy {

    private Integer checkInterval;

    private BigDecimal change;

    private DecisionType decisionType;

    private BigDecimal investmentPercentage;

    public Strategy(Integer checkInterval, BigDecimal change, DecisionType decisionType, BigDecimal investmentPercentage) {
        this.checkInterval = checkInterval;
        this.change = change;
        this.decisionType = decisionType;
        this.investmentPercentage = investmentPercentage;
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
