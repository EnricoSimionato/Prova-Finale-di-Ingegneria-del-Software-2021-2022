package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exception.EffectCannotBeActivatedException;
import it.polimi.ingsw.model.exception.InvalidIndexException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class VillagerTest {

    private Villager character = new Villager(1, 0);
    private Round round;

    @Before
    public void setUp() throws Exception {
        List<String> nicknames = new ArrayList<>();
        nicknames.add("mike");
        nicknames.add("enri");
        round = new Round(new ExpertGame(2, nicknames));
        character.setRound(round);
    }

    @Test
    public void testDoYourJob() throws InvalidIndexException, EffectCannotBeActivatedException {

        round.getGame().getGameTable().addStudentOnIsland(new Student(PawnColor.YELLOW), 0);
        round.getGame().getGameTable().addStudentOnIsland(new Student(PawnColor.YELLOW), 0);
        round.getGame().getGameTable().addStudentOnIsland(new Student(PawnColor.BLUE), 0);
        round.getGame().getGameTable().getSchoolBoards()[0].setProfessor(PawnColor.YELLOW, true);
        round.getGame().getGameTable().getSchoolBoards()[1].setProfessor(PawnColor.BLUE, true);
        round.setRoundState(2);

        character.activateEffect(0, round);
        assertEquals(4, character.getRoundState());

        character.doYourJob(0, 2);
        assertEquals(null, character.getGame().getPlayer(0).getPlayerMessageCli());
        assertEquals(PawnColor.YELLOW, character.getStudentColor());
    }

    @Test
    public void testStudentColor() throws EffectCannotBeActivatedException {
        character.activateEffect(0, round);
        character.doYourJob(0, 2);
        assertEquals(PawnColor.YELLOW, character.getStudentColor());
    }

    @Test
    public void testActivateEffect() {
        try {
            character.activateEffect(0, round);
            assertEquals(4, character.getRoundState());
        } catch (EffectCannotBeActivatedException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testChangeMotherNaturePosition() throws InvalidIndexException, EffectCannotBeActivatedException {
        character.activateEffect(0, round);
        character.doYourJob(0, 2);
        int playerId=0;
        int islandIndex=(character.getGame().getGameTable().getMotherNaturePosition())%character.getGame().getGameTable().getNumberOfIslands();;
        int expectedPosition=islandIndex;

        character.getGame().getPlayer(playerId).addGameTable(character.getGame().getGameTable());
        character.getGame().getPlayer(playerId+1).addGameTable(character.getGame().getGameTable());

        character.removeAssistant(playerId,7);
        character.removeAssistant(playerId+1,9);

        character.setRoundState(2);
        character.changeMotherNaturePosition(playerId, islandIndex);
        assertEquals(expectedPosition, character.getGame().getGameTable().getMotherNaturePosition());
    }
}