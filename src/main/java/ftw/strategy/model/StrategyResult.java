package ftw.strategy.model;

import java.math.BigDecimal;

public class StrategyResult {

    private BigDecimal result;

    public StrategyResult(BigDecimal result) {
        this.result = result;
    }

    public BigDecimal getResult() {
        return result;
    }
}
