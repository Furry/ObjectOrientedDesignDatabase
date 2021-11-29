package libs;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class API {
    private HttpServer _server;
    public API() throws IOException {
        // Create new http server
        this._server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);

        // All functions that add routes to the server should be added here.
        ping();
        get();
        set();
        dump();
        getLine();
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
     * this function will be called, and you'll see 'pong uwu' in the browser!
     */
    private void ping() {
        _server.createContext("/ping", exchange -> {
            writeResponse(exchange, "pong!");
        });
    }

    private void get() {
        _server.createContext("/get", exchange -> {
            Map<String, String> queryParams = QueryString.of(exchange.getRequestURI().getQuery());
            String key = queryParams.get("key");
            if (key == null) {
                writeResponse(exchange, "No key was provided.");
                return;
            }

            try {
                String value = Database.getInstance().get(key);
                writeResponse(exchange, value);
            } catch (Exception ignored) {
                writeResponse(exchange, "Internal Error");
            }
        });
    }

    private void set() {
        _server.createContext("/set", exchange -> {
            Map<String, String> queryParams = QueryString.of(exchange.getRequestURI().getQuery());
            String key = queryParams.get("key");
            String value = queryParams.get("value");
            if (key == null || value == null) {
                writeResponse(exchange, "A key or value was not provided.");
                return;
            }

            try {
                Database.getInstance().set(key, value);
                writeResponse(exchange, "Success");
            } catch (Exception ignored) {
                writeResponse(exchange, "Internal Error");
            }
        });
    }

    private void dump() {
        _server.createContext("/dump", exchange -> {
            try {
                String value = Database.getInstance().dump();
                writeResponse(exchange, value);
            } catch (Exception ignored) {
                writeResponse(exchange, "Internal Error");
            }
        });
    }

    private void getLine() {
        _server.createContext("/getLine", exchange -> {
            Map<String, String> queryParams = QueryString.of(exchange.getRequestURI().getQuery());
            String line = queryParams.get("line");
            if (line == null) {
                writeResponse(exchange, "No key was provided.");
                return;
            }

            try {
                String value = Database.getInstance().getLine(Integer.parseInt(line));
                writeResponse(exchange, value);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                writeResponse(exchange, "Internal Error");
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
            OutputStream body = exchange.getResponseBody();

            exchange.sendResponseHeaders(200, response.length());
            body.write(response.getBytes(StandardCharsets.UTF_8));
            body.flush();
            body.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
