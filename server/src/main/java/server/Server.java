package server;

import chess.ChessPiece;
import com.google.gson.Gson;
import model.GameData;
import model.UserData;
import service.Handler;
import service.Service;
import service.ServiceException;
import spark.*;

import java.util.ArrayList;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", (request, response) -> {
            //response.status(200);

            try {
                Handler.getInstance().clear();
                response.status(200);
                //throw new ServiceException("error go away pls");
                return "{}";
            } catch (ServiceException e) {
                response.status(500);
                return e.toString();
            }

        });
        Spark.post("/user", (request, response) -> {
            try {
                String userData = Handler.getInstance().register(request.body());
                response.status(200);
                return userData;
            } catch (ServiceException e) {
                //todo: implement proper failure modes
                throw new RuntimeException(e);
            }
        });
        Spark.post("/session", (request, response) -> {
            try {
                String authData = Handler.getInstance().login(request.body());
                response.status(200);
                return authData;
            } catch (ServiceException e) {
                //todo: implement proper failure modes
                throw new RuntimeException(e);
            }
        });
        Spark.delete("/session", (request, response) -> {
            try {
                Handler.getInstance().logout(request.body());
                response.status(200);
                return "{}";
            } catch (ServiceException e) {
                //todo: implement proper failure modes
                throw new RuntimeException(e);
            }
        });
        Spark.get("/game", (request, response) -> {
            try {
                ArrayList<GameData> gameData = Handler.getInstance().listGames(request.body());
                response.status(200);
                return gameData.toString();
            } catch (ServiceException e) {
                //todo: implement proper failure modes
                throw new RuntimeException(e);
            }
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
