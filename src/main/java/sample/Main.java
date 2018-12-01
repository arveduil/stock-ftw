package sample;

import com.google.inject.Guice;
import com.google.inject.Injector;
import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import stock.ExchangeRate;
import stock.data.connection.IDataConnection;
import stock.data.connection.exception.DataConnectionException;
import stock.guice.StockModule;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        initLayout();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void initLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/MainView.fxml"));
        BorderPane rootLayout = (BorderPane) loader.load();

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();

        MainController controller = (MainController) loader.getController();
        IDataConnection<List<String>> connection =  setupGuice();
        controller.setData(generateExchangeRateList(connection));
    }

    private IDataConnection<List<String>> setupGuice(){
        Injector injector = Guice.createInjector(new StockModule());
        IDataConnection<List<String>> connection = injector.getInstance(IDataConnection.class);
        return connection;
    }
    private List<ExchangeRate> generateExchangeRateList(IDataConnection<List<String>>  connection) {
        List<ExchangeRate> rates = new LinkedList<ExchangeRate>();

        try {
            connection.connect();
            for (String line : connection.getRawData()) {
                rates.add(new ExchangeRate(line));
                System.out.println(line);
            }
        } catch (DataConnectionException | ParseException e) {
            e.printStackTrace();
        }

        return rates;
    }
}
