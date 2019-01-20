package ftw.simulation.model;

import ftw.strategy.model.exception.InvalidSimulationInitialValuesException;
import ftw.simulation.validator.SimulationInitialValuesValidator;

import java.math.BigDecimal;

public class SimulationInitialValues {

    private Integer start;

    private Integer end;

    public SimulationInitialValues(Integer start, Integer end) throws InvalidSimulationInitialValuesException {
        this.start = start;
        this.end = end;
        SimulationInitialValuesValidator.validateSimulationInitialValues(this);
    }

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }
}
