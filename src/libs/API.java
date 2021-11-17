package libs;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class API {
    private HttpServer _server;
    public API() throws IOException {
        // Create new http server
        this._server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
        ping();

        this._server.start();
    }

    private void ping() {
        _server.createContext("/ping", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String res = "PONG";
                OutputStream body = exchange.getResponseBody();

                exchange.sendResponseHeaders(200, res.length());
                body.write(res.getBytes(StandardCharsets.UTF_8));
                body.flush();
                body.close();
            }
        });
    }
}
