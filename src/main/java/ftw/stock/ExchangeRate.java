package ftw.stock;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.chart.XYChart;
import ftw.stock.data.reader.DataUnit;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExchangeRate {

    private ObjectProperty< BigDecimal> value;

    private ObjectProperty<Date> date;

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

    public ExchangeRate(BigDecimal value, Date date) {
        this.value = new SimpleObjectProperty<>(value);
        this.date = new SimpleObjectProperty<>(date);
    }

    public ExchangeRate(DataUnit unit) throws ParseException {
        Date date = dateFormat.parse(unit.getDate());
        BigDecimal value = new BigDecimal(unit.getValue());
        this.date = new SimpleObjectProperty<>(date);
        this.value = new SimpleObjectProperty<>(value);
    }

    public BigDecimal getValue() {
        return value.getValue();
    }

    public void setValue(BigDecimal value) {
        this.value.setValue(value);
    }

    public Date getDate() {
        return date.getValue();
    }

    public void setDate(Date date) {
        this.date.setValue(date);
    }

    public final ObjectProperty<BigDecimal> getValueProperty() {
        return value;
    }

    public XYChart.Data<String,Float> convertToData(){
        return new XYChart.Data<String, Float>(dateFormat.format(this.getDate()),this.getValue().floatValue());
    }

}
