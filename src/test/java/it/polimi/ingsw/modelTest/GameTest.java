package it.polimi.ingsw.modelTest;
import it.polimi.ingsw.model.*;
import java.util.*;
import java.util.stream.Collectors;

import it.polimi.ingsw.model.exception.EmptyBagException;
import it.polimi.ingsw.model.exception.EmptyCloudException;
import it.polimi.ingsw.model.exception.InvalidIndexException;
import it.polimi.ingsw.model.exception.NoMoreTowersException;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    private Bag bag = new Bag();
    private SchoolBoard[] schoolBoards = new SchoolBoard[] { new SchoolBoard(9, TowerColor.WHITE, 8), new SchoolBoard(9, TowerColor.BLACK, 8)};
    private GameTable gameTable = new GameTable(2, schoolBoards,  bag);
    private Player[] players = {new Player("player0", 0,  new ArrayList<Assistant>()), new Player("player1", 1,  new ArrayList<Assistant>())};
    private String[] nicknames = {players[0].getNickname(), players[1].getNickname()};
    private Game game2p = new Game(2, Arrays.stream(nicknames).collect(Collectors.toList()));

    @Test
    public void testGetGameMode() {
        assertEquals(GameMode.NORMAL,game2p.getGameMode());
    }

    @Test
    public void testCreateGameTable() {

        //SchoolBoard[] expectedSchoolBoards= new SchoolBoard[]{new SchoolBoard(7, TowerColor.WHITE, 8), new SchoolBoard(7, TowerColor.BLACK, 8)};
        Game g = new Game(2);

        assertNull(g.getGameTable());
        g.setGameTable(g.createGameTable(2));
        assertNotNull(g.getGameTable());

        assertEquals(7, g.getGameTable().getSchoolBoards()[0].getNumberOfStudentsOnEntrance());
        assertEquals(TowerColor.WHITE, g.getGameTable().getSchoolBoards()[0].getTowersColor());
        assertEquals(8, g.getGameTable().getSchoolBoards()[0].getTowers().size());
        assertEquals(7, g.getGameTable().getSchoolBoards()[1].getNumberOfStudentsOnEntrance());
        assertEquals(TowerColor.BLACK, g.getGameTable().getSchoolBoards()[1].getTowersColor());
        assertEquals(8, g.getGameTable().getSchoolBoards()[1].getTowers().size());

    }

    @Test
    public void testCreateBag() throws EmptyBagException {

        assertNotNull(game2p.createBag());

        try {
            assertEquals(130, game2p.createBag().drawStudents(130).size());
        }catch (EmptyBagException e){
            assertTrue(true);
        }
    }

    @Test
    public void testSetWinner(){
        game2p.setWinner(TowerColor.WHITE);
        assertEquals(TowerColor.WHITE, game2p.getWinner());
    }

    @Test
    public void testGetWinner(){
        game2p.setWinner(TowerColor.WHITE);
        assertEquals(TowerColor.WHITE, game2p.getWinner());
    }

    @Test
    public void testCreateAssistants() {

        List<Assistant> assistantsCheck = new ArrayList<>();
        assistantsCheck = game2p.createAssistants();

        assertNotNull(assistantsCheck);

        for(int i=0; i<assistantsCheck.size(); i++){

            assertEquals(i+1, assistantsCheck.get(i).getCardValue());

            if(i==0 || i==1)
                assertEquals(1, assistantsCheck.get(i).getMotherNatureMoves());

            if(i==2 || i==3)
                assertEquals(2, assistantsCheck.get(i).getMotherNatureMoves());

            if(i==4 || i==5)
                assertEquals(3, assistantsCheck.get(i).getMotherNatureMoves());

            if(i==6 || i==7)
                assertEquals(4, assistantsCheck.get(i).getMotherNatureMoves());

            if(i==8 || i==9)
                assertEquals(5, assistantsCheck.get(i).getMotherNatureMoves());

        }
    }

    @Test
    public void testGetNumberOfPlayers() {
        assertEquals(2, game2p.getNumberOfPlayers());
    }

    @Test
    public void testGetGameTable() {
        game2p.setGameTable(gameTable);
        assertEquals(gameTable, game2p.getGameTable());
    }

    @Test
    public void testGetPlayer() {
        for (int i=0; i<game2p.getNumberOfPlayers(); i++)
            assertNotNull(game2p.getPlayer(i));
    }

    @Test
    public void testSetGameTable() {
        game2p.setGameTable(gameTable);
        assertEquals(gameTable, game2p.getGameTable());
    }

    @Test
    public void testIsAValidPositionForMotherNature() {

        game2p.setGameTable(gameTable);

        assertTrue(game2p.getGameTable().getNumberOfIslands() > 0);
        assertTrue(game2p.getGameTable().getMotherNaturePosition() >= 0);

        int givenPosition = -1;
        assertFalse(game2p.isAValidPositionForMotherNature(givenPosition));

        givenPosition = game2p.getGameTable().getIslands().size();
        assertFalse(game2p.isAValidPositionForMotherNature(givenPosition));

        givenPosition = game2p.getGameTable().getIslands().size() - 1;
        assertTrue(game2p.isAValidPositionForMotherNature(givenPosition));
    }

    @Test
    public void testGetRound() {
        Round r;
        r = game2p.startRound();

        assertEquals(r, game2p.getRound());
    }

    @Test
    public void testStartRound() throws EmptyCloudException, InvalidIndexException {
        game2p.setGameTable(gameTable);

        for (int i=0; i<game2p.getGameTable().getNumberOfClouds(); i++)
            assertTrue(game2p.getGameTable().getClouds()[i].getStudents().isEmpty());

        game2p.startRound();

        for (int i=0; i<game2p.getGameTable().getNumberOfClouds(); i++) {
            //assertNotNull(game2p.getGameTable().getStudentsOnCloud(i));
            for(Student s : game2p.getGameTable().getStudentsOnCloud(i))
                assertNotNull(s);
        }
    }

    @Test
    public void testTestStartRound() throws EmptyCloudException, InvalidIndexException {
        int playerOrder[]={0,1};
        game2p.setGameTable(gameTable);

        for (int i=0; i<game2p.getGameTable().getNumberOfClouds(); i++)
            assertTrue(game2p.getGameTable().getClouds()[i].getStudents().isEmpty());

        game2p.startRound(playerOrder);

        for (int i=0; i<game2p.getGameTable().getNumberOfClouds(); i++) {
            //assertNotNull(game2p.getGameTable().getStudentsOnCloud(i));
            for(Student s : game2p.getGameTable().getStudentsOnCloud(i))
                assertNotNull(s);
        }
    }

    @Test
    public void testCheckEndgame() throws InvalidIndexException, NoMoreTowersException {

    }
    ////// aggiunto da enrico quando sposta robe e fa modifiche (arriva da gametabletest)
    @Test
    public void testSetVictory(){
        game2p.setVictory();
        assertTrue(game2p.isVictory());
    }
    ////// aggiunto da enrico quando sposta robe e fa modifiche (arriva da gametabletest)
    @Test
    public void testSetDraw(){
        game2p.setDraw();
        assertTrue(game2p.isDraw());
    }

    ////// aggiunto da enrico quando sposta robe e fa modifiche (arriva da gametabletest)
    @Test
    public void testGetIsVictory(){
        assertFalse(game2p.isVictory());
    }

    ////// aggiunto da enrico quando sposta robe e fa modifiche (arriva da gametabletest)
    @Test
    public void testGetIsDraw(){
        assertFalse(game2p.isDraw());
    }
}