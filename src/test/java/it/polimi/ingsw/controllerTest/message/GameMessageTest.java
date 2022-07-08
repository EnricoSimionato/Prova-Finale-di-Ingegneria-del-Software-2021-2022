package it.polimi.ingsw.controllerTest.message;

import it.polimi.ingsw.controller.message.GameMessage;
import it.polimi.ingsw.model.*;
import junit.framework.TestCase;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class GameMessageTest extends TestCase {

    private GameMessage message;
    private ExpertGame game;

    @Before
    public void setUp(){
        List<String> nicknames = new ArrayList<>();
        nicknames.add("mike");
        nicknames.add("enri");
        game = new ExpertGame(2, nicknames);
        message = new GameMessage(game,0);
    }

    public void testGetGametable() {
        assertNotNull(message.getGametable());
    }

    public void testGetAssistants() {
        message.getAssistants();
    }

    public void testGetState() {
        message.getState();
    }

    public void testGetPlayerId() {
        assertEquals(0, message.getPlayerId());
    }

    public void testGetPlayerMessageGui() {
        message.getPlayerMessageGui();
    }

    public void testGetPlayerMessageCli() {
        message.getPlayerMessageCli();
    }

    public void testPrintDefaultOnCli() {
        message.printDefaultOnCli();
    }

    public void testGetListPlayedAssistants() {
        message.getListPlayedAssistants();
    }

    public void testGetPlayerOnTurn() {
        message.getPlayerOnTurn();
    }

    public void testGetGameMode() {
        assertEquals(GameMode.EXPERT, message.getGameMode());
    }

    public void testPrintIslands() {
        message.printIslands(message.getGametable().getIslands());
    }

    public void testPrintAssistants() {
        message.printAssistants(message.getAssistants());
    }

    public void testPrintCloud() {
        message.printCloud(message.getGametable().getClouds());
    }

    public void testGetClouds() {
        message.getClouds();
    }

    public void testPrintAllSchoolboards() {
        message.printAllSchoolboards();
    }

    public void testPrintSchoolboard() {
        message.printSchoolboard(0);
    }

    public void testPrintCharacter() {
        message.printCharacter(game.getCharacters());
    }

    public void testGetPlayedAssistants() {
        message.getPlayedAssistants();
    }

    public void testGetChatacters() {
        message.getCharacters();
    }

    public void testGetNumberOfPLayes() {
        message.getNumberOfPLayers();
    }

    public void testIsPostmanActive() {
        assertFalse(message.isPostmanActive());
    }

    public void testSetPlayerId() {
        message.setPlayerId(0);
    }

    public void testGetPlayesCoins() {
        assertEquals(message.getPlayesCoins(0),1);
    }

    public void testGetTableCoins() {
        assertEquals(18, message.getTableCoins());
    }

    public void testGetPlayersNicknames() {
        assertEquals("mike", message.getPlayersNicknames()[0]);
        assertEquals("enri", message.getPlayersNicknames()[1]);
    }
    public void testReturnCircleUnicodeForColor() {
        message.returnCircleUnicodeFromColor(TowerColor.WHITE);
    }

    public void testReturnCircleUnicodeFromColor() {
        message.returnCircleUnicodeForColor(PawnColor.YELLOW);
    }

    public void testGetWinnersIndexes() {
        message.getWinnersIndexes();
    }

    public void testGetWinnersNicknames() {
        message.getWinnersNicknames();
    }

    public void testPrintWinners() {
        message.printWinners();
    }

    public void testPrintTableCoins() {
        message.printTableCoins();
    }
}