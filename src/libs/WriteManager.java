package libs;

import java.io.FileOutputStream;
import java.io.IOException;

public class WriteManager {
    private final FileManager fileManager;
    private int lineCount;

    public WriteManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.lineCount = this.fileManager.readManager.countLines();
    }

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
