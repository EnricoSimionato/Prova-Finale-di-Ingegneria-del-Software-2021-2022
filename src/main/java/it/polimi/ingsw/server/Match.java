package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;

import java.util.ArrayList;
import java.util.List;

public class Match {

    private final int matchId;
    private final GameMode gameMode;
    private int numberOfPlayers;
    private final List<String> playerNicknames = new ArrayList<>();
    private final List<ClientConnection> sockets = new ArrayList<>();
    private Game model;

    /**
     * Creates a match instance - maybe to manage reconnection and related issues
     * @param matchId match identifier
     * @param gameMode match mode
     * @param numberOfPlayers number of players playing match
     * @param socket first player's socket
     */

    public Match(int matchId, GameMode gameMode, int numberOfPlayers, String playerNickname, ClientConnection socket) {
        this.matchId = matchId;
        this.gameMode = gameMode;
        this.numberOfPlayers = numberOfPlayers;
        playerNicknames.add(playerNickname);
        sockets.add(socket);
        model = null;
    }

    /**
     * Sets the instance of the game of the match
     * @param model game instance which manage the progression of the match
     */
    public void setModel(Game model){
        this.model = model;
    }

    /**
     * Returns the instance of the game of the match
     * @return the instance of the game of the match
     */
    public Game getModel(){
        return model;
    }

    /**
     * Returns a number which identifies the specific match
     * @return match identifier
     */
    public int getMatchId() {
        return matchId;
    }

    /**
     * Returns the instance of the current match
     * @return this match
     */
    public Match getMatch() {
        return this;
    }

    /**
     * Returns the mode, normal or expert, of the match
     * @return match mode
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * Returns the number of players which play in the game
     * @return number of player playing the match
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Returns the players' nicknames used during the match
     * @return list containing the players' nicknames
     */
    public List<String> getPlayerNicknames() {
        return new ArrayList<>(playerNicknames);
    }

    /**
     * Returns the players' socket by which the server can communicate with the clients
     * @return list containing the players' sockets
     */
    public List<ClientConnection> getSockets() {
        return new ArrayList<>(sockets);
    }

    /**
     * Adds a new player to the match adding his nickname and his socket to the current match
     * @param socket socket for communicating with the new player
     * @param playerNickname new player's nickname
     */
    public void addPlayer(ClientConnection socket, String playerNickname) {
        playerNicknames.add(playerNickname);
        sockets.add(socket);
    }
}
