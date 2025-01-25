package org.example;

import org.example.annotation.rest.Controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassScaner {
    public static List<Class<?>> findClasses(String packageName) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        URL resource = classLoader.getResource(path);

        if(resource == null) {
            throw new Exception("Package not found: " + packageName);
        }

        File directory = new File(resource.getFile());

        if(directory.exists()) {
            for(File file : Objects.requireNonNull(directory.listFiles())) {
                if(file.getName().endsWith(".class")) {
                    String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                    Class<?> clasz = Class.forName(className);

                    if(clasz.isAnnotationPresent(Controller.class)) {
                        classes.add(clasz);
                    }
                }
            }
        }

        return classes;
    }
}
