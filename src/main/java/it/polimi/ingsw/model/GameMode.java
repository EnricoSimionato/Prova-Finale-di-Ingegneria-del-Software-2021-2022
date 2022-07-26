package it.polimi.ingsw.model;

import java.io.Serializable;

public enum GameMode implements Serializable {
    NORMAL("Normal mode"), EXPERT("Expert mode");

    private String mode;

    /***
     * Creates a value of the enumeration
     * @param mode string linked to the value of the enumeration
     */
    private GameMode(String mode) {
        this.mode = mode;
    }

    /**
     * Returns the string linked to the value of the enumeration
     * @return the string linked to the value of the enumeration
     */
    public String getMode() {
        return mode;
    }
}
