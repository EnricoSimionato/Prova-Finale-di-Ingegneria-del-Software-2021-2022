package it.polimi.ingsw.modelTest;
import it.polimi.ingsw.model.*;
import java.util.*;
import java.util.stream.Collectors;

import it.polimi.ingsw.model.exception.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RoundTest {

    private Bag bag = new Bag();
    private SchoolBoard[] schoolBoards = new SchoolBoard[] { new SchoolBoard(7, TowerColor.WHITE, 8), new SchoolBoard(7, TowerColor.BLACK, 8)};
    private GameTable gameTable = new GameTable(2, schoolBoards,  bag);
    private Player[] players = {new Player("player0", 0,  new ArrayList<Assistant>()), new Player("player1", 1,  new ArrayList<Assistant>())};

    private Game game2p;

    //PianificationPhase Tests

    @Before
    public void setUp(){
        List<String> nicknames = new ArrayList<>();
        nicknames.add("mike");
        nicknames.add("enri");
        game2p = new Game(2, nicknames);
    }
/*
    @Test
    public void testGetPianificationPhase(){
        assertNotNull(game2p.startRound().getPianificationPhase());
    }
*/
   /* @Test
    public void testCalculateFirstPlayerPianificationPhase(){ //Ho messo PianificationPhase public da private, altrimenti non avrei potuto testarne i metodi
        game2p.startRound();
        int fp = game2p.getRound().calculateFirstPlayer(2);
        assertTrue(fp >= 0 && fp < game2p.getNumberOfPlayers());
    }*/

    @Test
    public void testAssistantNoChoice() throws InvalidIndexException {
        Player p0 = game2p.getPlayer(0);
        Player p1 = game2p.getPlayer(1);
        
        assertNotNull(p0.getAssistants());
        assertNotNull(p1.getAssistants());

        /*for(int i=0; i<p0.getAssistants().size(); i++) {
            System.out.println("p0["+i+"] -> cardValue= "+p0.getAssistants().get(i).getCardValue()+"; mnmoves= "+p0.getAssistants().get(i).getMotherNatureMoves());
            System.out.println("p1["+i+"] -> cardValue= "+p1.getAssistants().get(i).getCardValue()+"; mnmoves= "+p1.getAssistants().get(i).getMotherNatureMoves());
            System.out.println(" ");
        }*/

        game2p.startRound();

        assertTrue(game2p.getRound().assistantNoChoice(p1.getAssistants(),p0.getAssistants()));

        p0.playAssistant(1);
        p1.playAssistant(2);

        assertFalse(game2p.getRound().assistantNoChoice(p1.getAssistants(),p0.getAssistants()));
    }

    /*
    @Test
    public void testPlayAssistantPianificationPhase() throws InvalidIndexException, OutOfBoundException {

        game2p.startRound();
        assertTrue(game2p.getRound().getPlayedAssistants().length == 2);
        for(int i=0; i<game2p.getRound().getPlayedAssistants().length; i++)
            assertNull(game2p.getRound().getPlayedAssistants()[i]);

        game2p.getRound().getPianificationPhase().playAssistant(0, 0);
        game2p.getRound().getPianificationPhase().playAssistant(1, 1);
        for(int i=0; i<game2p.getRound().getPlayedAssistants().length; i++)
            assertNotNull(game2p.getRound().getPlayedAssistants()[i]);

        int[] playerOrder=game2p.getRound().getPlayerOrder();
        game2p.startRound(playerOrder);
        game2p.getRound().getPianificationPhase().playAssistant(0, 2);
        game2p.getRound().getPianificationPhase().playAssistant(1, 2);

        assertEquals("Assistant not playable", game2p.getPlayer(1).getPlayerMessage());

    }
*/
    //----------------------------------------------------------------
    //PlayedAssistants Tests

    /*@Test
    public void testGetPlayerIndex(){
        game2p.startRound();
        game2p.startRound().playAssistant(0, 1);
        assertTrue(game2p.getRound().getPlayedAssistants()[0].getPlayerIndex()>=0 && game2p.getRound().getPlayedAssistants()[0].getPlayerIndex()<game2p.getNumberOfPlayers());
    }*/

    /*@Test
    public void testGetAssistant(){
        game2p.startRound().playAssistant(0, 1);
        assertNotNull(game2p.getRound().getPlayedAssistants()[0].getAssistant());
    }*/

    //-----------------------------------------------------------------

    @Test
    public void testCheckPlayerOnTurn() throws PlayerNotOnTurnException {

        int[] playerOrder={0,1};
        game2p.startRound(playerOrder);
        int wrongPlayer = playerOrder[1];
        int rightPlayer = playerOrder[0];

        game2p.getRound().checkPlayerOnTurn(rightPlayer);
        assertEquals("Select an assistant", game2p.getPlayer(rightPlayer).getPlayerMessageCli());

        try {
            game2p.getRound().checkPlayerOnTurn(wrongPlayer);
        }catch (PlayerNotOnTurnException e){ assertTrue(true);}

        assertTrue(game2p.getPlayer(wrongPlayer).getPlayerMessageCli() == "You are not the current player");
    }

    /*@Test
    public void testSetRoundState(){
        game2p.startRound();
        game2p.getRound().setRoundState(1);
        assertEquals(1, game2p.getRound().getRoundState());
        game2p.getRound().setRoundState(4);
        assertEquals(-1, game2p.getRound().getRoundState());
    }*/

    @Test
    public void testGetRoundState(){
        game2p.startRound();
        assertEquals(0, game2p.getRound().getRoundState());
    }

    @Test
    public void testCheckStatusAndMethod() throws InvalidMethodException {
        game2p.startRound();
        game2p.getRound().setRoundState(1);
        try{
            game2p.getRound().checkStatusAndMethod(2);
        }catch (InvalidMethodException e){ assertTrue(true);}
    }

    @Test
    public void testSetMovesCounter(){
        int[] playerOrder={0,1};
        game2p.startRound(playerOrder);
        game2p.getRound().setMovesCounter(0, 1);
        assertEquals(1, game2p.getRound().getMovesCounter()[0]);
    }

    @Test
    public void testGetMovesCounter(){
        int[] playerOrder={0,1};
        game2p.startRound(playerOrder);
        assertEquals(0, game2p.getRound().getMovesCounter()[0]);
    }

    @Test
    public void testCheckNumberOfMoves() throws TooManyMovesException {

        int[] playerOrder={0,1};
        game2p.startRound(playerOrder);
        game2p.getRound().setMovesCounter(0,4);
        try{
            game2p.getRound().checkNumberOfMoves(0);
        }catch (TooManyMovesException e){
            assertTrue(true);
        }
    }

    @Test
    public void testSetErrorMessage() {
        game2p.startRound().setPlayerMessageCli(0, "Generic error");
        assertTrue(game2p.getPlayer(0).getPlayerMessageCli()=="Generic error");
    }

    @Test
    public void testGetIndexOfPlayerOnTurn(){
        game2p.startRound();
        game2p.getRound().setIndexOfPlayerOnTurn(0);
        assertEquals(0, game2p.getRound().getIndexOfPlayerOnTurn());

        game2p.getRound().setIndexOfPlayerOnTurn(-1);
        assertEquals(0, game2p.getRound().getIndexOfPlayerOnTurn());

        game2p.getRound().setIndexOfPlayerOnTurn(4);
        assertEquals(0, game2p.getRound().getIndexOfPlayerOnTurn());
    }

    @Test
    public void testIsPianificationPhaseEnded() {

        game2p.startRound();
        assertFalse(game2p.getRound().isPianificationPhaseEnded());

        game2p.getRound().setIndexOfPlayerOnTurn(game2p.getNumberOfPlayers()-1);
        assertTrue(game2p.getRound().isPianificationPhaseEnded());
    }

    @Test
    public void testIsActionPhaseEnded() {
        game2p.startRound();
        assertFalse(game2p.getRound().isActionPhaseEnded());

        game2p.getRound().setRoundState(3);
        game2p.getRound().setIndexOfPlayerOnTurn(game2p.getNumberOfPlayers()-1);
        assertTrue(game2p.getRound().isActionPhaseEnded());
    }

    @Test
    public void testIsTimeToMoveMotherNature() {
        int[] playerOrder={0,1};
        game2p.startRound(playerOrder);
        assertFalse(game2p.getRound().isTimeToMoveMotherNature());

        game2p.getRound().setRoundState(1);
        game2p.getRound().setMovesCounter(game2p.getRound().getPlayerOrder()[game2p.getRound().getIndexOfPlayerOnTurn()],3);
        assertTrue(game2p.getRound().isTimeToMoveMotherNature());
    }

    @Test
    public void testIsTimeToChooseACloud() {
        game2p.startRound();
        assertFalse(game2p.getRound().isTimeToChooseACloud());

        game2p.getRound().setRoundState(2);
        assertTrue(game2p.getRound().isTimeToChooseACloud());
    }

    @Test
    public void testIslandHasBeenChosen() {
        game2p.startRound();
        assertFalse(game2p.getRound().cloudHasBeenChosen());

        game2p.getRound().setRoundState(3);
        assertTrue(game2p.getRound().cloudHasBeenChosen());
    }

    @Test
    public void testSetPianificationPhaseOrder() throws InvalidIndexException {
        game2p.startRound();
        assertNotNull(game2p.getRound().getPlayedAssistants());

        game2p.getRound().removeAssistant(0,7);
        game2p.getRound().removeAssistant(1,5);

        game2p.getRound().setPianificationPhaseOrder();

        assertEquals(1, game2p.getRound().getPlayerOrder()[0]);
        assertEquals(0, game2p.getRound().getPlayerOrder()[1]);
    }

    @Test
    public void testSetActionPhaseOrder() throws InvalidIndexException {
        game2p.startRound();
        assertNotNull(game2p.getRound().getPlayedAssistants());

        game2p.getRound().removeAssistant(0,7);
        game2p.getRound().removeAssistant(1,5);

        game2p.getRound().setActionPhaseOrder();

        assertEquals(1, game2p.getRound().getPlayerOrder()[0]);
        assertEquals(0, game2p.getRound().getPlayerOrder()[1]);
    }


    @Test
    public void testSwitchToPianificationPhase() throws InvalidIndexException {

        Game gameTest = new Game(2, Arrays.stream(new String[]{"player0","player1"}).collect(Collectors.toList()));
        gameTest.startRound();
        game2p.startRound();

        game2p.getRound().removeAssistant(0,7);
        game2p.getRound().removeAssistant(1,5);

        gameTest.getRound().removeAssistant(0,7);
        gameTest.getRound().removeAssistant(1,5);

        gameTest.getRound().setPianificationPhaseOrder();
        int[] expectedPlayerOrder = gameTest.getRound().getPlayerOrder();
        game2p.getRound().switchToPianificationPhase();

        assertEquals(expectedPlayerOrder.length, game2p.getRound().getPlayerOrder().length);

        for(int i=0; i<game2p.getRound().getPlayerOrder().length; i++)
            assertEquals(expectedPlayerOrder[i], game2p.getRound().getPlayerOrder()[i]);
    }

    @Test
    public void testSwitchToActionPhase() throws InvalidIndexException {
        Game gameTest = new Game(2, Arrays.stream(new String[]{"player0","player1"}).collect(Collectors.toList()));
        gameTest.startRound();
        game2p.startRound();

        game2p.getRound().removeAssistant(0,7);
        game2p.getRound().removeAssistant(1,5);

        gameTest.getRound().removeAssistant(0,7);
        gameTest.getRound().removeAssistant(1,5);

        gameTest.getRound().setActionPhaseOrder();
        int[] expectedPlayerOrder = gameTest.getRound().getPlayerOrder();
        game2p.getRound().switchToActionPhase();

        assertEquals(expectedPlayerOrder.length, game2p.getRound().getPlayerOrder().length);

        for(int i=0; i<game2p.getRound().getPlayerOrder().length; i++)
            assertEquals(expectedPlayerOrder[i], game2p.getRound().getPlayerOrder()[i]);

        assertEquals(1, game2p.getRound().getRoundState());
        assertEquals(0, game2p.getRound().getIndexOfPlayerOnTurn());
    }

    @Test
    public void testCalculateNextPlayer() throws InvalidIndexException {
        game2p.startRound();

        game2p.getRound().calculateNextPlayer();
        game2p.getRound().setIndexOfPlayerOnTurn(game2p.getNumberOfPlayers()-1);
        game2p.getRound().removeAssistant(0,7);
        game2p.getRound().removeAssistant(1,5);

        game2p.getRound().setRoundState(3);
        game2p.getRound().calculateNextPlayer();

        game2p.getRound().setRoundState(1);
        game2p.getRound().setMovesCounter(game2p.getRound().getPlayerOrder()[game2p.getRound().getIndexOfPlayerOnTurn()], 3);
        game2p.getRound().calculateNextPlayer();
        assertEquals(2, game2p.getRound().getRoundState());

        game2p.getRound().calculateNextPlayer();
        assertEquals(3, game2p.getRound().getRoundState());

        game2p.getRound().calculateNextPlayer();
        assertEquals(1, game2p.getRound().getRoundState());

        assertNotEquals(2, game2p.getRound().getIndexOfPlayerOnTurn());
    }

    @Test
    public void testPlayAssistant() throws InvalidIndexException {
        int[] playerOrder = {0, 1};
        game2p.startRound(playerOrder);
        int playerId = 0;
        int idAssistant = 1;
        Assistant played = game2p.getPlayer(playerId).getAssistant(idAssistant);

        game2p.getRound().playAssistant(playerId, idAssistant);
        assertEquals(played, game2p.getRound().getPlayedAssistants()[playerId].getAssistant());

        game2p.getRound().playAssistant(playerId, idAssistant);
        assertEquals(played, game2p.getRound().getPlayedAssistants()[playerId].getAssistant());

        playerId = game2p.getRound().getPlayerOrder()[1];
        game2p.getRound().setRoundState(1);
        game2p.getRound().playAssistant(playerId, idAssistant);
        assertEquals("You cannot play any assistant now\n" +
                "Make your move:\n" +
                "1 : Move a student from entrance to table\n" +
                "2 : Move a student from entrance to an island", game2p.getPlayer(playerId).getPlayerMessageCli());

    }

    @Test
    public void testGetRound(){
        assertEquals(game2p.getRound(), game2p.getRound().getRound());
    }

    @Test
    public void testAddStudentOnIsland() {
        int[] playerOrder={0,1};
        int playerId=0;
        int studentIndex=0;
        int islandIndex = game2p.getGameTable().getMotherNaturePosition()+1;

        game2p.getPlayer(playerId).addGameTable(game2p.getGameTable());
        game2p.getPlayer(playerId).addSchoolBoard(schoolBoards[playerId]);
        game2p.getPlayer(playerId+1).addGameTable(game2p.getGameTable());
        game2p.getPlayer(playerId+1).addSchoolBoard(schoolBoards[playerId+1]);

        Student played=game2p.getPlayer(playerId).getStudentsFormEntrance()[studentIndex];

        game2p.startRound(playerOrder);
        game2p.getRound().setRoundState(1);
        game2p.getRound().setMovesCounter(playerId, 0);
        game2p.getRound().addStudentOnIsland(playerId, studentIndex, islandIndex);

        //assertEquals(played, game2p.getGameTable().getIslands().get(islandIndex).getStudents().get((studentIndex+1)%game2p.getGameTable().getIslands().get(islandIndex).getStudents().size()));

        game2p.getRound().addStudentOnIsland(playerId, studentIndex, islandIndex);
        //assertEquals(played, game2p.getGameTable().getIslands().get(islandIndex).getStudents().get((studentIndex+1)%game2p.getGameTable().getIslands().get(islandIndex).getStudents().size()));

        playerId=1;
        game2p.getRound().setRoundState(2);
        game2p.getRound().addStudentOnIsland(playerId, studentIndex, islandIndex);
       // assertEquals("You cannot move students now", game2p.getPlayer(playerId).getPlayerMessage());

        game2p.getRound().setRoundState(1);
        game2p.getRound().setMovesCounter(playerId, 4);
        game2p.getRound().addStudentOnIsland(playerId, studentIndex, islandIndex);
       // assertEquals("You can move no more students", game2p.getPlayer(playerId).getPlayerMessage());

    }

    @Test
    public void testAddStudentOnTable() throws InvalidIndexException {
        int[] playerOrder={0,1};
        int playerId=0;
        int studentIndex=6;

        game2p.getPlayer(playerId).addGameTable(game2p.getGameTable());
        game2p.getPlayer(playerId).addSchoolBoard(game2p.getGameTable().getSchoolBoards()[playerId]);
        game2p.getPlayer(playerId+1).addGameTable(game2p.getGameTable());
        game2p.getPlayer(playerId+1).addSchoolBoard(game2p.getGameTable().getSchoolBoards()[playerId+1]);
        game2p.getRound().removeAssistant(0,7);
        game2p.getRound().removeAssistant(1,5);
        //game2p.getRound().removeAssistant(0,6);
        //game2p.getRound().removeAssistant(1,4);

        game2p.startRound(playerOrder);
        game2p.getRound().setRoundState(1);
        assertEquals(0, game2p.getGameTable().getSchoolBoards()[playerId].getNumberOfStudentsOnTable(PawnColor.YELLOW));
        assertEquals(0, game2p.getGameTable().getSchoolBoards()[playerId].getNumberOfStudentsOnTable(PawnColor.BLUE));
        assertEquals(0, game2p.getGameTable().getSchoolBoards()[playerId].getNumberOfStudentsOnTable(PawnColor.GREEN));
        assertEquals(0, game2p.getGameTable().getSchoolBoards()[playerId].getNumberOfStudentsOnTable(PawnColor.RED));
        assertEquals(0, game2p.getGameTable().getSchoolBoards()[playerId].getNumberOfStudentsOnTable(PawnColor.GREEN));
        assertNotNull(game2p.getPlayer(playerId).getStudentsFormEntrance()[studentIndex]);
        PawnColor expectedColor=game2p.getGameTable().getSchoolBoards()[playerId].getStudentsFromEntrance()[studentIndex].getColor();

        game2p.getRound().addStudentOnTable(playerId, studentIndex);

        assertEquals(1, game2p.getGameTable().getSchoolBoards()[playerId].getNumberOfStudentsOnTable(expectedColor));
/*
        playerId=1;
        game2p.getRound().setRoundState(2);
        game2p.getRound().addStudentOnTable(playerId, studentIndex);
        assertEquals("You are not the current player", game2p.getPlayer(playerId).getPlayerMessage());

        game2p.getRound().setRoundState(1);
        game2p.getRound().setMovesCounter(playerId, 4);
        game2p.getRound().addStudentOnTable(playerId, studentIndex);
        assertEquals("You can move no more students", game2p.getPlayer(playerId).getPlayerMessage());
*/
    }

    @Test
    public void testIsANewAllowedPositionForMotherNature() {
        game2p.startRound();
        Assistant a = new Assistant(2,2);
        int islandIndex=(game2p.getGameTable().getMotherNaturePosition()+1)%game2p.getGameTable().getNumberOfIslands();

        assertTrue(game2p.getRound().isANewAllowedPositionForMotherNature(a, islandIndex));

        islandIndex=(game2p.getGameTable().getMotherNaturePosition()+3)%game2p.getGameTable().getNumberOfIslands();
        assertFalse(game2p.getRound().isANewAllowedPositionForMotherNature(a, islandIndex));
    }

    @Test
    public void testChangeMotherNaturePosition() throws InvalidIndexException {
        int[] playerOrder={0,1};
        int playerId=0;
        int islandIndex=(game2p.getGameTable().getMotherNaturePosition()+1)%game2p.getGameTable().getNumberOfIslands();;
        int expectedPosition=islandIndex;

        game2p.getPlayer(playerId).addGameTable(game2p.getGameTable());
        game2p.getPlayer(playerId+1).addGameTable(game2p.getGameTable());

        game2p.startRound(playerOrder);
        game2p.getRound().removeAssistant(playerId,7);
        game2p.getRound().removeAssistant(playerId+1,9);

        game2p.getRound().setRoundState(2);
        game2p.getRound().changeMotherNaturePosition(playerId, islandIndex);
        assertEquals(expectedPosition, game2p.getGameTable().getMotherNaturePosition());
    }

    @Test
    public void testGetStudentsFromCloud() throws InvalidIndexException, FullTableException {
        int[] playerOrder={0,1};
        int playerId=0;
        int cloudIndex=1;
        int expectedNumberOfStudents;

        game2p.getPlayer(playerId).addGameTable(game2p.getGameTable());
        game2p.getPlayer(playerId+1).addGameTable(game2p.getGameTable());
        game2p.getPlayer(playerId).addSchoolBoard(game2p.getGameTable().getSchoolBoards()[playerId]);
        game2p.getPlayer(playerId+1).addSchoolBoard(game2p.getGameTable().getSchoolBoards()[playerId+1]);

        expectedNumberOfStudents=game2p.getGameTable().getClouds()[cloudIndex].getNumberOfStudents();

        for(int i=0; i<game2p.getGameTable().getSchoolBoards()[playerId].getNumberOfStudentsOnEntrance()-1; i++){
            game2p.getPlayer(playerId).moveStudentOnTable(i);
            game2p.getPlayer(playerId+1).moveStudentOnTable(i);
        }

        for(int i=0; i<game2p.getGameTable().getSchoolBoards()[playerId].getNumberOfStudentsOnEntrance()-1; i++){
            assertNull(game2p.getPlayer(playerId).getStudentsFormEntrance()[i]);
            assertNull(game2p.getPlayer(playerId+1).getStudentsFormEntrance()[i]);
        }

        game2p.startRound(playerOrder);
        game2p.getRound().setRoundState(3);
        game2p.getRound().getStudentsFromCloud(playerId, cloudIndex);

        for(int i=0; i<expectedNumberOfStudents; i++){
            assertNotNull(game2p.getPlayer(playerId).getStudentsFormEntrance()[i]);
        }

        game2p.getRound().setRoundState(3);
        game2p.getRound().getStudentsFromCloud(playerId, cloudIndex);
        assertEquals("You are not the current player", game2p.getPlayer(playerId).getPlayerMessageCli());

        playerId=1;
        game2p.getRound().setRoundState(2);
        game2p.getRound().getStudentsFromCloud(playerId, cloudIndex);
        assertEquals("You cannot get students from cloud now\n" +
                "Mother nature position: " + game2p.getGameTable().getMotherNaturePosition() + "\n" +
                "Select an island where mother nature has to move: ", game2p.getPlayer(playerId).getPlayerMessageCli());
    }

    @Test
    public void testGetPreviousState(){
        int previousState = game2p.getRound().getRoundState();
        game2p.getRound().setRoundState(1);
        assertEquals(previousState, game2p.getRound().getPreviousState());
    }

    @Test
    public void testCheckEndGameAndSetWinner() throws InvalidIndexException, NoMoreTowersException {

        game2p.getGameTable().getSchoolBoards()[0].removeTowers(7);
        game2p.getRound().checkEndgameAndSetTheWinner();
        assertEquals(TowerColor.WHITE, game2p.getRound().getGame().getWinner());
        game2p.startRound();
        List<Tower> tta = new ArrayList<>();
        for (int i=0; i<7; i++)
            tta.add(new Tower(TowerColor.WHITE));
        game2p.getGameTable().getSchoolBoards()[0].addTowers(tta);
        game2p.getGameTable().getSchoolBoards()[0].removeTowers(5);
        game2p.getGameTable().getSchoolBoards()[1].removeTowers(5);
        game2p.getRound().checkEndgameAndSetTheWinner();
        assertTrue(game2p.isDraw());
    }

    @Test
    public void testDoYourJob() throws InvalidIndexException {
        game2p.startRound();
        game2p.getRound().doYourJob(0, 0);
    }

}