package ftw.stock.data.connection;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import ftw.stock.data.DataType;
import ftw.stock.data.connection.exception.DataConnectionException;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonFileDataConnection implements IDataConnection<JSONObject> {


    private StringBuilder rawDataBuilder = new StringBuilder();

    @Override
    public void connect(File file) throws DataConnectionException {
        try (Stream<String> stream = Files.lines(Paths.get(getClass().getClassLoader().getResource(file.getName()).toURI()))) {
            stream.forEach(line -> rawDataBuilder.append(line).append("\n"));
        } catch (Exception e) {
            throw new DataConnectionException(e);
        }
    }

    @Override
    public DataType getDataType() {
        return DataType.JSON;
    }

    @Override
    public JSONObject getRawData() {
        return new JSONObject(rawDataBuilder.toString());
    }

}
