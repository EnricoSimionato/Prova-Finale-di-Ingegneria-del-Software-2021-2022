package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exception.EffectCannotBeActivatedException;
import it.polimi.ingsw.model.exception.FullTableException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class InnKeeperTest {

    private InnKeeper character = new InnKeeper(2, 0);
    private Round round;

    @Before
    public void setUp() throws Exception {
        List<String> nicknames = new ArrayList<>();
        nicknames.add("mike");
        nicknames.add("enri");
        round = new Round(new ExpertGame(2, nicknames));
    }

    @Test
    public void testActivateEffect() throws FullTableException, EffectCannotBeActivatedException {
        try {
            character.activateEffect(0, round);
            assertEquals(0, character.getRoundState());
        } catch (EffectCannotBeActivatedException e) {
            System.out.println(e.getMessage());
        }

        int playerId = 0;

        round.getGame().getGameTable().getSchoolBoards()[0].addStudentOnTable(new Student(PawnColor.YELLOW));
        round.getGame().getGameTable().getSchoolBoards()[1].addStudentOnTable(new Student(PawnColor.YELLOW));
        round.getGame().getGameTable().getSchoolBoards()[1].setProfessor(PawnColor.YELLOW, true);
        character.activateEffect(playerId, round);
        assertFalse(round.getGame().getGameTable().getSchoolBoards()[1].getProfessors().contains(PawnColor.YELLOW));
        assertTrue(round.getGame().getGameTable().getSchoolBoards()[0].getProfessors().contains(PawnColor.YELLOW));
    }

}