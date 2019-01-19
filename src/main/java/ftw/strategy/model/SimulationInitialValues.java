package ftw.strategy.model;

import ftw.strategy.model.exception.InvalidSimulationInitialValuesException;
import ftw.strategy.model.validator.SimulationInitialValuesValidator;

import java.math.BigDecimal;

public class SimulationInitialValues {

    private BigDecimal budget;
    private Integer start;
    private Integer end;

    public SimulationInitialValues(BigDecimal budget, Integer start, Integer end) throws InvalidSimulationInitialValuesException {
        this.budget = budget;
        this.start = start;
        this.end = end;
        SimulationInitialValuesValidator.validateSimulationInitialValues(this);
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }
}
