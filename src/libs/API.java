package libs;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * The WebServer or API instance to broker database interaction between a website/application and the
 * database management engine (This Project)
 */
public class API {
    private HttpServer _server;

    /**
     * The Constructor for the API instance
     * @throws IOException
     */
    public API() throws IOException {

        // Create new http server
        this._server = HttpServer.create(
                new InetSocketAddress("localhost", 8080),0);

        // All functions that bind a new route/action to an incoming request are declared below.
        ping();
        get();
        set();
        dump();
        getLine();
        getFile();

        // Start the server after everything's been initialized.
        this._server.start();
    }

    /**
     * This is an example route, a template for what they should look like.
     * '_server' is the HttpServer object. This is what handles all incoming requests to the server,
     * and is what you need to use to add a new route.
     *
     * The 'createContext' function on '_server' (_server.createContext) takes a path, and a function,
     * and the function is called when a request is made to that path.
     *
     * Example, when this program is running if you go to "https://localhost:8080/ping" in your browser,
     * this function will be called, and you'll see 'pong!' in the browser.
     */
    private void ping() {
        _server.createContext("/ping", exchange -> {
            writeResponse(exchange, "pong!");
        });
    }

    /**
     * Handle incoming 'get' requests that access data.
     */
    private void get() {
        _server.createContext("/get", exchange -> {
            Map<String, String> queryParams = QueryString.of(exchange.getRequestURI().getQuery());
            // Get the key name from the query string.
            String key = queryParams.get("key");

            // If it doesn't exist, return an error.
            if (key == null) {
                writeResponse(exchange, "No key was provided.");
                return;
            }

            try {
                // Query the database for the key, and return the value.
                // Throws Internal Error in the event of an error.
                String value = Database.getInstance().get(key);
                writeResponse(exchange, value);
            } catch (Exception ignored) {
                writeResponse(exchange, "Internal Error");
            }
        });
    }

    /**
     * Handle incoming 'set' requests that set new values into the database.
     */
    private void set() {
        _server.createContext("/set", exchange -> {
            // Get the query string from the request.
            Map<String, String> queryParams = QueryString.of(exchange.getRequestURI().getQuery());
            String key = queryParams.get("key");
            String value = queryParams.get("value");

            // If the key or value is null, return an error.
            if (key == null || value == null) {
                writeResponse(exchange, "A key or value was not provided.");
                return;
            }

            try {
                // Set the key to the value in the database.
                Database.getInstance().set(key, value);
                writeResponse(exchange, "Success");
            } catch (Exception ignored) {
                writeResponse(exchange, "Internal Error");
            }
        });
    }

    /**
     * Handle incoming 'dump' requests that dump the entire database to the request.
     */
    private void dump() {
        _server.createContext("/dump", exchange -> {
            try {
                // Get the entire database from the instance.
                String value = Database.getInstance().dump();
                writeResponse(exchange, value);
            } catch (Exception ignored) {
                writeResponse(exchange, "Internal Error");
            }
        });
    }

    /**
     * Read data from the database at a specific line within the file.
     */
    private void getLine() {
        _server.createContext("/getLine", exchange -> {
            Map<String, String> queryParams = QueryString.of(exchange.getRequestURI().getQuery());
            String line = queryParams.get("line");
            // If the user didn't provide a line, return an error.
            if (line == null) {
                writeResponse(exchange, "No key was provided.");
                return;
            }

            try {
                // Read the line from the database, and return the response.
                String value = Database.getInstance().getLine(Integer.parseInt(line));
                writeResponse(exchange, value);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                writeResponse(exchange, "Internal Error");
            }
        });
    }

    /**
     * Get a file from the disk, and write it to the output stream.
     */
    private void getFile() {
        _server.createContext("/", exchange -> {
            URI uri = exchange.getRequestURI();

            // Check if file exists.
            File file = new File(uri.getPath());
            if (!file.exists()) {
                writeResponse(exchange, "File not found.");
            } else {
                String body = "";
                try {
                    // Read the file and write it to the output stream.
                    body = new String(Files.readAllBytes(Paths.get(file.getPath())));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                writeResponse(exchange, body);
            }

        });
    }

    /**
     * A short utility function to write a response to the client.
     * @param exchange The HTTPExchange object.
     * @param response The repsonse data you want to write.
     */
    private void writeResponse(HttpExchange exchange, String response) {
        try {
            // Get an output stream from the client-bound exchange.
            OutputStream body = exchange.getResponseBody();

            // Write an OK status code with the length of bytes we're returning
            exchange.sendResponseHeaders(200, response.length());

            // Write the data, flush the stream, and close the stream.
            body.write(response.getBytes(StandardCharsets.UTF_8));
            body.flush();
            body.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
