package ftw.stock.data.connection.exception;

public class InvalidDataFormatException extends Exception {

    private Exception cause;

    public InvalidDataFormatException() {

    }

    public InvalidDataFormatException(Exception cause) {
        this.cause = cause;
    }
}
