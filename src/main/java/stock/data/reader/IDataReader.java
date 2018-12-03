package stock.data.reader;

import stock.data.connection.exception.DataConnectionException;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;

public interface IDataReader<T> {
    void makeDataUnits(T list) throws ParseException;
    Collection<DataUnit> getDataUnits();
}
