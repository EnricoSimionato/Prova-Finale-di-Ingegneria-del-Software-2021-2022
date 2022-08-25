package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.client.gui.guiControllers.BoardController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;

public class MotherNatureMovedMessage extends GameMessage {
    private int islandIndex;

    /**
     * Creates a message which changes the scene after mother nature moved
     * @param model entire game instance
     * @param causingPlayerId id of the player which causes the change of the model
     * @param islandIndex index of the island where mother nature moved
 */
    public MotherNatureMovedMessage(Game model, int causingPlayerId, int islandIndex) {
        super(model, causingPlayerId);
        this.islandIndex = islandIndex;
    }

    /**
     * Displays the updated towers and islands, shows the next game message
     * @param controller
     */
    @Override
    public void renderWhatNeeded(BoardController controller) {
        controller.displayIsland(islandIndex);
        controller.displayMyTowers();
        controller.displayEnemyTowers();
        controller.showGameMessage();
        //if (getGameMode() == GameMode.EXPERT) controller.displayCharacter();
    }
}
