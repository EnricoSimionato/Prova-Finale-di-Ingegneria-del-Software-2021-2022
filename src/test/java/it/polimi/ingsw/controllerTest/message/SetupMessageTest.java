package it.polimi.ingsw.controllerTest.message;

import it.polimi.ingsw.controller.message.ConnectionState;
import it.polimi.ingsw.controller.message.SetupMessage;
import it.polimi.ingsw.model.Game;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class SetupMessageTest {

    private SetupMessage message;
    private Game game;

    @Before
    public void setUp(){
        message = new SetupMessage(ConnectionState.LOGIN, "test");
    }

    @Test
    public void testGetConnectionState() {
        assertEquals(ConnectionState.LOGIN, message.getConnectionState());
    }

    @Test
    public void testGetMessage() {
        assertEquals("test", message.getMessage());
    }

    @Test
    public void testSetConnectionState() {
        message.setConnectionState(ConnectionState.NUMBEROFPLAYERS);
        assertEquals(ConnectionState.NUMBEROFPLAYERS, message.getConnectionState());
    }
}