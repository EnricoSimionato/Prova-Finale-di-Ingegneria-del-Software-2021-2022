package it.polimi.ingsw.modelTest;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LastRound;
import it.polimi.ingsw.model.Round;
import org.junit.Test;
import org.junit.Before;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class LastRoundTest{

    String[] nicknames = {"mike", "enri"};
    int[] playerOrder={0,1};
    boolean studentsEnded = true;
    Round lastRound;
    @Before
    public void setUp(){
        lastRound = new LastRound(new Game(2, Arrays.stream(nicknames).collect(Collectors.toList())), playerOrder, studentsEnded);
    }

    @Test
    public void testLastRound(){
        LastRound lr = new LastRound();
        assertNotNull(lr);
    }

    @Test
    public void testIsActionPhaseEnded() {
        lastRound.setRoundState(2);
        lastRound.setIndexOfPlayerOnTurn(1);
        assertTrue(lastRound.isActionPhaseEnded());
    }

    @Test
    public void testCalculateNextPlayer() {
        lastRound.setRoundState(2);
        lastRound.setIndexOfPlayerOnTurn(1);
        lastRound.calculateNextPlayer();
        assertEquals(100, lastRound.getRoundState());

        lastRound.setRoundState(2);
        lastRound.setIndexOfPlayerOnTurn(0);
        lastRound.calculateNextPlayer();
        assertEquals(1, lastRound.getRoundState());
    }
}