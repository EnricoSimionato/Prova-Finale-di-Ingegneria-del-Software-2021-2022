package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exception.EffectCannotBeActivatedException;
import it.polimi.ingsw.model.exception.FullTableException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ThiefTest {

    private Thief character;
    private Round round;

    @Before
    public void setUp() throws Exception {
        character = new Thief(10, 0);
        List<String> nicknames = new ArrayList<>();
        nicknames.add("mike");
        nicknames.add("enri");
        round = new Round(new ExpertGame(2, nicknames));

    }

    @Test
    public void testDoYourJob() throws FullTableException, EffectCannotBeActivatedException {
        round.getGame().getGameTable().getSchoolBoards()[0].addStudentOnTable(new Student(PawnColor.YELLOW));
        round.getGame().getGameTable().getSchoolBoards()[0].addStudentOnTable(new Student(PawnColor.YELLOW));
        round.getGame().getGameTable().getSchoolBoards()[0].addStudentOnTable(new Student(PawnColor.YELLOW));
        round.getGame().getGameTable().getSchoolBoards()[0].addStudentOnTable(new Student(PawnColor.YELLOW));
        round.getGame().getGameTable().getSchoolBoards()[1].addStudentOnTable(new Student(PawnColor.YELLOW));
        round.getGame().getGameTable().getSchoolBoards()[1].addStudentOnTable(new Student(PawnColor.YELLOW));
        round = character.activateEffect(0, round);
        character.doYourJob(0, 0);
        assertEquals(4, round.getGame().getGameTable().getSchoolBoards()[0].getNumberOfStudentsOnTable(PawnColor.YELLOW));
        assertEquals(2, round.getGame().getGameTable().getSchoolBoards()[1].getNumberOfStudentsOnTable(PawnColor.YELLOW));
    }

    @Test
    public void testActivateEffect() throws EffectCannotBeActivatedException {
        assertEquals(character.activateEffect(0, round), character);
        assertEquals(4, character.getRound().getRoundState());
    }
}