package ui;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class ServerFacade {
    //ight we gotta listen for stuff somehow....lovely
    int port;
    public ServerFacade() {
        this(8000);
    }
    public ServerFacade(int port) {
        this.port = port;
    }

    public String login(String username, String password) {
        Map map = Map.of("username", username, "password", password);
        String body = new Gson().toJson(map);
        String req = request("POST", "/session", body);
        if(req.equals("connerror")) {
            return "connerror";
        }
        try {
            String auth = new Gson().fromJson(req, Map.class).get("authToken").toString();
            return auth;
        } catch (JsonSyntaxException e) {
            return "autherror";
        }

    }

    public String register(String username, String password, String email) {
        Map map = Map.of("username", username, "password", password, "email", email);
        String body = new Gson().toJson(map);
        String req = request("POST", "/user", body);
        if(req.equals("connerror")) {
            return "connerror";
        }
        //System.out.println(req);
        //todo: actual error impl
        return login(username, password);
    }
    private String request(String method, String path, String body) {
        try {
            HttpURLConnection http = (HttpURLConnection) new URI("http://localhost:"+port+path).toURL().openConnection();
            http.setRequestMethod(method);

            if(body != null) {
                http.setDoOutput(true);
                http.addRequestProperty("Content-Type", "application/json");
                var stream = http.getOutputStream();
                stream.write(body.getBytes());
                stream.flush();
                stream.close();
            }
            http.connect();

            try(InputStream ret = http.getInputStream()) {
                return new String(ret.readAllBytes());
            }
        } catch (URISyntaxException | IOException e) {
            //throw new RuntimeException(e);
            return "connerror";
        }
    }
    //public void
}
