package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PawnColor;

public class StudentsRemovedFromTableMessage extends GameMessage {
    private PawnColor color;
    private int numberOfRemovedStudents;

    public StudentsRemovedFromTableMessage(Game model, int causingPlayerId, PawnColor color, int numberOfRemovedStudents) {
        super(model, causingPlayerId);
        this.color = color;
        this.numberOfRemovedStudents = numberOfRemovedStudents;
    }
}
