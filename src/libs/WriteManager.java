package libs;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Handles keeping track of position in file and writing new data to it.
 */
public class WriteManager {
    private final FileManager fileManager;
    private int lineCount;

    /**
     * Constructor for WriteManager.
     * @param fileManager The associated FileManager.
     */
    public WriteManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.lineCount = this.fileManager.readManager.countLines();
    }

    /**
     * Writes a new entry to the database.
     * @param key The line to write.
     * @param val The value to write to the associated key.
     */
    public int writeEntry(String key, String val) {
        try {
            FileOutputStream fos = new FileOutputStream(fileManager.file, true);
            fos.write(String.format("%s::%s\n", key, val).getBytes());
            fos.close();
            this.lineCount++;
            return lineCount;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
