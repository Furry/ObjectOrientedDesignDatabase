import libs.API;

/**
 * This class is the main entry point to actually start the application & associated
 * HTTP server.
 */
public class ObjectOrientedDesignFinal {
    /**
     * This is the main entry point for the application.
     * It creates a new 'API' singleton instance which handles all requests with a website.
     * @param args CLI Args.
     * @throws Exception Some random exception.
     */
    public static void main(String[] args) throws Exception {
        // By default, it uses a file named 'db.jdb', creating a new one if it's not found.
        new API();
    }
}
