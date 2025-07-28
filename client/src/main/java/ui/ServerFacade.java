package ui;

import java.net.HttpURLConnection;
import java.net.URI;

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
            HttpURLConnection http = new URI("http://localhost:"+port+path).toURL().openConnection();
            http.setRequestMethod(method);

        }
    }
    //public void
}
