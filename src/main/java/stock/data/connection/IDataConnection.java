package stock.data.connection;

import stock.data.DataType;
import stock.data.connection.exception.DataConnectionException;

public interface IDataConnection<T> {

    void connect() throws DataConnectionException;

    DataType getDataType();

    T getRawData();

    void setFileName(String fileName);

    String getFileName();
}
