package ui;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

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
    }

    public boolean register(String username, String password, String email) {
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
