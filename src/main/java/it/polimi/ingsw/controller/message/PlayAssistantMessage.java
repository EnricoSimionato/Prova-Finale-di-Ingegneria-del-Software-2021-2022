package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.model.Game;

import java.io.Serializable;

public class PlayAssistantMessage extends PlayerMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private int assistantPosition;

    /**
     * Creates a message which permits to play an assistant
     * @param playerId identifier of the player which chooses the assistant
     * @param assistantPosition position of the chosen assistant
     */
    public PlayAssistantMessage(int playerId, int assistantPosition) {
        super(playerId);
        this.assistantPosition = assistantPosition;
    }

    /**
     * Calls the model method for playing an assistant
     * @param game instance of the current match
     */
    @Override
    public void performMove(Game game) {
        game.getRound().playAssistant(getPlayerId(), assistantPosition);
    }
}