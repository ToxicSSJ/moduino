package me.itoxic.moduino.server;

import me.itoxic.moduino.server.handlers.GenerateHandler;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Esta clase se encarga de la creación
 * del servidor API principal.
 *
 * @version 1.0
 * @since   2019-07-26
 *
 */
@Deprecated
public class HTTPServer {

    private String id;

    private HttpServer server = null;
    private Map<String, HttpHandler> routes;

    public HTTPServer() {
        this(UUID.randomUUID().toString());
    }

    public HTTPServer(String id) {

        this.id = id;

    }

    public HTTPServer create(int port) {

        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;

    }

    public HTTPServer addRoutes() {

        routes = new HashMap<String, HttpHandler>();

        routes.put("/generate", new GenerateHandler());

        for(Map.Entry<String, HttpHandler> route : routes.entrySet())
            server.createContext(route.getKey(), route.getValue());

        return this;

    }

    public HTTPServer start() {

        server.setExecutor(null); // creates a default executor
        server.start();

        return this;

    }


}
