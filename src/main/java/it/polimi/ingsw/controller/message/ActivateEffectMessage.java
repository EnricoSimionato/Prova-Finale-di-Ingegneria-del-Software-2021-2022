package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.model.Game;

import java.io.Serializable;

public class ActivateEffectMessage extends PlayerMessage implements Serializable {
    private static final long serialVersionUID = 6L;
    private int indexCard;

    /**
     * message which permits activating character effects
     * @param playerId
     * @param indexCard
     */
    public ActivateEffectMessage(int playerId, int indexCard) {
        super(playerId);
        this.indexCard = indexCard;
    }

    /**
     * Calls the function on round to activate the effect for the chosen character
     * @param game
     */
    @Override
    public void performMove(Game game) {
        game.getRound().activateEffect(getPlayerId(), indexCard);
        game.sendGame();
    }
}