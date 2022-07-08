package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exception.EffectCannotBeActivatedException;
import it.polimi.ingsw.model.exception.FullTableException;
import it.polimi.ingsw.model.exception.InvalidIndexException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CentaurTest {

    private Centaur character = new Centaur(6, 3);
    private ExpertGame game;
    Round round;

    @Before
    public void setUp() {
        List<String> nicknames = new ArrayList<>();
        nicknames.add("mike");
        nicknames.add("enri");
        game = new ExpertGame(2, nicknames);
        game.setASingleCharacter(character, 0);
        round = new Round(new ExpertGame(2, nicknames));
    }

    @Test
    public void testChangeMotherNaturePosition() throws FullTableException, EffectCannotBeActivatedException, InvalidIndexException {
        int[] playerOrder={0,1};
        int playerId=0;
        int islandIndex=(round.getGame().getGameTable().getMotherNaturePosition())%round.getGame().getGameTable().getNumberOfIslands();;
        int expectedPosition=islandIndex;

        round.getGame().getPlayer(playerId).addGameTable(round.getGame().getGameTable());
        round.getGame().getPlayer(playerId+1).addGameTable(round.getGame().getGameTable());

        round.getGame().startRound(playerOrder);
        round.removeAssistant(playerId,7);
        round.removeAssistant(playerId+1,9);

        round.setRoundState(2);
        character.setRound(round);
        character.changeMotherNaturePosition(playerId, islandIndex);
        assertEquals(expectedPosition, round.getGame().getGameTable().getMotherNaturePosition());
    }

}
