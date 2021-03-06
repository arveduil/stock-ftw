package ftw.strategy.model.validator;

import ftw.strategy.model.Strategy;
import ftw.strategy.model.exception.InvalidStrategyValuesException;
import ftw.simulation.model.exception.NonNumericFormatException;

import java.math.BigDecimal;

public class StrategyValidator {

    public static boolean validate(Strategy strategy) throws InvalidStrategyValuesException {
        if (strategy.getCheckInterval() < 1) {
            throw new InvalidStrategyValuesException("Check interval cannot be lesser than 0, is: " + strategy.getCheckInterval().doubleValue());
        }
        if (!isFraction(strategy.getChange().abs())) {
            throw new InvalidStrategyValuesException("Absolute change must be between and 1, is: " + Math.abs(strategy.getChange().doubleValue()));
        }
        if (!isFraction(strategy.getInvestmentPercentage())) {
            throw new InvalidStrategyValuesException("Investment percentage must be between 0 and 1, is: " + strategy.getInvestmentPercentage().doubleValue());
        }

        return true;
    }

    private static boolean isFraction(BigDecimal decimal) {
        return decimal.compareTo(new BigDecimal(1.0)) < 0 && decimal.compareTo(new BigDecimal(0.0)) > 0;
    }

    public static boolean validateInputForStrategyInitialValues(String checkIntervalStringInput, String changeInput, String investmentPercentageInput) throws NonNumericFormatException {

        try{
            Integer checkInterval = Integer.parseInt(checkIntervalStringInput);
        }catch (NumberFormatException e){
            throw new NonNumericFormatException("Check Interval must have numeric value, is " + checkIntervalStringInput);
        }
        try{
            BigDecimal change= new BigDecimal(changeInput);
        }catch (NumberFormatException e){
            throw new NonNumericFormatException("Change must have numeric value, is " + changeInput);
        }

        try{
            BigDecimal investmentPercentage =  new BigDecimal(investmentPercentageInput);
        }catch (NumberFormatException e){
            throw new NonNumericFormatException("Investment percentage must have numeric value, is "+ investmentPercentageInput);
        }
    return true;
    }
}
