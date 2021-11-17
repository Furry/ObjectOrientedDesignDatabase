package libs;

public class Database {
    private static Database _instance;
    private Cache cache = new Cache();

    public Database() {
        Database._instance = this;
    }

    public static Database getInstance() {
        if (Database._instance == null) {
            Database._instance = new Database();
        }
        return Database._instance;
    }

    public String get(String key) {
        String v = this.cache.get(key);
        if (v != null) return v;

        return ":(";
    }

    public void set(String key, String value) {
        this.cache.set(key, value);
    }
}
