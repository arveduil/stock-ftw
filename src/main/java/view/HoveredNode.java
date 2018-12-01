package view;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class HoveredNode extends StackPane {
   public HoveredNode(float value) {
        setPrefSize(15, 15);

        final Label label = createDataLabel( value);

        setOnMouseEntered(mouseEvent -> {
            getChildren().setAll(label);
            toFront();
        });

        setOnMouseExited(mouseEvent -> {
            getChildren().clear();
        });
    }

    private Label createDataLabel(float value) {
        final Label label = new Label(value + "");
        label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
        label.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        label.setTextFill(Color.FIREBRICK);
        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        return label;
    }
}