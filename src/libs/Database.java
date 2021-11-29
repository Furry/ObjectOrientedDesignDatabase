package libs;

/**
 * The Database class is used to store critical features of the database.
 *
 * This implementation is based on two separate patterns, the Singleton Pattern (As only one of this class can exist at a time)
 * and the Decorator Pattern, which is used to add additional functionality to the Database class via using the
 * FileManager class being passed into it.
 */
public class Database {
    // Keep track of the current static instance.
    private static Database _instance;
    // Create our empty cache.
    private Cache cache = new Cache();
    // Create our file manager.
    private final FileManager fileManager;


    /**
     * The constructor for the Database class.
     * @param fm The FileManager to be used.
     */
    public Database(FileManager fm) {
        // Set the static instance to this.
        Database._instance = this;
        this.fileManager = fm;
    }

    /**
     * The getInstance method is used to get the current instance of the Database class.
     * @return The current instance of the Database class.
     */
    public static Database getInstance() {
        // If getInstance is called when the static instance is null, create it.
        if (Database._instance == null) {
            try {
                Database._instance = new Database(
                        new FileManager("db.jdb")
                );
            } catch (Exception ignored) {}
        }
        // If the static instance exists, return it.
        return Database._instance;
    }

    /**
     * Gets an item from the database, attempting from the cache first,
     * moving onto the on-disk file if the cache query fails.
     * @param key The key to search for.
     * @return The result from the key
     */
    public String get(String key) {
        // Attempt to get the value from the cache. If it exists, return it.
        String v = this.cache.get(key);
        if (v != null) return v;

        // If the cache query failed, attempt to get the value from the file.
        String res = Database.getInstance().fileManager.readManager.findByKey(key);
        if (res != null) {
            this.cache.set(key, res);
            return res;
        } else {
            return "null";
        }
    }

    /**
     * Sets an item in the database, setting it in the cache before
     * writing it to disk.
     * @param key The key to set.
     * @param value The value to set.
     */
    public void set(String key, String value) {
        this.cache.set(key, value);
        this.fileManager.writeManager.writeEntry(key, value);
    }

    /**
     * Read a specific line from the database.
     * @param line The line to read.
     * @return
     */
    public String getLine(int line) {
        return this.fileManager.readManager.readToLine(line);
    }

    /**
     * Dump the entire contance of the database into a concatenated string.
     * @return
     */
    public String dump() {
        return this.fileManager.readManager.dump();
    }
}
