package org.example.annotation;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.ClassScaner;
import org.example.annotation.rest.Controller;
import org.example.annotation.rest.RequestMapping;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HttpHandle é parte do sun.net.httpserver, ela é responsável por manipular request http recebida pelo servidor.
 */
public class AnnotationHandler implements HttpHandler {
    private final Map<String, Method> routerHandlers = new HashMap<>();

    public void scanControllers(String packageName) throws Exception {
        List<Class<?>> controllerClasses = ClassScaner.findClasses(packageName);
        for(Class<?> clasz : controllerClasses) {
            registerController(clasz.getDeclaredConstructor().newInstance());
        }
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
