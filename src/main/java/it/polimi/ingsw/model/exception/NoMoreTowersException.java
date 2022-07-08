package it.polimi.ingsw.model.exception;

import it.polimi.ingsw.model.TowerColor;

public class NoMoreTowersException extends Exception {
    private TowerColor emptySchoolboardColor;

    public NoMoreTowersException(TowerColor emptySchoolboardColor) {
        super();
        this.emptySchoolboardColor = emptySchoolboardColor;
    }


    public TowerColor getEmptySchoolboardColor() {
        return emptySchoolboardColor;
    }
}
