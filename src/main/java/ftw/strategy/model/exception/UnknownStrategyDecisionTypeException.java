package ftw.strategy.model.exception;

public class UnknownStrategyDecisionTypeException extends Exception {

    private static final String MESSAGE = "Unknown strategy decision type";

    public UnknownStrategyDecisionTypeException() {
        super(MESSAGE);
    }
}
