package libs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ReadManager {
    private final FileManager fileManager;
    private FileInputStream contentStream;
    private Scanner contentScanner;
    private int linePos = 1;
    private final int size;

    public ReadManager(FileManager fileManager) throws FileNotFoundException {
        this.fileManager = fileManager;
        this.contentStream = new FileInputStream(this.fileManager.file.getAbsolutePath());
        this.contentScanner = new Scanner(this.contentStream);
        this.size = countLines();
    }

    private void resetScanner() {
        try {
            this.contentStream.close();
            this.contentStream = new FileInputStream(this.fileManager.file.getAbsolutePath());
            this.contentScanner = new Scanner(this.contentStream);
        } catch (Exception ignored) {}
    }

    public int countLines() {
        resetScanner();
        int lines = 0;
        while (this.contentScanner.hasNextLine()) {
            lines += 1;
            this.contentScanner.nextLine();
        }
        return lines;
    }

    public String dump() {
        resetScanner();
        StringBuilder builder = new StringBuilder();
        while (this.contentScanner.hasNextLine()) {
            builder.append(this.contentScanner.nextLine()).append("\n");
        }
        return builder.toString();
    }

    public String readToLine(int line) {
        if (line > this.size) {
            resetScanner();
//            System.out.println("Current line is " + this.linePos + " and you are trying to read to line " + line + " Max line is " + this.size);

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
