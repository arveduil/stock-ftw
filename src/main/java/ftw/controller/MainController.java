package ftw.controller;

import ftw.sample.Main;
import ftw.strategy.DecisionType;
import ftw.strategy.applicator.StrategyApplicator;
import ftw.strategy.model.Strategy;
import ftw.strategy.model.SimulationInitialValues;
import ftw.strategy.model.StrategyResult;
import ftw.strategy.model.exception.InvalidSimulationInitialValuesException;
import ftw.strategy.model.exception.InvalidStrategyValuesException;
import ftw.strategy.model.exception.NonnumericFormatException;
import ftw.strategy.model.validator.SimulationInitialValuesValidator;
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
import ftw.stock.ExchangeRate;
import ftw.stock.data.connection.IDataConnection;
import ftw.stock.data.connection.exception.DataConnectionException;
import ftw.stock.data.connection.exception.InvalidDataFormatException;
import ftw.stock.data.reader.DataUnit;
import ftw.stock.data.reader.IDataReader;
import ftw.view.HoveredNode;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class MainController {

    @FXML
    public TextField startIterationTextField;
    @FXML
    public TextField initialBudgetTextField;
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
    @FXML
    private TableColumn<Strategy, Boolean> isActiveStrategy;
    private List<ExchangeRate> rates = new LinkedList<>();
    private Stage primaryStage;
    private ObservableList<Strategy> strategies = FXCollections.observableArrayList();

    @FXML
    private Button createStrategyButton;

    public void setData(Stage primaryStage) {
        this.primaryStage = primaryStage;
        ObservableList<Data<String, Float>> observableList = FXCollections.observableArrayList(rates.stream().map(ExchangeRate::convertToData).collect(Collectors.toList()));
        lineChart.setCursor(Cursor.CROSSHAIR);
        lineChart.setTitle("Exchange history");
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

    private SimulationInitialValues createStrategyInitialValues(){
        String budgetInput = initialBudgetTextField.getText();
        String  startInput =  startIterationTextField.getText();
        String endInput = endIterationTextField.getText();

        try {
            SimulationInitialValuesValidator.validateInputForSimulationInitialValues(budgetInput,startInput,endInput);
            return new SimulationInitialValues(new BigDecimal(budgetInput),Integer.parseInt(startInput),Integer.parseInt(endInput) );
        } catch (NonnumericFormatException | InvalidSimulationInitialValuesException e) {
            runStrategyMessage.setText(e.getMessage());
            return  null;
        }
    }

    @FXML
    public void runStrategy(ActionEvent actionEvent) {
        SimulationInitialValues simulationInitialValues = createStrategyInitialValues();
        if(simulationInitialValues != null){
            StrategyApplicator applicator;
 //           try {
                applicator = new StrategyApplicator(rates,strategies,simulationInitialValues.getBudget());
//            } catch (InvalidSimulationInitialValuesException e) {
//                runStrategyMessage.setText(e.getMessage());
//                return;
//            }

            Map<Strategy, StrategyResult> strategyToStrategyResult = applicator.getStrategyResults();

            resultColumn.setCellValueFactory(dataValue -> strategyToStrategyResult.get(dataValue).getResultProperty());
        }

    }
}
