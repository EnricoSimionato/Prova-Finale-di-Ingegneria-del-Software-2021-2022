package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.model.Game;

import java.io.Serializable;

public class AddStudentOnTableMessage extends PlayerMessage implements Serializable {
    private static final long serialVersionUID = 3L;
    private int studentIndex;

    /**
     * message which permits adding students on tables
     * @param playerId
     * @param studentIndex
     */
    public AddStudentOnTableMessage(int playerId, int studentIndex) {
        super(playerId);
        this.studentIndex = studentIndex;
    }

    /**
     * calls the model method to add the student on table
     * @param game
     */
    @Override
    public void performMove(Game game) {
        game.getRound().addStudentOnTable(getPlayerId(), studentIndex);
    }
}
