package org.example;

import com.sun.net.httpserver.HttpServer;
import org.example.annotation.AnnotationHandler;
import org.example.config.ServerConfig;

import java.net.InetSocketAddress;

public class EmbeddedServer {
    private HttpServer server;

    public void start(int port) throws Exception {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        AnnotationHandler handler = new AnnotationHandler();
        handler.scanControllers("org.example.controller");
        server.createContext("/", handler);
        server.start();
        System.out.println("\n" +
                "  ███████╗ ██████╗ ██████╗  ██████╗ ███████╗\n" +
                "  ██╔════╝██╔═══██╗██╔══██╗██╔═══██╗██╔════╝\n" +
                "  █████╗  ██║   ██║██████╔╝██║   ██║█████╗  \n" +
                "  ██╔══╝  ██║   ██║██╔══██╗██║   ██║██╔══╝  \n" +
                "  ██║     ╚██████╔╝██║  ██║╚██████╔╝███████╗\n" +
                "  ╚═╝      ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ ╚══════╝\n" +
                "                                           \n" +
                "  :: HTTP FORGE ::                (v1.0.0)      \n" +
                "  Forging your HTTP server with power and precision!\n" +
                "                                           \n" +
                "  🔥 Server is running on port: " + port + "\n" +
                "  🔗 Access: http://localhost:" + port + "  \n" +
                "                                           \n" +
                "  [*] Controllers scanned in: org.example.controller\n" +
                "  [*] Ready to handle requests!            \n" +
                "                                           \n" +
                "  [🔥] May your APIs be strong and your endpoints sharp!\n" +
                "                                           \n"
        );
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
            System.out.println("\n" +
                    "  ███████╗ ██████╗ ██████╗  ██████╗ ███████╗                               \n" +
                    "  ██╔════╝██╔═══██╗██╔══██╗██╔═══██╗██╔════╝                               \n" +
                    "  █████╗  ██║   ██║██████╔╝██║   ██║█████╗                                 \n" +
                    "  ██╔══╝  ██║   ██║██╔══██╗██║   ██║██╔══╝                                 \n" +
                    "  ██║     ╚██████╔╝██║  ██║╚██████╔╝███████╗                               \n" +
                    "  ╚═╝      ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ ╚══════╝                               \n" +
                    "                                                                           \n" +
                    "  :: HTTP Forge ::                (v1.0.0)                                 \n" +
                    "  Server stopped.                                                          \n" +
                    "  The forge cools down...                                                  \n" +
                    "                                                                           \n"
            );
        }
    }

}
