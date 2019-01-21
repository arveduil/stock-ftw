package ftw.stock.data.connection;

import ftw.stock.data.DataType;
import ftw.stock.data.connection.exception.DataConnectionException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class XlsFileDataConnection implements IDataConnection<List<String>> {

private List<String> rawData = new LinkedList<>();


public void connect(File file) throws DataConnectionException {
        try (Stream<String> stream = Files.lines(Paths.get(getClass().getClassLoader().getResource(file.getName()).toURI()))) {
        stream.forEach(line -> rawData.add(line));
        } catch (Exception e) {
        throw new DataConnectionException(e);
        }
        }

public DataType getDataType() {
        return DataType.XLS;
        }

public List<String> getRawData() {
        return rawData;
        }


}
