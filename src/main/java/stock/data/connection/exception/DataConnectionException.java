package stock.data.connection.exception;

public class DataConnectionException extends Exception {

    private Exception cause;

    public DataConnectionException() {

    }

    public DataConnectionException(Exception cause) {
        this.cause = cause;
    }
}
