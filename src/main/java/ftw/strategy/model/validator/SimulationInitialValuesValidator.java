package ftw.strategy.model.validator;

import ftw.stock.ExchangeRate;
import ftw.strategy.model.exception.NonnumericFormatException;
import ftw.strategy.model.SimulationInitialValues;
import ftw.strategy.model.exception.InvalidSimulationInitialValuesException;

import java.math.BigDecimal;
import java.util.List;

public class SimulationInitialValuesValidator {

    public static boolean validateSimulationInitialValues(SimulationInitialValues simulationInitialValues) throws InvalidSimulationInitialValuesException {
        if (simulationInitialValues.getBudget().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidSimulationInitialValuesException("Budget  cannot be lesser than 0, is: " + simulationInitialValues.getBudget().doubleValue());
        }
        if (simulationInitialValues.getEnd().compareTo(simulationInitialValues.getStart()) < 0) {
            throw new InvalidSimulationInitialValuesException("End value must be greater than start value " );
        }
        return true;
    }

    public static boolean validateInputForSimulationInitialValues(String budgetInput, String startInput, String endInput) throws NonnumericFormatException, InvalidSimulationInitialValuesException {
        BigDecimal budget;
        Integer start;
        Integer end;

        try {
             budget = new BigDecimal(budgetInput);
        }catch (NumberFormatException e){
            throw new NonnumericFormatException("Budget must have numeric value, is " + budgetInput);
        }
        try {
             start = new Integer(startInput);
        }catch (NumberFormatException e){
            throw new NonnumericFormatException("Start must have numeric value, is " + startInput);
        }
        try {
             end = new Integer(endInput);
        }catch (NumberFormatException e){
            throw new NonnumericFormatException("End must have numeric value, is " + endInput);
        }

        return true;
    }

    public static boolean validateSimulationForData(List<ExchangeRate> rates, SimulationInitialValues values) throws InvalidSimulationInitialValuesException {
        if(values.getEnd()-values.getStart() >rates.size()){
            throw new InvalidSimulationInitialValuesException("Simulation range is greater than ammount of data.");
        }

        return true;
    }
}
