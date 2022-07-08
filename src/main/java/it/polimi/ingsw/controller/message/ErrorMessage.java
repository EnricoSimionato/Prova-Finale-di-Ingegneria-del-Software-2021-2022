package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.client.gui.guiControllers.BoardController;
import it.polimi.ingsw.model.Game;

public class ErrorMessage extends GameMessage {

    /**
     * message which permits the signalisation of an error that has occoured
     * @param model
     * @param playerId
     */
    public ErrorMessage(Game model, int playerId) {
        super(model, playerId);
    }

    /**
     * displays the error message
     * @param controller
     */
    @Override
    public void renderWhatNeeded(BoardController controller) {
        controller.showGameMessage();
    }
}
