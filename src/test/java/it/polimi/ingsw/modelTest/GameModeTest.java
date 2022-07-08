package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.GameMode;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class GameModeTest {

    @Test
    public void testGetMode() {
        GameMode mode = GameMode.NORMAL;
        assertTrue(mode.getMode().equals("Normal mode"));
        mode = GameMode.EXPERT;
        assertTrue(mode.getMode().equals("Expert mode"));
    }
}
