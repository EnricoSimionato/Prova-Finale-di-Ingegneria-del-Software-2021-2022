package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.client.gui.guiControllers.BoardController;
import it.polimi.ingsw.model.Game;

public class PostGetStudentsFromCloudsMessage extends GameMessage {

    /**
     * message which permits changing the displayed situation after getting students from cloud
     * @param model
     * @param playerId
     */
    public PostGetStudentsFromCloudsMessage(Game model, int playerId) {
        super(model, playerId);
    }

    /**
     * displays the updated entrances, clouds, assistants and shows the next game message
     * @param controller
     */
    @Override
    public void renderWhatNeeded(BoardController controller) {
        controller.displayMyEntrance();
        controller.displayEnemyEntrance();
        controller.displayClouds();
        controller.displayAssistants();
        controller.showGameMessage();
    }
}

