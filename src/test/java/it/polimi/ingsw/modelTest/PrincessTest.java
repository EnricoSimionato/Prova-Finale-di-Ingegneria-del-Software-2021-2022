package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exception.EffectCannotBeActivatedException;
import it.polimi.ingsw.model.exception.EmptyBagException;
import it.polimi.ingsw.model.exception.EmptyTableException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PrincessTest {

    private Princess character = new Princess(11, 0, 4);
    private Round round;

    @Before
    public void setUp() throws Exception {
        List<String> nicknames = new ArrayList<>();
        nicknames.add("mike");
        nicknames.add("enri");
        round = new Round(new ExpertGame(2, nicknames));
    }

    @Test
    public void testDoYourJob() throws EmptyBagException, EmptyTableException, EffectCannotBeActivatedException {

        Student chosen;

        character.addStudents(round.getGame().getGameTable().getBag().drawStudents(4));
        character.activateEffect(0, round);
        chosen = character.getStudentsOnCard()[2];
        character.doYourJob(0, 2);
        assertEquals(chosen, round.getGame().getGameTable().getSchoolBoards()[0].removeStudentFromTable(chosen.getColor()));
    }

    @Test
    public void testActivateEffect() throws EffectCannotBeActivatedException {
        assertEquals(character.activateEffect(0, round), character);
        assertEquals(4, character.getRound().getRoundState());
    }
}