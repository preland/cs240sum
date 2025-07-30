package ui;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import model.GameData;
import model.ListGameResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ServerFacade {
    //ight we gotta listen for stuff somehow....lovely
    int port;
    public ServerFacade() {
        this(8080);
    }
    public ServerFacade(int port) {
        this.port = port;
    }

    public String login(String username, String password) {
        Map map = Map.of("username", username, "password", password);
        String body = new Gson().toJson(map);
        String req = request("POST", "/session", body, null);
        if(req.equals("401")) {
            return "401";
        }
        try {
            String auth = new Gson().fromJson(req, Map.class).get("authToken").toString();
            return auth;
        } catch (JsonSyntaxException e) {
            return "401";
        }

    }

    public String register(String username, String password, String email) {
        Map map = Map.of("username", username, "password", password, "email", email);
        String body = new Gson().toJson(map);
        String req = request("POST", "/user", body, null);
        if(req.equals("connerror")) {
            return "connerror";
        } else if (req.equals("403")) {
            return "403";
        }
        //System.out.println(req);
        //todo: actual error impl
        return login(username, password);
    }
    public String logout(String auth) {
        String req = request("DELETE", "/session", null, auth);
        if(Objects.equals(req, "connerror")) {
            return req;
        }
        return "";
    }
    public String joinGame(int gameID, boolean isWhite, String auth) {
        Map map = Map.of("gameID", gameID, "playerColor", isWhite ? "WHITE" : "BLACK");
        String body = new Gson().toJson(map);
        String req = request("PUT", "/game", body, auth);
        return req;
    }
    public ArrayList<GameData> listGames(String auth) {
        String req = request("GET", "/game", null, auth);
        ArrayList<GameData> rawGames = new Gson().fromJson(req, ListGameResponse.class).games();
        return rawGames;
    }
    public String createGame(String gameName, String auth) {
        Map map = Map.of("gameName", gameName);
        String body = new Gson().toJson(map);
        String req = request("POST", "/game", body, auth);

        try {
            //System.out.println(req);
            String id = new Gson().fromJson(req, Map.class).get("gameID").toString();
            return id;
        } catch (NullPointerException e) {
            return "internalerror";
        }

    }
    private String request(String method, String path, String body, String auth) {
        try {
            HttpURLConnection http = (HttpURLConnection) new URI("http://localhost:"+port+path).toURL().openConnection();
            http.setRequestMethod(method);
            if(auth != null) {
                http.addRequestProperty("authorization", auth);
            }
            if(body != null) {
                http.addRequestProperty("Content-Type", "application/json");
                http.setDoOutput(true);
                var stream = http.getOutputStream();
                stream.write(body.getBytes());
                stream.flush();
                stream.close();
            }
            http.connect();

            try(InputStream ret = http.getInputStream()) {
                return new String(ret.readAllBytes());
            }
        } catch (URISyntaxException e) {
            //throw new RuntimeException(e);
            return "500";
        } catch (IOException e) {
            //Server returned HTTP response code: 403 for URL:
            //System.out.println(e.getMessage().substring(36,39));
            return e.getMessage().substring(36,39);//e.getMessage();
        }
    }
    public void clear() {

        System.out.println(request("DELETE", "/db", null, null));
    }
    //public void
}
