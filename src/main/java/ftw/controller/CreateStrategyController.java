package ftw.controller;

import ftw.sample.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class CreateStrategyController {

    private Stage dialogStage;
    @FXML
    Button okButton;



    public void setStage(Stage stage)
    {
        this.dialogStage = stage;
    }
}
