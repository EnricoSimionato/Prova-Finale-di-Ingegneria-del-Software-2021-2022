package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.client.gui.guiControllers.BoardController;
import it.polimi.ingsw.model.Game;

public class PostAddStudentOnIslandMessage extends GameMessage {

    /**
     * message which permits changing the displayed situation after adding student on islands
     * @param model
     * @param playerId
     */
    public PostAddStudentOnIslandMessage(Game model, int playerId) {
        super(model, playerId);
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
