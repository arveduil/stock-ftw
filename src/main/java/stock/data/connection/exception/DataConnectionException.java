package stock.data.connection.exception;

import javax.xml.crypto.Data;

public class DataConnectionException extends Exception {

    private Exception cause;

    public DataConnectionException() {

    }

    public DataConnectionException(Exception cause) {
        this.cause = cause;
    }
}
