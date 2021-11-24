package libs;

import java.io.*;
import java.util.Scanner;

public class FileManager {
    public final String DB_NAME;
    public final File file;

    // Manager Decorators
    public final ReadManager readManager;
    public final WriteManager writeManager;

    public FileManager(String dbName) throws Exception {
        // If the user passes a file name that isn't valid, throw an exception
        if (!dbName.endsWith(".jdb")) {
            throw new Exception("Invalid database name");
        }

        if (!new File(dbName).exists()) {
            // Create it
            new File(dbName).createNewFile();
        }
        this.file = new File(dbName);
        DB_NAME = this.file.getName();

        this.readManager = new ReadManager(this);
        this.writeManager = new WriteManager(this);
    }
}

