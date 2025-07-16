package server;

import com.google.gson.Gson;
import service.Service;
import service.ServiceException;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", (request, response) -> {
            //response.status(200);

            try {
                //todo: clear database
                Service.getInstance().clear();
                response.status(200);
                //throw new ServiceException("error go away pls");
                return "{}";
            } catch (ServiceException e) {
                response.status(500);
                return e.toString();
            }

        });
        Spark.post("/user", (request, response) -> {

        });
        //This line initializes the server and can be removed once you have a functioning endpoint 
        //Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
