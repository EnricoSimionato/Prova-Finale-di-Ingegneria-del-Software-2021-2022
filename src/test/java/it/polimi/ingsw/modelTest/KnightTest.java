package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.*;
import java.util.*;
import java.util.stream.Collectors;

import it.polimi.ingsw.model.exception.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KnightTest {

    private Knight character = new Knight(8, 0);
    private Round round;

    @Before
    public void setUp() throws Exception {
        List<String> nicknames = new ArrayList<>();
        nicknames.add("mike");
        nicknames.add("enri");
        round = new Round(new ExpertGame(2, nicknames));
    }

    @Test
    public void testActivateEffect() {
        try {
            character.activateEffect(0, round);
            assertEquals(0, character.getRoundState());
        } catch (EffectCannotBeActivatedException e) {
            System.out.println(e.getMessage());
        }
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