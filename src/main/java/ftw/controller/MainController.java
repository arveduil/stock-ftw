package ftw.controller;

import ftw.sample.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import ftw.stock.ExchangeRate;
import ftw.stock.data.connection.IDataConnection;
import ftw.stock.data.connection.exception.DataConnectionException;
import ftw.stock.data.connection.exception.InvalidDataFormatException;
import ftw.stock.data.reader.DataUnit;
import ftw.stock.data.reader.IDataReader;
import ftw.view.HoveredNode;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {

    @FXML
    private LineChart lineChart;
    private List<ExchangeRate> rates = new LinkedList<>();

    @FXML
    private Button createStrategyButton;

    public void setData(Stage primaryStage) {
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
        createStrategyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(Main.class.getClassLoader().getResource("ftw/view/CreateStrategyViewPopUp.fxml"));
                    AnchorPane page = (AnchorPane) fxmlLoader.load();
                    Stage stage = new Stage();
                    Scene scene = new Scene(page);
                    stage.setTitle("New Window");
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(primaryStage);
                    stage.setScene(scene);
                    CreateStrategyController presenter = fxmlLoader.getController();
                    presenter.setStage(stage);
                    stage.showAndWait();

                    ((Node)(event.getSource())).getScene().getWindow().hide();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public <T> void loadData(IDataConnection<T> connection, IDataReader<T> reader) {

        try {
            connection.connect();
            reader.makeDataUnits(connection.getRawData());
            Collection<DataUnit> units = reader.getDataUnits();
            for (DataUnit unit : units) {
                rates.add(new ExchangeRate(unit));
            }
        } catch (DataConnectionException | ParseException | InvalidDataFormatException e) {
            e.printStackTrace();
        }
    }

   /* public void loadStrategyView() {
     *//*   Stage strategyStage = new Stage();

        strategyStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("ftw/view/CreateStrategyView.fxml"));
        BorderPane rootLayout = null;
        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(rootLayout);
        strategyStage.setScene(scene);
        strategyStage.show();
    }*//*

        createStrategyButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

            }
        });
    }*/
}
