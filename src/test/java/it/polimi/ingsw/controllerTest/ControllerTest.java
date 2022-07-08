package it.polimi.ingsw.controllerTest;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.message.ActivateEffectMessage;
import it.polimi.ingsw.controller.message.Message;
import it.polimi.ingsw.controller.message.PlayerMessage;
import it.polimi.ingsw.model.ExpertGame;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Round;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ControllerTest {

    private Controller c;

    @Before
    public void setUp(){
        List<String> nicknames = new ArrayList<>();
        nicknames.add("mike");
        nicknames.add("enri");
        c = new Controller(new Game(2, nicknames));
    }

    @Test
    public void testUpdate() {
        PlayerMessage m = new ActivateEffectMessage(0,0);
        c.update(m);
    }

    @Test
    public void testPerformMove() {
        PlayerMessage m = new ActivateEffectMessage(0,0);
        c.performMove(m);
    }
}