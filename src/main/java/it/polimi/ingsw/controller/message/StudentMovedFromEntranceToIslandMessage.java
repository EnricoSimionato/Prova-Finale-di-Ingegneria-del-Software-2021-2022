package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.client.gui.guiControllers.BoardController;
import it.polimi.ingsw.model.Game;

public class StudentMovedFromEntranceToIslandMessage extends GameMessage {
    private int studentIndex;
    private int islandIndex;

    /**
     * Creates a message which changes the scene after a student has been moved from the entrance to an island
     * @param model entire game instance
     * @param causingPlayerId id of the player which causes the change of the model
     * @param studentIndex index of the student which has been moved
     * @param islandIndex index of the island where the student has been moved
     */
    public StudentMovedFromEntranceToIslandMessage(Game model, int causingPlayerId, int studentIndex, int islandIndex) {
        super(model, causingPlayerId);
        this.studentIndex = studentIndex;
        this.islandIndex = islandIndex;
    }

    /**
     * displays the updated entrances and islands, shows the next game message
     * @param controller
     */
    @Override
    public void renderWhatNeeded(BoardController controller) {
        if(getPlayerOnTurn() == getPlayerId())
            controller.displayMyEntrance();
        else
            controller.displayEnemyEntrance();
        //controller.displayIslandEffect();
        controller.displayIslands();
        controller.showGameMessage();
    }
}
