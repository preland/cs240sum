package server;
import chess.ChessGame;
import chess.InvalidMoveException;
import org.eclipse.jetty.websocket.api.Session;
import com.google.gson.Gson;
import dataaccess.DataAccess;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.annotations.*;
//import spark.Session;
import service.ServiceException;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.Objects;

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
        //System.out.println(message);
    }

    private void handleConnect(Session session, String message, UserGameCommand cmd) {
        Server.sessions.replace(session, cmd.getGameID());
        String output = "Unknown";
        try {
            String username = DataAccess.getInstance().getUsername(cmd.getAuthToken());
            GameData gameData = DataAccess.getInstance().listGames().stream().filter(g -> g.gameID() == cmd.getGameID()).findFirst().orElse(null);
            if(gameData == null) {

                output = "game does not exist";
                throw new ServiceException(500);
            }

            ServerMessage msg = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            msg.setGame(gameData);
            notify(session, msg);
            //if(Objects.equals(gameData.whiteUsername(), username) || Objects.equals(gameData.blackUsername(), username)) {
                ServerMessage msg1 = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
                msg1.setMessage(username + " joined game");
                notifyAllBut(session, msg1);
            //}
        } catch (ServiceException | IOException e) {
            ServerMessage msg = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            msg.setErrorMessage(output);
            try {
                notify(session, msg);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            //throw new RuntimeException(e);
        }
    }

    private void handleLeave(Session session, String message, UserGameCommand cmd) {

    }

    private void handleMove(Session session, String message, UserGameCommand cmd) {
        String output = "unknown error";
        ServerMessage msg = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
        try {
            String username = DataAccess.getInstance().getUsername(cmd.getAuthToken());
            GameData gameData = DataAccess.getInstance().listGames().stream().filter(g -> g.gameID() == cmd.getGameID()).findFirst().orElse(null);
            if(username == null) {
                //todo: handle error
                output = "you are observing";
                throw new ServiceException(400);
            }
            if(gameData.game() == null) {
                //todo: handle
                output = "game does not exist";
                throw new ServiceException(500);
            }
            ChessGame.TeamColor team = Objects.equals(gameData.whiteUsername(), username) ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;

            if(gameData.game().getTeamTurn() != team && !Objects.equals(gameData.whiteUsername(), gameData.blackUsername())) {
                output = "it is not your turn!";
                throw new ServiceException(400);
            }
            try {
                gameData.game().makeMove(cmd.getMove());
                DataAccess.getInstance().updateGame(cmd.getGameID(), gameData.game());
            } catch (InvalidMoveException e) {
                msg = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
                output = "invalid move";
                msg.setMessage(output);
                notify(session, msg);
                return;
            }
            ChessGame.TeamColor opponent = Objects.equals(gameData.whiteUsername(), username) ? ChessGame.TeamColor.BLACK : ChessGame.TeamColor.WHITE;
            if(gameData.game().isInCheckmate(opponent)) {
                output = "game over; " + team + " wins";
            } else if(gameData.game().isInStalemate(opponent)) {
                output = "game over; stalemate";
            } else if(gameData.game().isInCheck(opponent)) {
                output = opponent + " is in check";
            } else {
                output = team + "has finished their move";
            }
            msg = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            msg.setGame(gameData);
            notifyAll(session, msg);
            msg = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
            msg.setMessage(output);
            notifyAllBut(session, msg);
        } catch (ServiceException | IOException e) {
            //throw new RuntimeException(e);
            msg.setMessage(output);
            try {
                notify(session, msg);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    private void handleResign(Session session, String message, UserGameCommand cmd) {
    }
    private void notifyAllBut(Session session, ServerMessage serverMessage) throws IOException{
        for(Session s : Server.sessions.keySet()) {
            if(session==s) {
                continue;
            }
            if(Objects.equals(Server.sessions.get(s), Server.sessions.get(session))) {
                notify(s, serverMessage);
            }
        }
        System.out.println("end loop");
    }
    private void notifyAll(Session session, ServerMessage serverMessage) throws IOException{
        for(Session s : Server.sessions.keySet()) {
            if(Objects.equals(Server.sessions.get(s), Server.sessions.get(session))) {
                notify(s, serverMessage);
            }
        }
    }
    private void notify(Session session, ServerMessage serverMessage) throws IOException {
        String message = new Gson().toJson(serverMessage);
        System.out.println(session.getRemote().toString());
        System.out.println(message);
        session.getRemote().sendString(message);
    }
}
