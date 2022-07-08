package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.model.Game;

import java.io.Serializable;

public abstract class PlayerMessage extends Message implements Serializable {
    private int playerId;

    /**
     *
     * @param playerId
     */
    public PlayerMessage(int playerId) {
        this.playerId = playerId;
    }

    /**
     * abstract class which will allow to perform the move depending on the message sub-type
     */
    public abstract void performMove(Game game);

    /**
     * returns the playerId attribute
     * @return
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * sets the playerId attribute with the value passed by parameter
     * @param playerId
     */
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}