package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.InvalidIndexException;

import java.io.Serializable;

public enum PawnColor implements Serializable {
    GREEN(0),  RED(1), YELLOW(2), PINK(3), BLUE(4);
    private final int index;

    /**
     * Constructs an element of the enumeration
     * @param index unique integer value associated with each value of the enumeration. YELLOW index is 0, BLUE index is 1, GREEN index is 2, RED index is 3, PINK index is 4
     */
    PawnColor(int index) { this.index = index; }

    /**
     * Returns the index associated with the specific value of the enumeration
     * @return unique index associated with the specific value of the enumeration
     */
    public int getIndex() { return index; }

    /**
     * Return the associate Pawn Color for the received index
     * @param index
     * @throws InvalidIndexException
     */
    public static PawnColor associateIndexToPawnColor(int index) throws InvalidIndexException {

        PawnColor pc = null;

        if(index<0 || index>4)
            throw new InvalidIndexException("There is no such pawn color");

        switch (index){
            case 0:
                pc=PawnColor.GREEN;
                break;
            case 1:
                pc=PawnColor.RED;
                break;
            case 2:
                pc=PawnColor.YELLOW;
                break;
            case 3:
                pc=PawnColor.PINK;
                break;
            case 4:
                pc=PawnColor.BLUE;
                break;
        }

        return pc;
    }
}
