package stock.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import stock.data.connection.FileDataConnection;
import stock.data.connection.IDataConnection;
import stock.data.reader.FileDataReader;
import stock.data.reader.IDataReader;

public class StockModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IDataConnection.class).to(FileDataConnection.class);
        bind(IDataReader.class).to(FileDataReader.class);
        bind(String.class).annotatedWith(Names.named("fileConnectionFileName")).toInstance("data.txt");
    }
}
