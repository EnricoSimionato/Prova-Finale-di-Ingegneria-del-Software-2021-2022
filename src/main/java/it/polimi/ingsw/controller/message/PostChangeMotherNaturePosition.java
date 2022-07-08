package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.client.gui.guiControllers.BoardController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;

public class PostChangeMotherNaturePosition extends GameMessage {

    /**
     * message which permits changing the displayed situation after changing mother nature position
     * @param model
     * @param playerId
     */
    public PostChangeMotherNaturePosition(Game model, int playerId) {
        super(model, playerId);
    }

    /**
     * displays the updated towers and islands, shows the next game message
     * @param controller
     */
    @Override
    public void renderWhatNeeded(BoardController controller) {
        controller.displayMyTowers();
        controller.displayEnemyTowers();
        controller.displayIslands();
        controller.showGameMessage();
        if (getGameMode() == GameMode.EXPERT) controller.displayCharacter();
    }
}
