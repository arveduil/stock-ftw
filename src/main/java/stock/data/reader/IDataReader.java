package stock.data.reader;

import stock.data.connection.exception.InvalidDataFormatException;

import java.text.ParseException;
import java.util.Collection;

public interface IDataReader<T> {

    void makeDataUnits(T list) throws InvalidDataFormatException;

    Collection<DataUnit> getDataUnits();
}
