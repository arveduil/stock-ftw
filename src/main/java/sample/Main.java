package sample;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import stock.data.connection.IDataConnection;
import stock.data.connection.exception.DataConnectionException;
import stock.guice.StockModule;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new StockModule());
        IDataConnection<List<String>> connection = injector.getInstance(IDataConnection.class);
        try {
            connection.connect();
            for (String line : connection.getRawData()) {
                System.out.println(line);
            }
        } catch (DataConnectionException e) {
            e.printStackTrace();
        }

        launch(args);
    }
}
