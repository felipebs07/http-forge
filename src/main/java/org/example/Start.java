package org.example;

import org.example.annotation.start.ForgerBoot;
import org.example.config.ServerConfig;

@ForgerBoot
public class Start {
    public static void main(String[] args) throws Exception {
        EmbeddedServer server = new EmbeddedServer();
        server.start(ServerConfig.getPort());
    }
}