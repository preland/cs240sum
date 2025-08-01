package server;

import chess.ChessPiece;
import com.google.gson.Gson;
import model.GameData;
import model.UserData;
import server.Handler;
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
                response.status(e.getErrorCode());
                return e.getMessage();
            }

        });
        Spark.post("/user", (request, response) -> {
            try {
                String userData = Handler.getInstance().register(request.body());
                response.status(200);
                return userData;
            } catch (ServiceException e) {
                //todo: implement proper failure modes
                //throw new RuntimeException(e);
                response.status(e.getErrorCode());
                return e.getMessage();
            }
        });
        Spark.post("/session", (request, response) -> {
            try {
                String authData = Handler.getInstance().login(request.body());
                response.status(200);
                return authData;
            } catch (ServiceException e) {
                //todo: implement proper failure modes
                //throw new RuntimeException(e);
                response.status(e.getErrorCode());
                return e.getMessage();
            }
        });
        Spark.delete("/session", (request, response) -> {
            try {
                Handler.getInstance().logout(request.body(), request.headers("authorization"));
                response.status(200);
                return "{}";
            } catch (ServiceException e) {
                //todo: implement proper failure modes
                //throw new RuntimeException(e);
                response.status(e.getErrorCode());
                return e.getMessage();
            }
        });
        Spark.get("/game", (request, response) -> {
            try {
                String gameData = Handler.getInstance().listGames(request.body(), request.headers("authorization"));
                response.status(200);
                return gameData;
            } catch (ServiceException e) {
                //todo: implement proper failure modes
                //throw new RuntimeException(e);
                response.status(e.getErrorCode());
                return e.getMessage();
            }
        });
        Spark.post("/game", (request, response) -> {
           try {
               String gameID = Handler.getInstance().createGame(request.body(), request.headers("authorization"));
               response.status(200);
               return gameID;
           } catch (ServiceException e) {
               //todo: implement proper failure modes
               //throw new RuntimeException(e);
               response.status(e.getErrorCode());
               return e.getMessage();
           }
        });
        Spark.put("/game", (request, response) -> {
            try {
                Handler.getInstance().joinGame(request.body(), request.headers("authorization"));
                response.status(200);
                return "{}";
            } catch (ServiceException e) {
                //todo: implement proper failure modes
                //throw new RuntimeException(e);
                //todo: impl proper response codes
                response.status(e.getErrorCode());
                return e.getMessage();
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
