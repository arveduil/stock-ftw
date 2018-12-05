package ftw.stock.data.connection;

import ftw.stock.data.DataType;
import ftw.stock.data.connection.exception.DataConnectionException;

public interface IDataConnection<T> {

    void connect() throws DataConnectionException;

    DataType getDataType();

    T getRawData();

    void setFileName(String fileName);

    String getFileName();
}
