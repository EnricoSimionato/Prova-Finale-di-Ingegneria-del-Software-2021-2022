package it.polimi.ingsw.controllerTest.message;

import it.polimi.ingsw.controller.message.AddStudentOnTableMessage;
import it.polimi.ingsw.controller.message.PlayerMessage;
import it.polimi.ingsw.model.Game;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class PlayerMessageTest {

    private PlayerMessage message;
    private Game game;

    @Before
    public void setUp(){
        message = new AddStudentOnTableMessage(0,0);
        List<String> nicknames = new ArrayList<>();
        nicknames.add("mike");
        nicknames.add("enri");
        game = new Game(2, nicknames);
    }

    @Test
    public void testPerformMove() {
        message.performMove(game);
    }

    @Test
    public void testGetPlayerId() {
        assertEquals(0,message.getPlayerId());
    }

    @Test
    public void testSetPlayerId() {
        message.setPlayerId(1);
        assertEquals(1,message.getPlayerId());
    }
}