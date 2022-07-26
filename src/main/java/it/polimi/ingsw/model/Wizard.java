package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.InvalidIndexException;
import it.polimi.ingsw.model.exception.InvalidStringException;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public enum Wizard implements Serializable {
    GREEN_WIZARD(0, "Green"), YELLOW_WIZARD(1, "Yellow"), PURPLE_WIZARD(2, "Purple"), BLUE_WIZARD(3, "Blue");
    private final int index;
    private final String color;

    /**
     * Creates a value of the enumeration
     * @param index index associated to the wizard
     * @param color color of the wizard
     */
    Wizard(int index, String color) {
        this.index = index;
        this.color = color;
    }

    /**
     * Returns the index of the wizard in the enumeration
     * @return the index of the wizard in the enumeration
     */
    public int getIndex() { return index; }

    /**
     * Returns the wizard which has the index equals to the received one
     * @param index index of the wizard which is looked for
     * @throws InvalidIndexException if there is no wizard which as the required index associated
     */
    public static Wizard associateIndexToWizard(int index) throws InvalidIndexException {
        Wizard wizard = null;

        if (index < 0 || index > 4)
            throw new InvalidIndexException("There is no such wizard");

        switch (index) {
            case 0:
                wizard = Wizard.GREEN_WIZARD;
                break;
            case 1:
                wizard = Wizard.YELLOW_WIZARD;
                break;
            case 2:
                wizard = Wizard.PURPLE_WIZARD;
                break;
            case 3:
                wizard = Wizard.BLUE_WIZARD;
                break;
        }
        return wizard;
    }

    /**
     * Returns a lexicographical way to represent the wizard
     * @return color of the wizard
     */
    @Override
    public String toString() {
        return color;
    }

    /**
     * Returns the wizard which has the color equals to the received one (colors : "Green", "Yellow", "Purple", "Blue")
     * @param wizard color of the wizard which is looked for
     * @throws InvalidIndexException if there is no wizard which as the required index associated
     */
    public static Wizard getWizardFromString(String wizard) throws InvalidStringException {
        if (!"Green".equals(wizard) && !"Yellow".equals(wizard) && !"Purple".equals(wizard) && !"Blue".equals(wizard)) throw new InvalidStringException("There is no such wizard");

        Wizard returnedWizard = null;
        switch (wizard) {
            case "Green":
                returnedWizard = Wizard.GREEN_WIZARD;
                break;
            case "Yellow":
                returnedWizard = Wizard.YELLOW_WIZARD;
                break;
            case "Purple":
                returnedWizard = Wizard.PURPLE_WIZARD;
                break;
            case "Blue":
                returnedWizard = Wizard.BLUE_WIZARD;
                break;
        }
        return returnedWizard;
    }
}
