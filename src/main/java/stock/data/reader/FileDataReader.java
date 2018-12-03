package stock.data.reader;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FileDataReader implements IDataReader<List<String>> {
    private Collection<DataUnit> dataUnits = new LinkedList<>();
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

    public void makeDataUnits(List<String> list) throws ParseException {
        for (String line :
                list) {
            String splitRecord[] = line.split(";");
            DataUnit unit = new DataUnit(splitRecord[0], splitRecord[1]);
            dataUnits.add(unit);
        }
    }

    public Collection<DataUnit> getDataUnits() {
        return this.dataUnits;
    }
}
