package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exception.EffectCannotBeActivatedException;
import it.polimi.ingsw.model.exception.EmptyTableException;
import it.polimi.ingsw.model.exception.FullTableException;
import it.polimi.ingsw.model.exception.InvalidIndexException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MinstrelTest {

    private Minstrel character;
    private Round round;

    @Before
    public void setUp() throws Exception {
        character = new Minstrel(10, 1);
        List<String> nicknames = new ArrayList<>();
        nicknames.add("mike");
        nicknames.add("enri");
        round = new Round(new ExpertGame(2, nicknames));
    }

    @Test
    public void testDoYourJob() throws FullTableException, EmptyTableException, EffectCannotBeActivatedException, InvalidIndexException {

        List<Student> newStudentsOnEntrance = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            round.getGame().getGameTable().getSchoolBoards()[1].addStudentOnTable(new Student(PawnColor.associateIndexToPawnColor(i)));
            newStudentsOnEntrance.add(new Student(PawnColor.associateIndexToPawnColor(i)));
        }
        character.activateEffect(1, round);
        for (int i = 0; i < 3; i++) character.getGame().getGameTable().getSchoolBoards()[1].removeStudentFromEntrance(i);
        character.getGame().getGameTable().getSchoolBoards()[1].addStudentsOnEntrance(newStudentsOnEntrance);
        Student[] entrance = character.getGame().getGameTable().getSchoolBoards()[1].getStudentsFromEntrance();
        character.doYourJob(1, 3);
        character.doYourJob(1,0);
        character.doYourJob(1,1);
        character.doYourJob(1,2);
        character.doYourJob(1,0);
        character.doYourJob(1,1);
        character.doYourJob(1,2);

        assertEquals(entrance[0].getColor(), PawnColor.GREEN);
        assertEquals(entrance[1].getColor(), PawnColor.RED);
        assertEquals(entrance[2].getColor(), PawnColor.YELLOW);
        for (int i = 0; i < 3; i++) {
            assertEquals(character.getGame().getGameTable().getSchoolBoards()[1].getNumberOfStudentsOnTable(i), 1);
        }

    }

    @Test
    public void testActivateEffect() throws EffectCannotBeActivatedException, InvalidIndexException, FullTableException {

        for (int i = 0; i < 3; i++) {
            round.getGame().getGameTable().getSchoolBoards()[0].addStudentOnTable(new Student(PawnColor.associateIndexToPawnColor(i)));
        }
        character.activateEffect(0, round);
        assertEquals(character.activateEffect(0, round), character);
        assertEquals(4, character.getRound().getRoundState());
    }
}