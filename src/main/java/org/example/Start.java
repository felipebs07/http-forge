package org.example;

import org.example.config.ServerConfig;

public class Start {
    public static void main(String[] args) throws Exception {
        EmbeddedServer server = new EmbeddedServer();
        server.start(ServerConfig.getPort());
    }
}