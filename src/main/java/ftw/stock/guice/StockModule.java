package ftw.stock.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import ftw.stock.data.connection.JsonFileDataConnection;
import ftw.stock.data.connection.TxtFileDataConnection;
import ftw.stock.data.connection.IDataConnection;
import ftw.stock.data.connection.XlsFileDataConnection;
import ftw.stock.data.reader.JsonDataReader;
import ftw.stock.data.reader.TxtDataReader;
import ftw.stock.data.reader.IDataReader;
import ftw.stock.data.reader.XlsDataReader;

public class StockModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IDataConnection.class).annotatedWith(Names.named("txt")).to(TxtFileDataConnection.class);
        bind(IDataConnection.class).annotatedWith(Names.named("json")).to(JsonFileDataConnection.class);
        bind(IDataConnection.class).annotatedWith(Names.named("xls")).to(XlsFileDataConnection.class);
        bind(IDataReader.class).annotatedWith(Names.named("txt")).to(TxtDataReader.class);
        bind(IDataReader.class).annotatedWith(Names.named("json")).to(JsonDataReader.class);
        bind(IDataReader.class).annotatedWith(Names.named("xls")).to(XlsDataReader.class);
    }
}
