package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exception.EffectCannotBeActivatedException;
import it.polimi.ingsw.model.exception.InvalidIndexException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class HealerTest {

    private Healer character = new Healer(2, 0);
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
    public void testDoYourJob() throws EffectCannotBeActivatedException {
        int oldState = 0;
        character.activateEffect(0, round);
        oldState = character.getOldState();
        character.doYourJob(0, 1);
        assertEquals(3, character.getNumberOfProibitionCard());
        assertEquals(oldState, character.getRoundState());
        assertEquals(character.getStateMessageCli(), character.getGame().getPlayer(0).getPlayerMessageCli());
    }

    @Test
    public void testSetAlreadyPlayedCharacter() {
        character.setAlreadyPlayedCharacter(false);
        assertFalse(character.getRound().getAlreadyPLayedCharacter());
    }

    @Test
    public void testActivateEffect() {
        int oldState = round.getRoundState();
        character.activateEffect(0, 5);
        assertEquals(oldState, character.getOldState());
    }

    @Test
    public void testTestActivateEffect() throws EffectCannotBeActivatedException {
        character.activateEffect(0, round);
        assertEquals("Select an island where you want to put the prohibition card", round.getGame().getPlayer(0).getPlayerMessageCli());
        assertEquals(4, character.getRoundState());
    }

    @Test
    public void testSwitchToPianificationPhase() throws InvalidIndexException {
        Game gameTest = new Game(2, Arrays.stream(new String[]{"player0","player1"}).collect(Collectors.toList()));
        gameTest.startRound();
        round.getGame().startRound();

        round.removeAssistant(0,7);
        round.removeAssistant(1,5);

        gameTest.getRound().removeAssistant(0,7);
        gameTest.getRound().removeAssistant(1,5);

        gameTest.getRound().setPianificationPhaseOrder();
        int[] expectedPlayerOrder = gameTest.getRound().getPlayerOrder();
        character.switchToPianificationPhase();

        assertEquals(expectedPlayerOrder.length, round.getPlayerOrder().length);

    }

    @Test
    public void testGetNumberOfProibitionCard() {
        int oldState = round.getRoundState();
        assertEquals(oldState, character.getOldState());
    }

    @Test
    public void testChangeMotherNaturePosition() throws EffectCannotBeActivatedException, InvalidIndexException {
        character.activateEffect(0, round);

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