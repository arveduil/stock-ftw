package ftw.stock.data;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import ftw.controller.MainController;
import ftw.stock.data.connection.IDataConnection;
import ftw.stock.data.reader.IDataReader;
import ftw.stock.guice.StockModule;

import java.io.File;

public class FileProcessor {

    private File file;

    private IDataReader reader;
    private IDataConnection connection;

    public FileProcessor(File file)
    {
        this.file = file;
    }

    public void process() {
        String extension = "";
        Injector injector = Guice.createInjector(new StockModule());

        int i = file.getName().lastIndexOf('.');
        if (i > 0) {
            extension = file.getName().substring(i + 1);
        }
        if (extension.equals("txt")) {
            reader = injector.getInstance(Key.get(IDataReader.class, Names.named("txt")));
            connection = injector.getInstance(Key.get(IDataConnection.class, Names.named("txt")));
        } else if (extension.equals("json")) {
            reader = injector.getInstance(Key.get(IDataReader.class, Names.named("json")));
            connection = injector.getInstance(Key.get(IDataConnection.class, Names.named("json")));
        } else if (extension.equals("xls")) {
            reader = injector.getInstance(Key.get(IDataReader.class, Names.named("xls")));
            connection = injector.getInstance(Key.get(IDataConnection.class, Names.named("xls")));
        }
    }

    public IDataReader getReader() {
        return this.reader;
    }

    public IDataConnection getConnection() {
        return this.connection;
    }
}
