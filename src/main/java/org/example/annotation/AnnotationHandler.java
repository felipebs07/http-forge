package org.example.annotation;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.annotation.rest.Controller;
import org.example.annotation.rest.RequestMapping;
import org.example.annotation.start.ForgerBoot;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * HttpHandle é parte do sun.net.httpserver, ela é responsável por manipular request http recebida pelo servidor.
 */
public class AnnotationHandler implements HttpHandler {
    private final Map<String, Method> routerHandlers = new HashMap<>();

    public void scanControllers(String packageName) throws Exception {

        Map<String, List<Class<?>>> mapAnnotation =  findClasses(packageName);

        for(var annotation : mapAnnotation.values()) {
            annotation.forEach(clazz -> {
                try {
                    registerController(clazz.getDeclaredConstructor().newInstance());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public static Map<String, List<Class<?>>> findClasses(String packageName) throws Exception {
        List<Class<?>> classBoot = new ArrayList<>();
        List<Class<?>> classController = new ArrayList<>();

        String path = packageName.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource(path);

        if(resource == null) {
            throw new Exception("Package not found: " + packageName);
        }
        List<File> files = listAllFiles(new File(resource.getFile()));
        System.out.println(files);

        for(File file : files) {
            if(file.getName().endsWith(".class")) {
                String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                Class<?> clasz = Class.forName(className);


                // PACKAGE NAME IS ORG.EXAMPLE
                // VOU CRIAR UMA FORMA DE IR ADICIONANDO ROTAS ATE CHEGAR NA CLASS FINAL
                // ORG.EXAMPLE (DEU ERRO?) NEXT
                // ORG.EXAMPLE.PASTA (DEU ERRO?) PEGA A PROXIMA

                if(clasz.isAnnotationPresent(ForgerBoot.class)) {
                    classBoot.add(clasz);
                }

                if(clasz.isAnnotationPresent(Controller.class)) {
                    classController.add(clasz);
                }
            }
        }

        Map<String, List<Class<?>>> mapingAnnotation = new HashMap<>();
        mapingAnnotation.put("boot", classBoot);
        mapingAnnotation.put("controllers", classController);

        return mapingAnnotation;
    }

    public static List<File> listAllFiles(File file) {
        List<File> files = new ArrayList<>();
        if(file.isDirectory()) {
            File[] children = file.listFiles();

            if(children != null) {
                for(File child : children) {
                    if(child.isDirectory()) {
                        files.addAll(listAllFiles(child));
                    } else {
                        files.add(child);
                    }
                }
            }
        }
        return files;
    }

    public void registerController(Object controller) {
        Class<?> clasz = controller.getClass();
        String pathController = clasz.getDeclaredAnnotation(Controller.class).path();
       for(Method method: clasz.getDeclaredMethods()) {
          if(method.isAnnotationPresent(RequestMapping.class)) {
              RequestMapping annotation = method.getAnnotation(RequestMapping.class);

              String path = pathController + annotation.path();
              String httpMethod = annotation.method().toUpperCase();
              routerHandlers.put(httpMethod + " " + path, method);
          }
       }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod().toUpperCase();
        String requestPath = exchange.getRequestURI().getPath();

        String key = requestMethod + " " + requestPath;
        Method method = routerHandlers.get(key);

        String response = "";
        int status = 404;

        if(method != null) {
            try {
                Object controller = method.getDeclaringClass().getDeclaredConstructor().newInstance();

                response = (String) method.invoke(controller);
                status = 200;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response = "Route not founded";
        }

        exchange.sendResponseHeaders(status, response.length());
        try(OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
