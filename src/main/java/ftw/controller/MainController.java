package ftw.controller;

import ftw.presenter.CreateStrategyPresenter;
import ftw.sample.Main;
import ftw.simulation.model.SimulationInitialValues;
import ftw.simulation.model.exception.NonNumericFormatException;
import ftw.simulation.validator.SimulationInitialValuesValidator;
import ftw.stock.ExchangeRate;
import ftw.stock.data.FileProcessor;
import ftw.stock.data.connection.JsonFileDataConnection;
import ftw.stock.data.connection.TxtFileDataConnection;
import ftw.stock.data.connection.IDataConnection;
import ftw.stock.data.connection.exception.DataConnectionException;
import ftw.stock.data.connection.exception.InvalidDataFormatException;
import ftw.stock.data.reader.DataUnit;
import ftw.stock.data.reader.JsonDataReader;
import ftw.stock.data.reader.TxtDataReader;
import ftw.stock.data.reader.IDataReader;
import ftw.strategy.DecisionType;
import ftw.strategy.applicator.StrategyApplicator;
import ftw.strategy.model.Strategy;
import ftw.strategy.model.exception.InvalidSimulationInitialValuesException;
import ftw.strategy.model.exception.InvalidStrategyValuesException;
import ftw.view.HoveredNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.Desktop;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class MainController {

    @FXML
    public TextField startIterationTextField;

    @FXML
    public TextField endIterationTextField;

    @FXML
    public Label runStrategyMessage;

    @FXML
    public TableColumn<Strategy, BigDecimal> resultColumn;

    @FXML
    private TableView strategyTable;

    @FXML
    private TableColumn<Strategy, Integer> checkIntervalColumn;

    @FXML
    private TableColumn<Strategy, BigDecimal> changeColumn;

    @FXML
    private TableColumn<Strategy, DecisionType> decisionTypeTableColumn;

    @FXML
    private TableColumn<Strategy, BigDecimal> investmentPercentageColumn;

    @FXML
    private LineChart lineChart;

    private List<ExchangeRate> rates = new LinkedList<>();

    private Stage primaryStage;

    private ObservableList<Strategy> strategies = FXCollections.observableArrayList();

    private final FileChooser fileChooser = new FileChooser();

    private Desktop desktop = Desktop.getDesktop();

    public void setPrimaryStage(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }


    public void setData() {
        ObservableList<Data<String, Float>> observableList = FXCollections.observableArrayList(rates.stream().map(ExchangeRate::convertToData).collect(Collectors.toList()));
        lineChart.setCursor(Cursor.CROSSHAIR);
        lineChart.setTitle("Stock FTW");
        lineChart.setAnimated(false);

        for (Data<String, Float> element : observableList) {
            element.setNode(new HoveredNode(element.getYValue()));
        }
        XYChart.Series series = new XYChart.Series(observableList);
        series.setName("Exchange rate");

        this.lineChart.getData().add(series);

        this.strategyTable.setItems(this.strategies);

    }

    @FXML
    private void initialize() {
        strategyTable.getSelectionModel().setSelectionMode(
                SelectionMode.SINGLE);

        changeColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                .getChangeProperty());
        checkIntervalColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                .getCheckIntervalProperty());
        decisionTypeTableColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                .getDecisionTypeProperty());
        investmentPercentageColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                .getInvestmentPercentageProperty());

        fileChooser.setTitle("Load stock data");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT", "*.txt"),
                new FileChooser.ExtensionFilter("JSON", "*.json")
        );
    }

    private Strategy showAddStrategy() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getClassLoader().getResource("ftw/view/CreateStrategyView.fxml"));
            AnchorPane page = (AnchorPane) fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(page);
            stage.setTitle("Create Strategy");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            stage.setScene(scene);
            CreateStrategyPresenter presenter = fxmlLoader.getController();
            presenter.setStage(stage);
            stage.showAndWait();
            return presenter.getStrategy();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    private void handleCreateAction(ActionEvent event) throws InvalidStrategyValuesException {
        Strategy createdStrategy = showAddStrategy();
        if (createdStrategy != null) {
            strategies.add(createdStrategy);
        }
    }

    @FXML
    private void handleOpenFile(ActionEvent event) {
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            FileProcessor processor = new FileProcessor(file);
            processor.process();
            IDataConnection connection = processor.getConnection();
            IDataReader reader = processor.getReader();
            loadData(connection, reader, file);
            setData();
        }
    }

    public <T> void loadData(IDataConnection<T> connection, IDataReader<T> reader, File file) {

        try {
            connection.connect(file);
            reader.makeDataUnits(connection.getRawData());
            Collection<DataUnit> units = reader.getDataUnits();
            for (DataUnit unit : units) {
                rates.add(new ExchangeRate(unit));
            }
        } catch (DataConnectionException | ParseException | InvalidDataFormatException e) {
            e.printStackTrace();
        }
    }

    private SimulationInitialValues createStrategyInitialValues(){
        String startInput = startIterationTextField.getText();
        String endInput = endIterationTextField.getText();

        try {
            SimulationInitialValuesValidator.validateInputForSimulationInitialValues(startInput, endInput);
            return new SimulationInitialValues(Integer.parseInt(startInput), Integer.parseInt(endInput) );
        } catch (NonNumericFormatException | InvalidSimulationInitialValuesException e) {
            runStrategyMessage.setText(e.getMessage());
            return  null;
        }
    }

    @FXML
    public void runStrategy(ActionEvent actionEvent) {
        SimulationInitialValues simulationInitialValues = createStrategyInitialValues();
        if(simulationInitialValues != null){
            StrategyApplicator applicator;
            try {
                applicator = new StrategyApplicator(rates, strategies, simulationInitialValues);
            } catch (InvalidSimulationInitialValuesException e) {
                runStrategyMessage.setText(e.getMessage());
                return;
            }

            applicator.applyStrategies();
            Map<Date, BigDecimal> exchangeRateResultMap = applicator.getSimulationResult().getResults();

            drawOnLineChart(exchangeRateResultMap);
        }

    }

    private void drawOnLineChart(Map<Date, BigDecimal> exchangeRateResultMap) {
        ObservableList<Data<String, Float>> observableList = FXCollections.observableArrayList();

        for ( Map.Entry<Date, BigDecimal> entry: exchangeRateResultMap.entrySet())
        {
            Data<String,Float> data =new Data<String, Float>(DateFormat.getDateInstance(DateFormat.SHORT).format(entry.getKey()), entry.getValue().floatValue());
            data.setNode(new HoveredNode(data.getYValue()));
            observableList.add(data);
        }

        XYChart.Series series = new XYChart.Series(observableList);
        series.setName("Simulation results");
        this.lineChart.getData().clear();
        this.lineChart.getData().add(series);
    }

    final double SCALE_DELTA = 1.1;

}
