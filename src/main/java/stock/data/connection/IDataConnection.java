package stock.data.connection;

import stock.data.DataType;

public interface IDataConnection<T> {

    void connect();

    DataType getDataType();

    T getRawData();
}
