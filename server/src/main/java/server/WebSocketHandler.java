package server;
//import org.eclipse.jetty.websocket.api.Session;
import com.google.gson.Gson;
import dataaccess.DataAccess;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.annotations.*;
import spark.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

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
                handleConnect(session, message, cmd);
                break;
            case LEAVE:
                handleLeave(session, message, cmd);
                break;
            case MAKE_MOVE:
                handleMove(session, message, cmd);
                break;
            case RESIGN:
                handleResign(session, message, cmd);
                break;
            default:
                break;
        }
        System.out.println(message);
    }

    private void handleConnect(Session session, String message, UserGameCommand cmd) {
        Server.sessions.replace(session, cmd.getGameID());
        try {
            GameData gameData = DataAccess.getInstance().listGames().stream().filter(g -> g.gameID() == cmd.getGameID());
            ServerMessage msg = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        }
    }

    private void handleLeave(Session session, String message) {
    }

    private void handleMove(Session session, String message) {
    }

    private void handleResign(Session session, String message) {
    }
    private void notify(Session session, ServerMessage serverMessage) {
        String message = new Gson().toJson(serverMessage);

    }
}
