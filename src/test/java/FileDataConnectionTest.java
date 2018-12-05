import org.junit.Before;
import org.junit.Test;
import ftw.stock.data.DataType;
import ftw.stock.data.connection.FileDataConnection;
import ftw.stock.data.connection.IDataConnection;
import ftw.stock.data.connection.exception.DataConnectionException;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class FileDataConnectionTest {

    private IDataConnection<List<String>> dataConnection;

    @Before
    public void init() {
        dataConnection = new FileDataConnection();
        dataConnection.setFileName("data.txt");
    }

    @Test
    public void dataTypeTest() {
        DataType dataType = dataConnection.getDataType();

        assertEquals(DataType.CSV, dataType);
    }

    @Test
    public void fileNameTest() {
        String fileName = dataConnection.getFileName();

        assertEquals("data.txt", fileName);
    }

    @Test(expected = DataConnectionException.class)
    public void dataConnectionExceptionTest() throws DataConnectionException {
        String badFileName = "data2.txt";
        dataConnection.setFileName(badFileName);
        dataConnection.connect();
    }

    @Test
    public void rawDataSizeTest() throws DataConnectionException {
        dataConnection.connect();
        List<String> rawData = dataConnection.getRawData();

        assertEquals(10, rawData.size());
    }

    @Test
    public void rawDataContentTest() throws DataConnectionException {
        dataConnection.connect();
        List<String> rawData = dataConnection.getRawData();

        List<String> expectedData = new LinkedList<>();
        expectedData.add("2018-11-01;282.7");
        expectedData.add("2018-11-02;265.2");
        expectedData.add("2018-11-03;245.7");
        expectedData.add("2018-11-04;255.2");
        expectedData.add("2018-11-05;276.9");
        expectedData.add("2018-11-06;301.2");
        expectedData.add("2018-11-07;303.2");
        expectedData.add("2018-11-08;235.5");
        expectedData.add("2018-11-09;256.5");
        expectedData.add("2018-11-10;253.5");

        assertArrayEquals(rawData.toArray(), expectedData.toArray());
    }
}
