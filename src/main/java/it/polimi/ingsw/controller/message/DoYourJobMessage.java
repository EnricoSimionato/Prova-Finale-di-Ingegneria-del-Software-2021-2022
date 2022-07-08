package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.exception.InvalidIndexException;

public class DoYourJobMessage extends PlayerMessage{
    private int parameter;

    /**
     * message which permits to do the character job
     * @param playerId
     * @param parameter
     */
    public DoYourJobMessage(int playerId, int parameter) {
        super(playerId);
        this.parameter = parameter;
    }

    /**
     * calls the model method to do the character job
     * @param game
     */
    @Override
    public void performMove(Game game) {
        try {
            game.getRound().doYourJob(getPlayerId(), parameter);
            game.sendGame();
        } catch (InvalidIndexException e) {
        }
    }
}
