package ftw.strategy.model;

import ftw.strategy.DecisionType;
import ftw.strategy.model.exception.InvalidStrategyValuesException;
import ftw.strategy.model.validator.StrategyValidator;
import javafx.beans.property.*;

import java.math.BigDecimal;

public abstract class Strategy {

    protected ObjectProperty<Integer> checkInterval;

    protected ObjectProperty<BigDecimal> change;

    protected ObjectProperty<DecisionType> decisionType;

    protected ObjectProperty<BigDecimal> investmentPercentage;

    protected BooleanProperty isActiveStrategy;

    public Strategy(Integer checkInterval, BigDecimal change, DecisionType decisionType, BigDecimal investmentPercentage) throws InvalidStrategyValuesException {
        this.checkInterval = new SimpleObjectProperty<>(checkInterval);
        this.change = new SimpleObjectProperty<>(change);
        this.decisionType = new SimpleObjectProperty<>(decisionType);
        this.investmentPercentage = new SimpleObjectProperty<>(investmentPercentage);
        this.isActiveStrategy = new SimpleBooleanProperty(true);
        StrategyValidator.validate(this);
    }

    public abstract BigDecimal apply(BigDecimal initial, BigDecimal change);

    public Integer getCheckInterval() {
        return checkInterval.getValue();
    }

    public BigDecimal getChange() {
        return change.getValue();
    }

    public DecisionType getDecisionType() {
        return decisionType.getValue();
    }

    public BigDecimal getInvestmentPercentage() {
        return investmentPercentage.getValue();
    }

    public ObjectProperty<Integer> getCheckIntervalProperty() {
        return checkInterval;
    }

    public ObjectProperty<BigDecimal> getChangeProperty() {
        return change;
    }

    public ObjectProperty<DecisionType> getDecisionTypeProperty() {
        return decisionType;
    }

    public ObjectProperty<BigDecimal> getInvestmentPercentageProperty() {
        return investmentPercentage;
    }

}
