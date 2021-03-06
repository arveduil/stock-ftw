package ftw.sample;

import com.google.inject.Guice;
import com.google.inject.Injector;
import ftw.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ftw.stock.data.connection.IDataConnection;
import ftw.stock.data.reader.IDataReader;
import ftw.stock.guice.StockModule;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;

        initApplication();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initApplication() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("ftw/view/MainView.fxml"));
        BorderPane rootLayout = loader.load();

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
        MainController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
    }
}
