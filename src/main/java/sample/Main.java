package sample;

import com.google.inject.Guice;
import com.google.inject.Injector;
import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import stock.data.connection.IDataConnection;
import stock.data.reader.IDataReader;
import stock.guice.StockModule;

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
        loader.setLocation(getClass().getClassLoader().getResource("view/MainView.fxml"));
        BorderPane rootLayout = loader.load();

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();

        MainController controller = loader.getController();
        IDataConnection connection = setupConnector();
        IDataReader reader = setupReader();
        controller.loadData(connection, reader);
        controller.setData();
    }

    private IDataConnection setupConnector() {
        Injector injector = Guice.createInjector(new StockModule());
        IDataConnection connection = injector.getInstance(IDataConnection.class);
        return connection;
    }

    private IDataReader setupReader() {
        Injector injector = Guice.createInjector(new StockModule());
        IDataReader reader = injector.getInstance(IDataReader.class);
        return reader;
    }
}
