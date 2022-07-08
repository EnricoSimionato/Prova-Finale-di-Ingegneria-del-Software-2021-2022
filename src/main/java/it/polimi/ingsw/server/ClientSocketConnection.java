package it.polimi.ingsw.server;
import it.polimi.ingsw.controller.message.ConnectionState;
import it.polimi.ingsw.controller.message.SetupMessage;
import it.polimi.ingsw.controller.message.TerminatorMessage;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.controller.message.PlayerMessage;
import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocketConnection extends Observable<PlayerMessage> implements ClientConnection, Runnable {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Server server;
    private boolean active = true;

    /**
     * Creates an instance of ClientSocketConnection
     * @param socket socket of the client which try to connect, the classe communicates to the client by it
     * @param server server instance which manage the connections
     */
    public ClientSocketConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * Return the state of the connection
     * @return true, if the socket is open, false, if the socket is closed
     */
    private synchronized boolean isActive(){
        return active;
    }

    public synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
            out.reset();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public synchronized Object receive() {
        Object buffer = null;
        try {
            buffer = in.readObject();
        } catch(IOException e){
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return buffer;
    }

    public synchronized void closeConnectionNotifier() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    private void closeAndNotify() {
        closeConnectionNotifier();
        /////// eventuale controllo delle disconnessioni
        server.exitingPlayer(this);
        System.out.println("Connection " + this + " closed");

    }

    @Override
    public synchronized void closeConnection() {
        send(new TerminatorMessage("You have been disconnected. The match is over"));
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
        System.out.println("Connection " + this + " closed");
    }

    /**
     * Sends a message to the client in an asynchronous way
     * @param message message to send
     */
    @Override
    public void asyncSend(final Object message){
        new Thread(() -> send(message)).start();
    }

    @Override
    public void run() {
        try {
            Object buffer;

            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            send(new SetupMessage(ConnectionState.LOGIN, "Choose your nickname"));
            String playerNickname = "";
            boolean flag;
            do {
                buffer = in.readObject();
                flag = true;
                if (buffer instanceof SetupMessage && ((SetupMessage) buffer).getConnectionState() == ConnectionState.LOGIN && !((SetupMessage) buffer).getMessage().equals(""))
                    playerNickname = ((SetupMessage) buffer).getMessage();
                for (Match m : server.getAllMatchesOnServer()) {
                    for (String s : m.getPlayerNicknames()) {
                        if (s.equals(playerNickname)) {
                            flag = false;
                            send(new SetupMessage(ConnectionState.LOGIN, "The nickname is already chosen!\nChoose your nickname"));
                        }
                    }
                }
            } while (playerNickname.equals("") || !flag);
            System.out.println("The player choose is " + playerNickname);

            send(new SetupMessage(ConnectionState.MATCHMODE, "Welcome " + playerNickname + "\nChoose game mode { 0 : normal mode - 1 : expert mode } :"));
            int gameMode = -1;
            do {
                buffer = in.readObject();
                if (buffer instanceof SetupMessage && ((SetupMessage) buffer).getConnectionState() == ConnectionState.MATCHMODE && ((SetupMessage) buffer).getMessage() != null) {
                    try {
                        gameMode = Integer.parseInt(((SetupMessage) buffer).getMessage());
                    } catch (NumberFormatException e) {
                        send(new SetupMessage(ConnectionState.MATCHMODE, "Error : you are not sending the correct information\nChoose game mode { 0 : normal mode - 1 : expert mode } :"));
                    }
                }
                else
                    send(new SetupMessage(ConnectionState.MATCHMODE, "Error : you are not sending the correct information\nChoose game mode { 0 : normal mode - 1 : expert mode } :"));
            } while (gameMode != 0 && gameMode != 1);
            System.out.println("The player with socket " + this + " choose " + (gameMode == 0 ? "normal" : "expert") + " mode");

            send(new SetupMessage(ConnectionState.NUMBEROFPLAYERS, "Choose number of players { 2 : match with two players - 3 : match with three players - 4 : match with four players} :"));
            int numberOfPlayers = 0;
            do {
                buffer = in.readObject();
                if (buffer instanceof SetupMessage && ((SetupMessage) buffer).getConnectionState() == ConnectionState.NUMBEROFPLAYERS) {
                    try {
                        numberOfPlayers = Integer.parseInt(((SetupMessage) buffer).getMessage());
                    } catch (NumberFormatException e) {
                        send(new SetupMessage(ConnectionState.MATCHMODE, "Error : you are not sending the correct information\nChoose game mode { 0 : normal mode - 1 : expert mode } :"));
                    }
                } else
                    send(new SetupMessage(ConnectionState.NUMBEROFPLAYERS, "Error : you are not sending the correct information\nChoose number of players { 2 : match with two players - 3 : match with three players - 4 : match with four players} :"));
            } while (numberOfPlayers < 2 || numberOfPlayers > 4);
            System.out.println("The player with socket " + this + " choose " + numberOfPlayers + " players mode");

            System.out.println("Adding " + playerNickname + " into the lobby (player : " + this + ")");

            server.lobby((gameMode == 0 ? GameMode.NORMAL : GameMode.EXPERT), numberOfPlayers, playerNickname, this);

            while (isActive()) {
                buffer = in.readObject();
                if (buffer instanceof SetupMessage) {

                } else if (buffer instanceof PlayerMessage) {
                    PlayerMessage clientMessage = (PlayerMessage) buffer;
                    if (server.getMyId(this) == clientMessage.getPlayerId())
                        notify(clientMessage);
                    else
                        //Invia l'errore al client
                        System.out.println(this + " is sending a message with an other's player id");
                } else {
                    System.out.println(this + " is sending a wrong object");
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("The client sends an unknown object");
        } catch (IOException e) {
            System.err.println(this + " is trying to close the connection");
        } finally {
            closeAndNotify();
        }
    }
}
