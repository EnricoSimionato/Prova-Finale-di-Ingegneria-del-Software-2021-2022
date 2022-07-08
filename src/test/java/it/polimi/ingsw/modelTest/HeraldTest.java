package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exception.EffectCannotBeActivatedException;
import it.polimi.ingsw.model.exception.InvalidIndexException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HeraldTest {

    private Herald character = new Herald(1, 0);
    private Round round;

    @Before
    public void setUp() {
        List<String> nicknames = new ArrayList<>();
        nicknames.add("mike");
        nicknames.add("enri");
        round = new Round(new ExpertGame(2, nicknames));
    }

    @Test
    public void testDoYourJob() throws InvalidIndexException, EffectCannotBeActivatedException {
        int oldMotherNaturePosition = round.getGame().getGameTable().getMotherNaturePosition();
        round.getGame().getGameTable().addStudentOnIsland(new Student(PawnColor.RED), (round.getGame().getGameTable().getMotherNaturePosition()+6)%12);
        round.getGame().getGameTable().getSchoolBoards()[0].setProfessor(PawnColor.RED, true);
        character.activateEffect(0, round);
        character.doYourJob(0, (character.getRound().getGame().getGameTable().getMotherNaturePosition()+6)%12);
        assertEquals(character.getRound().getGame().getGameTable().getIslands().get((character.getRound().getGame().getGameTable().getMotherNaturePosition()+6)%12).getTowers().get(0).getColor(), TowerColor.WHITE);
        assertEquals(round.getGame().getGameTable().getMotherNaturePosition(), oldMotherNaturePosition);
    }

    @Test
    public void testActivateEffect() throws EffectCannotBeActivatedException {
        assertEquals(character.activateEffect(0, round), character);
        assertEquals(4, character.getRound().getRoundState());
    }
}