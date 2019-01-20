package ftw.simulation.validator;

import ftw.stock.ExchangeRate;
import ftw.simulation.model.exception.NonNumericFormatException;
import ftw.simulation.model.SimulationInitialValues;
import ftw.strategy.model.exception.InvalidSimulationInitialValuesException;

import java.util.List;

public class SimulationInitialValuesValidator {

    public static boolean validateSimulationInitialValues(SimulationInitialValues simulationInitialValues) throws InvalidSimulationInitialValuesException {
        if (simulationInitialValues.getEnd().compareTo(simulationInitialValues.getStart()) < 0) {
            throw new InvalidSimulationInitialValuesException("End value must be greater than start value " );
        }
        return true;
    }

    public static boolean validateInputForSimulationInitialValues(String startInput, String endInput) throws NonNumericFormatException, InvalidSimulationInitialValuesException {
        try {
            Integer start = new Integer(startInput);
        } catch (NumberFormatException e){
            throw new NonNumericFormatException("Start must have numeric value, is " + startInput);
        }
        try {
            Integer end = new Integer(endInput);
        } catch (NumberFormatException e){
            throw new NonNumericFormatException("End must have numeric value, is " + endInput);
        }

        return true;
    }

    public static boolean validateSimulationForData(List<ExchangeRate> rates, SimulationInitialValues values) throws InvalidSimulationInitialValuesException {
        if(values.getEnd() - values.getStart() > rates.size()){
            throw new InvalidSimulationInitialValuesException("Simulation range is greater than ammount of data.");
        }

        return true;
    }
}
