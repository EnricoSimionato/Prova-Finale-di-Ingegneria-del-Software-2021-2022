package it.polimi.ingsw.model;

import java.io.Serializable;

public class Tower implements Serializable {
    private final TowerColor color;

    /**
     * Creates a tower of the color passed as parameter
     * @param color color of the new tower
     */
    public Tower(TowerColor color) {
        this.color = color;
    }

    /**
     * Returns the color of the tower
     * @return color of the tower
     */
    public TowerColor getColor() {
        return color;
    }

    /**
     * Compares the parameter o and the current instance of the class Tower (this).
     * Two instances are equal if they have the same color
     * @param o object to compare to this
     * @return true, if the object and this instance are equal; false, if the object and this instance are different
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tower tower = (Tower) o;
        return color == tower.color;
    }
}
