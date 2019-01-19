package ftw.controller;

import ftw.strategy.model.exception.NonnumericFormatException;
import ftw.strategy.DecisionType;
import ftw.strategy.model.Strategy;
import ftw.strategy.model.exception.InvalidStrategyValuesException;
import ftw.strategy.model.validator.StrategyValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.math.BigDecimal;

public class CreateStrategyPresenter {

    private Stage dialogStage;
    private Strategy strategy;

    @FXML
    Button okButton;

    @FXML
    TextField changeTextField;

    @FXML
    TextField checkIntervalTextField;

    @FXML
    ComboBox<DecisionType> decisionComboBox;

    @FXML
    TextField investmentPercentageTextField;

    @FXML
    Label messageArea;

    @FXML
    public void initialize() {
        decisionComboBox.setItems(FXCollections.observableArrayList(DecisionType.values()));

        checkIntervalTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    investmentPercentageTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        decisionComboBox.setValue(DecisionType.BUY);
    }

    @FXML
    private void handleOkAction(ActionEvent event) {
        String checkIntervalInput = checkIntervalTextField.getText();
        String changeInput = changeTextField.getText();
        String investmentPercentageInput=  investmentPercentageTextField.getText();

        if (parametersOk(checkIntervalInput,changeInput,investmentPercentageInput) && createStrategy(checkIntervalInput,changeInput,investmentPercentageInput)) {
            dialogStage.close();
        }
    }

    private boolean parametersOk(String checkIntervalInput, String changeInput, String investmentPercentageInput){
        try {
            StrategyValidator.validateInputForStrategyInitialValues(checkIntervalInput,changeInput,investmentPercentageInput);
        } catch (NonnumericFormatException  e) {
            this.messageArea.setText(e.getMessage());
            return false;
        }

        return true;
    }

    public void setStage(Stage stage)
    {
        this.dialogStage = stage;
    }

    public boolean createStrategy(String checkIntervalInput, String changeInput, String investmentPercentageInput){
        try {
            this.strategy = new Strategy(Integer.parseInt(checkIntervalInput),new BigDecimal(changeInput),decisionComboBox.getValue(),new BigDecimal(investmentPercentageInput));
            return true;
        } catch ( InvalidStrategyValuesException e) {
            this.messageArea.setText(e.getMessage());
        }
        return false;
    }

    public Strategy getStrategy(){
        return  this.strategy;
    }
}
