package ftw.stock.data.reader;

import ftw.stock.data.connection.exception.InvalidDataFormatException;

import java.util.Collection;

public interface IDataReader<T> {

    void makeDataUnits(T list) throws InvalidDataFormatException;

    Collection<DataUnit> getDataUnits();
}
