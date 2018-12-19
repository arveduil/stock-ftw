package ftw.stock.data.reader;

import ftw.stock.data.connection.exception.InvalidDataFormatException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.LinkedList;

public class JsonDataReader implements IDataReader<JSONObject> {

    private Collection<DataUnit> dataUnits = new LinkedList<>();

    @Override
    public void makeDataUnits(JSONObject list) throws InvalidDataFormatException {
        try {
            JSONArray data = list.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject dataValue = data.getJSONObject(i);
                String date = dataValue.getString("date");
                String value = dataValue.getString("value");
                DataUnit unit = new DataUnit(date, value);
                dataUnits.add(unit);
            }
        } catch (Exception e) {
            throw new InvalidDataFormatException(e);
        }
    }

    @Override
    public Collection<DataUnit> getDataUnits() {
        return this.dataUnits;
    }
}
