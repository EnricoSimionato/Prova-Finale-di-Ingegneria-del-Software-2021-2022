package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.client.gui.guiControllers.BoardController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.PawnColor;

public class StudentMovedFromEntranceToTableMessage extends GameMessage {
    private PawnColor color;
    private int studentOnTableIndex;
    private int studentOnEntranceIndex;
    /**
     * Creates a message which changes the scene after a student has been moved from the entrance to a table
     * @param model entire game instance
     * @param causingPlayerId of the player to which the message has to be sent
     * @param color color of the table which has to be rendered
     * @param studentOnTableIndex index of the table where the student has been added
     * @param studentOnEntranceIndex index of the entrance from where the student has been moved
     */
    public StudentMovedFromEntranceToTableMessage(Game model, int causingPlayerId, PawnColor color, int studentOnTableIndex, int studentOnEntranceIndex) {
        super(model, causingPlayerId);
        this.color = color;
        this.studentOnTableIndex = studentOnTableIndex;
        this.studentOnEntranceIndex = studentOnEntranceIndex;
    }

    /**
     * Displays the updated entrances, professors and shows the game message
     * @param controller GUI controller which manages the scene
     */
    @Override
    public void renderWhatNeeded(BoardController controller) {
        if (controller.getMyPlayerId() == getCausingPlayerId()) {
            controller.displayStudentOnMyEntrance(studentOnEntranceIndex);
            controller.displayMyStudentOnTable(color, studentOnTableIndex, studentOnTableIndex);
            controller.displayMyProfessor(color);
        } else {
            controller.displayStudentOnEnemyEntrance(studentOnEntranceIndex);
            controller.displayEnemyStudentOnTable(color, studentOnTableIndex, studentOnTableIndex);
            controller.displayEnemyProfessor(color);
        }
        //controller.displayIslandEffect();
        controller.showGameMessage();
    }
}
