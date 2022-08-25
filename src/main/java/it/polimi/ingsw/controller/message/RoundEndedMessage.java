package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.client.gui.guiControllers.BoardController;
import it.polimi.ingsw.model.Game;

public class RoundEndedMessage extends GameMessage {

    /**
     * Creates a message which changes the scene after a round ended
     * @param model entire game instance
     * @param causingPlayerId id of the player to which the message has to be sent
     */
    public RoundEndedMessage(Game model, int causingPlayerId) {
        super(model, causingPlayerId);
    }

    @Override
    public void renderWhatNeeded(BoardController controller) {
        controller.displayAssistants();
        controller.showGameMessage();
    }
}