package it.polimi.ingsw.model;

import java.io.Serializable;

public enum TowerColor implements Serializable {
    WHITE(0), BLACK(1), GREY(2);
    private final int index;

    /**
     * Creates an element of the enumeration
     * @param index unique integer value associated with each value of the enumeration. WHITE index is 0, BLACK index is 1, GRAY index is 2
     */
    TowerColor(int index) {
        this.index = index;
    }

    /**
     * Returns the index associated with the specific value of the enumeration
     * @return unique index associated with the specific value of the enumeration
     */
    public int getIndex() {
        return index;
    }
}
