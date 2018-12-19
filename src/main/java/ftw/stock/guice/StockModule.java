package ftw.stock.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import ftw.stock.data.connection.FileDataConnection;
import ftw.stock.data.connection.IDataConnection;
import ftw.stock.data.connection.JsonFileDataConnection;
import ftw.stock.data.reader.FileDataReader;
import ftw.stock.data.reader.IDataReader;
import ftw.stock.data.reader.JsonDataReader;

public class StockModule extends AbstractModule {

    @Override
    protected void configure() {
        /*bind(IDataConnection.class).to(FileDataConnection.class);
        bind(IDataReader.class).to(FileDataReader.class);*/
        bind(IDataConnection.class).to(JsonFileDataConnection.class);
        bind(IDataReader.class).to(JsonDataReader.class);
        bind(String.class).annotatedWith(Names.named("fileConnectionFileName")).toInstance("data.txt");
        bind(String.class).annotatedWith(Names.named("jsonConnectionFileName")).toInstance("data.json");
    }
}
