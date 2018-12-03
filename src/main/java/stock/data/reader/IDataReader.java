package stock.data.reader;

import java.text.ParseException;
import java.util.Collection;

public interface IDataReader<T> {

    void makeDataUnits(T list) throws ParseException;

    Collection<DataUnit> getDataUnits();
}
