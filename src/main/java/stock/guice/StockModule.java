package stock.guice;

import com.google.inject.AbstractModule;
import stock.data.connection.FileDataConnection;
import stock.data.connection.IDataConnection;

public class StockModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IDataConnection.class).to(FileDataConnection.class);
    }
}
