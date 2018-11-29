package stock.data.connection;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import stock.data.DataType;
import stock.data.connection.exception.DataConnectionException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class FileDataConnection implements IDataConnection<List<String>> {

    private String fileName;

    private List<String> rawData = new LinkedList<>();

    public void connect() throws DataConnectionException {
        try (Stream<String> stream = Files.lines(Paths.get(getClass().getClassLoader().getResource(fileName).toURI()))) {
            stream.forEach(line -> rawData.add(line));
        } catch (Exception e) {
            throw new DataConnectionException(e);
        }
    }

    public DataType getDataType() {
        return DataType.CSV;
    }

    public List<String> getRawData() {
        return rawData;
    }

    @Inject
    public void setFileName(@Named("fileConnectionFileName") String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

}
