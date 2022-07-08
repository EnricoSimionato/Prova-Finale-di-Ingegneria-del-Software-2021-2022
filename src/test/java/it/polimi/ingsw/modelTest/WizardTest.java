package it.polimi.ingsw.modelTest;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exception.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class WizardTest {

    @Test
    public void testGetIndex() {
        for (int i=0; i < Wizard.values().length; i++)
            assertEquals(i, Wizard.values()[i].getIndex());
    }

    @Test
    public void testAssociateIndexToWizard() throws InvalidIndexException {
        for (int i=0; i < Wizard.values().length; i++)
            assertEquals(Wizard.associateIndexToWizard(i), Wizard.values()[i]);
    }

    @Test
    public void testToString() {
        assertEquals("Green", Wizard.GREEN_WIZARD.toString());
        assertEquals("Yellow", Wizard.YELLOW_WIZARD.toString());
        assertEquals("Purple", Wizard.PURPLE_WIZARD.toString());
        assertEquals("Blue", Wizard.BLUE_WIZARD.toString());
    }

    @Test
    public void testGetWizardFromString() {
        for (int i=0; i < Wizard.values().length; i++)
            assertEquals(Wizard.getWizardFromString(Wizard.values()[i].toString()), Wizard.values()[i]);
    }
}