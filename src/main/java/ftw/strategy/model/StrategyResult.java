package ftw.strategy.model;

import javafx.beans.property.SimpleObjectProperty;

import java.math.BigDecimal;

public class StrategyResult {
    private SimpleObjectProperty<BigDecimal> result;

    public StrategyResult(BigDecimal result) {
        this.result =   new SimpleObjectProperty<BigDecimal>(result);
    }

    public BigDecimal getResult() {
        return result.getValue();
    }

    public SimpleObjectProperty<BigDecimal> getResultProperty() {
        return result;
    }

}
