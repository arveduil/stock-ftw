package ftw.stock.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import ftw.stock.data.connection.FileDataConnection;
import ftw.stock.data.connection.IDataConnection;
import ftw.stock.data.reader.FileDataReader;
import ftw.stock.data.reader.IDataReader;

public class StockModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IDataConnection.class).to(FileDataConnection.class);
        bind(IDataReader.class).to(FileDataReader.class);
        bind(String.class).annotatedWith(Names.named("fileConnectionFileName")).toInstance("data.txt");
    }
}
