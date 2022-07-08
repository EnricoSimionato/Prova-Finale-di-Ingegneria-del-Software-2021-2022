package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.Objects;

public class Assistant implements Serializable {
    private int cardValue;
    private int motherNatureMoves;
    public static final int MAXVALUE = 10;

    /**
     * Creates an assistant card with the given two values
     * @param cardValue value of the assistant card
     * @param motherNatureMoves number of maximum moves that mother nature can perform when a player plays the assistant card
     */
    public Assistant(int cardValue, int motherNatureMoves) {
        if (cardValue >= 1 && cardValue <= 10) this.cardValue = cardValue;
        if (motherNatureMoves >= 1 && motherNatureMoves <= 5) this.motherNatureMoves = motherNatureMoves;
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
     * returns true if the Object passed by parameter is an Assistant which has the same card value and mother nature moves as this
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assistant assistant = (Assistant) o;
        return cardValue == assistant.cardValue && motherNatureMoves == assistant.motherNatureMoves;
    }

    /**
     * returns the result of the method hash of cardValue and motherNatureMoves
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(cardValue, motherNatureMoves);
    }
}
