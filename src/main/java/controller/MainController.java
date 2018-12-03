package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import stock.ExchangeRate;
import stock.data.connection.IDataConnection;
import stock.data.connection.exception.DataConnectionException;
import stock.data.reader.DataUnit;
import stock.data.reader.IDataReader;
import view.HoveredNode;

import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {

    @FXML
    private LineChart lineChart;
    private List<ExchangeRate> rates = new LinkedList<>();

    public void setData() {
        ObservableList<Data<String, Float>> observableList = FXCollections.observableArrayList(rates.stream().map(ExchangeRate::convertToData).collect(Collectors.toList()));
        lineChart.setCursor(Cursor.CROSSHAIR);
        lineChart.setTitle("Exchange history");
        lineChart.setAnimated(false);

        for (Data<String, Float> element : observableList) {
            element.setNode(new HoveredNode(element.getYValue()));
        }
        XYChart.Series series = new XYChart.Series(observableList);
        series.setName("Exchange rate");

        lineChart.getData().add(series);
    }


    public <T> void loadData(IDataConnection<T> connection, IDataReader<T> reader){

        try {
            connection.connect();
            reader.makeDataUnits(connection.getRawData());
            Collection<DataUnit> units = reader.getDataUnits();
            for (DataUnit unit : units) {
                rates.add(new ExchangeRate(unit));
            }
        } catch (DataConnectionException | ParseException e) {
            e.printStackTrace();
        }
    }
}
