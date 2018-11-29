package stock.data.connection;

import stock.data.DataType;

import java.util.List;

public class FileDataConnection implements IDataConnection<List<String>> {

    public void connect() {

    }

    public DataType getDataType() {
        return DataType.CSV;
    }

    public List<String> getRawData() {
        return null;
    }
}
