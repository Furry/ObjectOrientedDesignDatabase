package libs;

public class Database {
    private static Database _instance;
    public Database() {
        Database._instance = this;
    }

    public static Database getInstance() {
        if (Database._instance == null) {
            Database._instance = new Database();
        }
        return Database._instance;
    }
}
