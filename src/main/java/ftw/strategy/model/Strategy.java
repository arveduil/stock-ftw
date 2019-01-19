package ftw.strategy.model;

import ftw.strategy.DecisionType;
import ftw.strategy.model.exception.InvalidStrategyValuesException;
import ftw.strategy.model.validator.StrategyValidator;
import javafx.beans.property.*;

import java.math.BigDecimal;

public class Strategy {

    private ObjectProperty<Integer> checkInterval;
    private ObjectProperty<BigDecimal> change;
    private ObjectProperty<DecisionType> decisionType;
    private ObjectProperty<BigDecimal> investmentPercentage;
    private BooleanProperty isActiveStrategy;


    public Strategy() throws InvalidStrategyValuesException {
        this(0, BigDecimal.ZERO , DecisionType.BUY , BigDecimal.ZERO);
    }

    public Strategy(Integer checkInterval, BigDecimal change, DecisionType decisionType, BigDecimal investmentPercentage) throws InvalidStrategyValuesException {
        this.checkInterval = new SimpleObjectProperty<Integer>(checkInterval);
        this.change = new SimpleObjectProperty<BigDecimal>(change);
        this.decisionType = new SimpleObjectProperty<DecisionType>(decisionType);
        this.investmentPercentage = new SimpleObjectProperty<BigDecimal>(investmentPercentage);
        this.isActiveStrategy = new SimpleBooleanProperty(true);
        StrategyValidator.validate(this);
    }

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
