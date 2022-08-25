package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.client.gui.guiControllers.BoardController;
import it.polimi.ingsw.model.Game;

public class AssistantPlayedMessage extends GameMessage {

    /**
     * Creates a message which change the scene after a player chooses an assistant
     * @param model entire game instance
     * @param causingPlayerId id of the player which causes the change of the model
     * */
    public AssistantPlayedMessage(Game model, int causingPlayerId) {
        super(model, causingPlayerId);
    }

    /**
     * Displays the updated entrances, clouds, assistants and shows the next game message//////////////
     * @param controller
     */
    @Override
    public void renderWhatNeeded(BoardController controller) {
        controller.displayAssistants();
        controller.displayEnemyAssistant();
        //controller.displayMyEntrance();
        //controller.displayEnemyEntrance();
        //controller.displayClouds();
        controller.showGameMessage();
    }

}
