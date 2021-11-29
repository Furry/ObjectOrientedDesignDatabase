package libs;

public class Database {
    private static Database _instance;
    private Cache cache = new Cache();
    private final FileManager fileManager;

    public Database(FileManager fm) {
        Database._instance = this;
        this.fileManager = fm;
    }

    public static Database getInstance() {
        if (Database._instance == null) {
            try {
                Database._instance = new Database(
                        new FileManager("db.jdb")
                );
            } catch (Exception ignored) {}
        }
        return Database._instance;
    }

    public String get(String key) {
        String v = this.cache.get(key);
        if (v != null) return v;
        String res = Database.getInstance().fileManager.readManager.findByKey(key);
        if (res != null) {
            this.cache.set(key, res);
            return res;
        } else {
            return "null";
        }
    }

    public void set(String key, String value) {
        this.cache.set(key, value);
        this.fileManager.writeManager.writeEntry(key, value);
    }

    public String getLine(int line) {
        return this.fileManager.readManager.readToLine(line);
    }

    public String dump() {
        return this.fileManager.readManager.dump();
    }
}
