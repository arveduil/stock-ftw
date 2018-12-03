package stock.data.reader;

import stock.data.connection.exception.InvalidDataFormatException;

import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class FileDataReader implements IDataReader<List<String>> {

    private Collection<DataUnit> dataUnits = new LinkedList<>();

    public void makeDataUnits(List<String> list) throws InvalidDataFormatException {
        try {
            for (String line : list) {
                String splitRecord[] = line.split(";");
                DataUnit unit = new DataUnit(splitRecord[0], splitRecord[1]);
                dataUnits.add(unit);
            }
        } catch (Exception e) {
            throw new InvalidDataFormatException(e);
        }
    }
    public Collection<DataUnit> getDataUnits() {
        return this.dataUnits;
    }
}
