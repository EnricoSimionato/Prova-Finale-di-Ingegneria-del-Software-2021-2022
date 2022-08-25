package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.client.gui.guiControllers.BoardController;
import it.polimi.ingsw.model.Game;

public class StudentMovedFromCloudToEntranceMessage extends GameMessage {
    private int cloudIndex;
    /**
     * Creates a message which changes the scene after a student has been moved from the entrance to a table
     * @param model entire game instance
     * @param causingPlayerId id of the player to which the message has to be sent
     * @param cloudIndex index of the cloud which has to be rendered
     */
    public StudentMovedFromCloudToEntranceMessage(Game model, int causingPlayerId, int cloudIndex) {
        super(model, causingPlayerId);
        this.cloudIndex = cloudIndex;
    }

    /**
     * displays the updated entrances, clouds, assistants and shows the next game message
     * @param controller
     */
    @Override
    public void renderWhatNeeded(BoardController controller) {
        if(controller.getMyPlayerId() == getCausingPlayerId())
            controller.displayMyEntrance();
        else
            controller.displayEnemyEntrance();
        controller.displayCloud(cloudIndex);
        //controller.displayAssistants();
        //controller.showGameMessage();
    }
}

