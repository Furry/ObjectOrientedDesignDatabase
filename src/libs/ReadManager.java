package libs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The ReadManager class handles all file reading. The reason this class exists is to allow for
 * SmartTraversal™. The database file can get extremely large, and it is not feasible to read
 * the entire file into memory. This class allows for the file to be read in a buffer.
 *
 * The position of the buffer is maintained by the ReadManager, and if given a key to read, it will restart
 * the buffer at the beginning and search, stopping when it finds it, so it doesn't need to restart from the beginning
 * for the next read.
 */
public class ReadManager {
    private final FileManager fileManager;
    private FileInputStream contentStream;
    private Scanner contentScanner;
    private int linePos = 1;
    private final int size;

    /**
     * The constructor for the ReadManager class.
     * @param fileManager The FileManager instance that this ReadManager is associated with.
     * @throws FileNotFoundException
     */
    public ReadManager(FileManager fileManager) throws FileNotFoundException {
        this.fileManager = fileManager;
        this.contentStream = new FileInputStream(this.fileManager.file.getAbsolutePath());
        this.contentScanner = new Scanner(this.contentStream);
        this.size = countLines();
    }

    /**
     * Resets the stored scanner's position to the beginning of the file,
     * re-declaring all variables within the class.
     */
    private void resetScanner() {
        try {
            this.contentStream.close();
            this.contentStream = new FileInputStream(this.fileManager.file.getAbsolutePath());
            this.contentScanner = new Scanner(this.contentStream);
        } catch (Exception ignored) {}
    }

    /**
     * Counts the number of lines in the file.
     * @return The line count.
     */
    public int countLines() {
        resetScanner();
        int lines = 0;
        while (this.contentScanner.hasNextLine()) {
            lines += 1;
            this.contentScanner.nextLine();
        }
        return lines;
    }

    /**
     * Dumps the contents of the file, concatenated into a single string
     * separated by new lines per entry.
     * @return
     */
    public String dump() {
        resetScanner();
        StringBuilder builder = new StringBuilder();
        while (this.contentScanner.hasNextLine()) {
            builder.append(this.contentScanner.nextLine()).append("\n");
        }
        return builder.toString();
    }

    /**
     * Read up to a specific line without resetting the scanner unless necessary.
     * @param line The line to read up to.
     * @return The contents of the line.
     */
    public String readToLine(int line) {
        if (line > this.size) {
            resetScanner();
        }

        if (linePos > line) {
            try {
                resetScanner();
            } catch (Exception e) {}
            this.linePos = 1;
        }

        // Constantly consume the next line until we're at the destination
        while (linePos < line) {
            if (this.contentScanner.hasNextLine()) {
                this.contentScanner.nextLine();
            }
            linePos += 1;
        }

        String nextLn = this.contentScanner.nextLine();
//        System.out.println(nextLn);
        return nextLn;
        // Return the next line and pray it's not null lol
//        return this.contentScanner.nextLine();
    }

    /**
     * Iterates over all entries in the database,
     * returning the entry with the given associated key.
     * @param key The key to search for.
     * @return The entry with the given key.
     */
    public String findByKey(String key) {
        resetScanner();
        while (this.contentScanner.hasNextLine()) {
            String line = this.contentScanner.nextLine();
            if (line.startsWith(key)) {
                return line.split("::")[1];
            }
        }
        return null;
    }
}
