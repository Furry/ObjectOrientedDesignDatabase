package libs;

import java.io.*;

/**
 * The FileManager class is used to wrap around the ReadManager and WriteManager classes,
 * exposing common or co-dependent methods to the Database.
 *
 * This implementation is the Proxy Pattern, as it contains two separate classes, and acts as a
 * proxy to the classes features.
 */
public class FileManager {
    public final String DB_NAME;
    public final File file;

    // Final manager instances
    public final ReadManager readManager;
    public final WriteManager writeManager;

    /**
     * The constructor for the FileManager
     * @param dbName The name of the database
     * @throws Exception If the file cannot be created.
     */
    public FileManager(String dbName) throws Exception {
        // If the user passes a file name that isn't valid, throw an exception
        if (!dbName.endsWith(".jdb")) {
            throw new Exception("Invalid database name");
        }

        // If it doesn't exist, make it.
        if (!new File(dbName).exists()) {
            new File(dbName).createNewFile();
        }
        this.file = new File(dbName);
        DB_NAME = this.file.getName();

        // Create the manager instances
        this.readManager = new ReadManager(this);
        this.writeManager = new WriteManager(this);
    }
}

