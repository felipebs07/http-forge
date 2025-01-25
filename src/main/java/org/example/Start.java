package org.example;


import com.sun.net.httpserver.HttpServer;
import org.example.annotation.AnnotationHandler;

import java.net.InetSocketAddress;

public class Start {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        AnnotationHandler handler = new AnnotationHandler();

        handler.scanControllers("org.example.controller");

        server.createContext("/", handler);

        server.start();
        System.out.println("Running server in port 8000");
    }
}