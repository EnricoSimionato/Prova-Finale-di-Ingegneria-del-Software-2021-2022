package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.client.gui.guiControllers.BoardController;
import it.polimi.ingsw.model.Game;

public class PostPlayAssistantMessage extends GameMessage {

    /**
     * message which permits changing the displayed situation after playing an assistant
     * @param model
     * @param playerId
     */
    public PostPlayAssistantMessage(Game model, int playerId) {
        super(model, playerId);
    }

    /**
     * displays the updated entrances, clouds, assistants and shows the next game message
     * @param controller
     */
    @Override
    public void renderWhatNeeded(BoardController controller) {
        controller.displayAssistants();
        controller.displayEnemyAssistant();
        controller.displayMyEntrance();
        controller.displayEnemyEntrance();
        controller.displayClouds();
        controller.showGameMessage();
    }

}
