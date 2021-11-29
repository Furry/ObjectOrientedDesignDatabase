import libs.API;
import libs.Database;
import libs.FileManager;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class ObjectOrientedDesignFinal {
    public static void main(String[] args) throws Exception {
        // If the file 'db.jdb' exists, delete it.
        API api = new API();

//        FileManager fileManager = new FileManager("db.jdb");
//
//        int pos = fileManager.writeManager.writeEntry("applebees", "chicken tortilla soup");
//
//        Thread.sleep(1000);
//        System.out.println(pos);
//        System.out.println(fileManager.readManager.readToLine(pos));
//        System.out.println(fileManager.readManager.readToLine(1));

//        System.out.println(fileManager.file.length());

        // Run a full benchmark on all line read, line write, and random key-read benchmarks.
        // fullBenchmark();

    }

    public static String hashString(String s) {
        // Create an unsalted256 bit hash
        String hash = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(s.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            hash = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

    public static void fullBenchmark() throws Exception {

        File file = new File("db.jdb");
        if (file.exists()) {
            file.delete();
        }

        // Hash 1 to 9999, store the hash and number in a dictionary.
        Map<Integer, String> map = new HashMap<>();
        for (int i = 1; i <= 9999; i++) {
            map.put(i, hashString(Integer.toString(i)));
        }

        // Create a new file manager
        FileManager fileManager = new FileManager("db.jdb");

        // Create a new timer
        long startTime = System.currentTimeMillis();

        // Iterate over the dictionary
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            int num = entry.getKey();
            String hash = entry.getValue();

            // Write the entry to the file
            fileManager.writeManager.writeEntry(Integer.toString(num), hash);
        }

        // Get the time it took to write
        long endTime = System.currentTimeMillis();

        // Print the time it took to write in ms
        System.out.println("Time to write 9999 unique values: " + (endTime - startTime) + " ms");
        benchmarkRandomRead(fileManager);
    }

    public static void benchmarkRandomRead(FileManager fileManager) {
        // Generate 50 random numbers between 1 and 9999
        int[] randomNumbers = new int[50];
        for (int i = 0; i < randomNumbers.length; i++) {
            randomNumbers[i] = (int) (Math.random() * 9999) + 1;
        }

        // Create a new timer
        long startTime = System.currentTimeMillis();

        // Iterate over the random numbers
        for (int i = 0; i < randomNumbers.length; i++) {
            int num = randomNumbers[i];

            // Read the entry from the file
            fileManager.readManager.readToLine(num);
        }

        // Get the time it took to read
        long endTime = System.currentTimeMillis();

        // Print the time it took to read in ms
        System.out.println("Time to read 50 random values: " + (endTime - startTime) + " ms");
        benchmarkKeyReads(fileManager);
    }

    public static void benchmarkKeyReads(FileManager fileManager) {
        // Generate 50 random numbers between 1 and 9999
        int[] randomNumbers = new int[50];
        for (int i = 0; i < randomNumbers.length; i++) {
            randomNumbers[i] = (int) (Math.random() * 9999) + 1;
        }

        // Create a new timer
        long startTime = System.currentTimeMillis();


        // Iterate over the random numbers
        for (int i = 0; i < randomNumbers.length; i++) {
            int num = randomNumbers[i];
            fileManager.readManager.findByKey(Integer.toString(num));
        }

        // Get the time it took to read
        long endTime = System.currentTimeMillis();

        // Print the time it took to read in ms
        System.out.println("Time to read 50 random values by key: " + (endTime - startTime) + " ms");

    }


}
