import javafx.scene.chart.XYChart;
import org.junit.Test;
import stock.ExchangeRate;
import stock.data.DataType;
import stock.data.reader.DataUnit;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ExchangeRateTest {
    @Test
    public void createExchangeRateFromStringTest() throws ParseException {
        DataUnit unit = new DataUnit("2018-11-05", "21.47");
        ExchangeRate rate = new ExchangeRate(unit);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        assertEquals("2018-11-05",dateFormat.format(rate.getDate()));
        assertEquals(new BigDecimal("21.47"),rate.getValue());
    }

    @Test
    public void convertExchangeRateToData() throws ParseException {
        XYChart.Data<String,Float> expectedData = new XYChart.Data<String,Float>("2018-11-05",21.47f);
        DataUnit unit = new DataUnit("2018-11-05", "21.47");
        ExchangeRate rate = new ExchangeRate(unit);

        XYChart.Data<String,Float> data = rate.convertToData();

        assertEquals(expectedData.getXValue(), data.getXValue());
        assertEquals(expectedData.getYValue(), data.getYValue());
    }
}
