package server;
//import org.eclipse.jetty.websocket.api.Session;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.annotations.*;
import spark.Session;
import websocket.commands.UserGameCommand;

@WebSocket
public class WebSocketHandler {
    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        Server.sessions.put(session, 0);
    }
    @OnWebSocketClose
    public void onClose(Session session, int num, String str) {
        Server.sessions.remove(session);
    }
    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        UserGameCommand cmd = new Gson().fromJson(message, UserGameCommand.class);
        switch(cmd.getCommandType()) {
            case CONNECT:
                handleConnect(session, message);
                break;
            case LEAVE:
                handleLeave(session, message);
                break;
            case MAKE_MOVE:
                handleMove(session, message);
                break;
            case RESIGN:
                handleResign(session, message);
                break;
            default:
                break;
        }
        System.out.println(message);
    }

    private void handleConnect(Session session, String message) {
    }

    private void handleLeave(Session session, String message) {
    }

    private void handleMove(Session session, String message) {
    }

    private void handleResign(Session session, String message) {
    }
}
