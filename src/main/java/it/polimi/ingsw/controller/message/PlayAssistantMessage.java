package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.model.Game;

import java.io.Serializable;

public class PlayAssistantMessage extends PlayerMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private int assistantPosition;

    /**
     * message which permits playing an assistant
     * @param playerId
     * @param assistantPosition
     */
    public PlayAssistantMessage(int playerId, int assistantPosition) {
        super(playerId);
        this.assistantPosition = assistantPosition;
    }

    /**
     * calls the model method to play an assistant
     * @param game
     */
    @Override
    public void performMove(Game game) {
        game.getRound().playAssistant(getPlayerId(), assistantPosition);
    }
}