package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.Jester;
import it.polimi.ingsw.model.ExpertGame;
import it.polimi.ingsw.model.Round;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.exception.EffectCannotBeActivatedException;
import it.polimi.ingsw.model.exception.EmptyBagException;
import it.polimi.ingsw.model.exception.InvalidIndexException;
import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JesterTest {

    private Jester character = new Jester(1, 0, 6);
    private Round round;

    @Before
    public void setUp() {
        List<String> nicknames = new ArrayList<>();
        nicknames.add("mike");
        nicknames.add("enri");
        round = new Round(new ExpertGame(2, nicknames));
    }

    @Test
    public void testDoYourJob() throws EmptyBagException, InvalidIndexException, EffectCannotBeActivatedException {
        character.addStudents(round.getGame().getGameTable().getBag().drawStudents(6));
        character.activateEffect(0, round);
        character.getRound().getGame().getGameTable().getSchoolBoards()[0].addStudentsOnEntrance(character.getRound().getGame().getGameTable().getBag().drawStudents(8));
        Student[] entrance = character.getRound().getGame().getGameTable().getSchoolBoards()[0].getStudentsFromEntrance();
        Student[] studentsOnCard = character.getStudentsOnCard();
        //number of student to change
        character.doYourJob(0, 3);
        assertEquals(5, character.getRoundState());
        //index student on card
        for (int i = 0; i < 3; i++) character.doYourJob(0, i);
        assertEquals(6, character.getRoundState());
        //index student on entrance
        for (int i = 0; i < 3; i++) character.doYourJob(0, i);

        for (int i = 0; i < 3; i++) assertEquals(character.getRound().getGame().getGameTable().getSchoolBoards()[0].getStudentsFromEntrance()[i].getColor(), studentsOnCard[i].getColor());
        for (int i = 0; i < 3; i++) assertEquals(entrance[i].getColor(), character.getStudentsOnCard()[i].getColor());

    }

    @Test
    public void testActivateEffect() throws EffectCannotBeActivatedException {
        assertEquals(character.activateEffect(0, round), character);
        assertEquals(4, character.getRound().getRoundState());
    }
}