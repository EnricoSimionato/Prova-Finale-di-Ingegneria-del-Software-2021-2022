package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.model.Game;

import java.io.Serializable;

public class AddStudentOnIslandMessage extends PlayerMessage implements Serializable {
    private static final long serialVersionUID = 2L;
    private int studentIndex;
    private int islandIndex;

    /**
     * message which permits adding students on islands
     * @param playerId
     * @param studentIndex
     * @param islandIndex
     */
    public AddStudentOnIslandMessage(int playerId, int studentIndex, int islandIndex) {
        super(playerId);
        this.studentIndex = studentIndex;
        this.islandIndex = islandIndex;
    }

    /**
     * calls the model method to add the student on island
     * @param game
     */
    @Override
    public void performMove(Game game) {
        game.getRound().addStudentOnIsland(getPlayerId(), studentIndex, islandIndex);
    }
}
