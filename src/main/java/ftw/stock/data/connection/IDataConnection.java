package ftw.stock.data.connection;

import ftw.stock.data.DataType;
import ftw.stock.data.connection.exception.DataConnectionException;

import java.io.File;

public interface IDataConnection<T> {

    void connect(File file) throws DataConnectionException;

    DataType getDataType();

    T getRawData();

}
