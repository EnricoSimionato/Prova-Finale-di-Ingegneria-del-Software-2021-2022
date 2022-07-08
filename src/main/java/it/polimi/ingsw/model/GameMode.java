package it.polimi.ingsw.model;

import java.io.Serializable;

public enum GameMode implements Serializable {
    NORMAL("Normal mode"), EXPERT("Expert mode");

    private String mode;

    /***
     * sets the mode attribute
     * @param mode
     */
    private GameMode(String mode) {
        this.mode = mode;
    }

    /**
     * returns the mode attribute
     * @return
     */
    public String getMode() {
        return mode;
    }
}
