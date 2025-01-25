package org.example.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerConfig {
    private static final String CONFIG_FILE = "forge.config";

    public static int getPort() throws IOException {
        Properties props = new Properties();
        InputStream inputStream = ServerConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
        if (inputStream == null) {
            throw new FileNotFoundException("Config file not found: " + CONFIG_FILE);
        }
        Properties prosps = new Properties();
        props.load(inputStream);
        return Integer.parseInt(props.getProperty("server.port", "8080"));
    }
}
