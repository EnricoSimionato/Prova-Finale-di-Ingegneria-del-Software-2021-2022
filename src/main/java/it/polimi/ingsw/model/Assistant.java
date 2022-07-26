package it.polimi.ingsw.model;

import java.io.Serializable;

public class Assistant implements Serializable {
    private int cardValue;
    private int motherNatureMoves;
    private final int NUMBEROFASSISTANTS = 10;
    private final int MAXIMUMNUMBEROFMOVES = 5;

    private static final String assistants = "1,1;2,1;3,2;4,2;5,3;6,3;7,4;8,4;9,5;10,5";


    /**
     * Creates an assistant card with the given two values
     * @param cardValue value of the assistant card
     * @param motherNatureMoves maximum number of moves that mother nature can perform when a player plays the assistant card
     */
    public Assistant(int cardValue, int motherNatureMoves) {
        if (cardValue > 0 && cardValue <= NUMBEROFASSISTANTS) this.cardValue = cardValue;
        if (motherNatureMoves > 0 && motherNatureMoves <= MAXIMUMNUMBEROFMOVES) this.motherNatureMoves = motherNatureMoves;
    }

    /**
     * Returns the value of the card
     * @return value of the assistant card
     */
    public int getCardValue() {
        return cardValue;
    }

    /**
     * Returns the maximum number of movements mother nature can perform after the player plays this assistant card
     * @return maximum number of movements mother nature can perform after the player plays this assistant card
     */
    public int getMotherNatureMoves() {
        return motherNatureMoves;
    }

    /**
     * Compares the parameter o and the current instance of the class Assistant (this).
     * Two instances are equal if they have the same card value and mother nature moves as this
     * @param o object to compare to this
     * @return true, if the object and this instance are equal; false, if the object and this instance are different
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assistant assistant = (Assistant) o;
        return cardValue == assistant.cardValue && motherNatureMoves == assistant.motherNatureMoves;
    }

    /**
     * Returns the stored values, which represents the identifiers and the mother nature moves of all the possible assistants. The format is: "value 1,mother nature moves 1;value 2,..."
     * @return values and the mother nature moves of all the possible assistants, returned as a sigle string
     */
    public static String getAssistants() { return assistants; }
}
