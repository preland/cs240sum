package ui;

import com.google.gson.Gson;

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

    public boolean login(String username, String password) {
        Map map = Map.of("username", username, "password", password);
        String body = new Gson().toJson(map);
        request("POST", "/session", body);
        //todo: actual error impl
        //todo: handle authToken stuff
        return true;
    }

    public boolean register(String username, String password, String email) {
        Map map = Map.of("username", username, "password", password, "email", email);
        String body = new Gson().toJson(map);
        request("POST", "/user", body);
        //todo: actual error impl
        return true;
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
            throw new RuntimeException(e);
        }
    }
    //public void
}
