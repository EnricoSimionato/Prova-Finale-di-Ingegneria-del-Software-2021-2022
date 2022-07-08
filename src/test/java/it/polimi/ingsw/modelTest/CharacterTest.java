package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Character;
import it.polimi.ingsw.model.exception.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class CharacterTest {

    private Character c;
    private Round round;
    private SchoolBoard[] schoolBoards = new SchoolBoard[] { new SchoolBoard(7, TowerColor.WHITE, 8), new SchoolBoard(7, TowerColor.BLACK, 8)};;

    @Before
    public void setUp() {
        List<String> nicknames = new ArrayList<>();
        nicknames.add("mike");
        nicknames.add("enri");
        c = new Character(1, 2, "Name");
        round = new Round(new ExpertGame(2, nicknames));

    }

    @Test
    public void testGetPlayerOrder() throws EffectCannotBeActivatedException {
        int[] playerOrder={0,1};
        c.activateEffect(1, round.getGame().startRound(playerOrder));
        for (int i=0; i<2; i++)
            assertEquals(i, c.getPlayerOrder()[i]);
    }

    @Test
    public void testGetMovesCounter() throws EffectCannotBeActivatedException {
        c.activateEffect(1, round);
        assertEquals(0, c.getMovesCounter()[0]);
    }

    @Test
    public void testSetMovesCounter() throws EffectCannotBeActivatedException {
        c.activateEffect(1, round);
        c.setMovesCounter(0,1);
        assertEquals(1, c.getMovesCounter()[0]);
    }

    @Test
    public void testSetIndexOfPlayerOnTurn() throws EffectCannotBeActivatedException {
        c.activateEffect(1, round);
        c.setIndexOfPlayerOnTurn(0);
        assertEquals(0, c.getIndexOfPlayerOnTurn());
    }

    @Test
    public void testGetIndexOfPlayerOnTurn() throws EffectCannotBeActivatedException {
        c.activateEffect(1, round);
        c.setIndexOfPlayerOnTurn(0);
        assertEquals(0, c.getIndexOfPlayerOnTurn());
    }

    @Test
    public void testSetAlreadyPlayedCharacter() throws EffectCannotBeActivatedException {
        c.activateEffect(1, round);
        c.setAlreadyPlayedCharacter(true);
        assertTrue(c.getAlreadyPLayedCharacter());
    }

    @Test
    public void testGetAlreadyPLayedCharachter() throws EffectCannotBeActivatedException {
        c.activateEffect(1, round);
        c.setAlreadyPlayedCharacter(true);
        assertTrue(c.getAlreadyPLayedCharacter());
    }

    @Test
    public void testCalculateFirstPlayer() throws EffectCannotBeActivatedException {
        c.activateEffect(1, round);
        assertNotNull(c.calculateFirstPlayer(2));
    }

    @Test
    public void testCheckNumberOfMoves() throws EffectCannotBeActivatedException {
        c.activateEffect(1, round);
        try{
            c.checkNumberOfMoves(0);
        }catch (TooManyMovesException e){
            assertTrue(true);
        }
    }

    @Test
    public void checkSetMessageToAPlayerAndWaitingMessageForOthers() throws EffectCannotBeActivatedException {
        c.activateEffect(1, round);
        c.setMessageToAPlayerAndWaitingMessageForOthers(0, "ciao", "ciao");
        assertEquals(round.getGame().getPlayer(0).getPlayerMessageCli(), "ciao");
    }

    @Test
    public void testIsPianificationPhaseEnded() throws EffectCannotBeActivatedException {
        c.activateEffect(1, round);
        round.getGame().startRound();
        assertFalse(c.isPianificationPhaseEnded());
        round.setIndexOfPlayerOnTurn(round.getGame().getNumberOfPlayers()-1);
        assertTrue(c.isPianificationPhaseEnded());
    }

    @Test
    public void testIsActionPhaseEnded() throws EffectCannotBeActivatedException {
        c.activateEffect(1, round);

        round.getGame().startRound();
        assertFalse(c.isActionPhaseEnded());

        round.setRoundState(3);
        round.setIndexOfPlayerOnTurn(round.getGame().getNumberOfPlayers()-1);
        assertTrue(c.isActionPhaseEnded());
    }

    @Test
    public void testIsTimeToChooseTheNextStudent() throws EffectCannotBeActivatedException {
        c.activateEffect(1, round);
        round.setRoundState(1);
        assertTrue(c.isTimeToChooseTheNextStudent());
    }

    @Test
    public void isTimeToChooseACloud() throws EffectCannotBeActivatedException {
        c.activateEffect(1, round);

        round.getGame().startRound();
        assertFalse(c.isTimeToChooseACloud());

        round.setRoundState(2);
        assertTrue(c.isTimeToChooseACloud());
    }

    @Test
    public void testCloudHasBeenChosen() throws EffectCannotBeActivatedException {
        c.activateEffect(1, round);
        round.setRoundState(3);
        assertTrue(c.cloudHasBeenChosen());
    }

    @Test
    public void testPlayAssistant() throws InvalidIndexException {
        int[] playerOrder={0,1};
        round.getGame().startRound(playerOrder);
        int playerId=0;
        int idAssistant=1;
        Assistant played = round.getGame().getPlayer(playerId).getAssistant(idAssistant);
        c.setRound(round);
        playerId = round.getPlayerOnTurn();
        c.playAssistant(playerId, idAssistant);
        assertEquals(played, round.getPlayedAssistants()[playerId].getAssistant());

        playerId = round.getPlayerOnTurn();
        c.playAssistant(playerId, idAssistant);
        assertEquals(played, round.getPlayedAssistants()[(playerId+1)%2].getAssistant());

    }

    @Test
    public void testIsTimeToMoveMotherNature(){
        int[] playerOrder={0,1};
        round.getGame().startRound(playerOrder);
        c.setRound(round);
        assertFalse(c.isTimeToMoveMotherNature());

        round.setRoundState(1);
        round.setMovesCounter(round.getPlayerOrder()[round.getIndexOfPlayerOnTurn()],3);
        assertTrue(round.isTimeToMoveMotherNature());
    }

    @Test
    public void testIsTheGameEnded() throws EffectCannotBeActivatedException {
        c.activateEffect(1, round);
        round.setRoundState(100);
        assertTrue(c.isTheGameEnded());
    }

    @Test
    public void testSetPianificationPhaseOrder() throws EffectCannotBeActivatedException, InvalidIndexException {
        c.activateEffect(1, round);
        round.getGame().startRound();
        assertNotNull(round.getPlayedAssistants());

        round.removeAssistant(0,7);
        round.removeAssistant(1,5);

        c.setPianificationPhaseOrder();

        assertEquals(1, c.getPlayerOrder()[0]);
        assertEquals(0, c.getPlayerOrder()[1]);
    }

    @Test
    public void setActionPhaseOrder() throws EffectCannotBeActivatedException, InvalidIndexException {
        c.activateEffect(1, round);
        round.getGame().startRound();
        assertNotNull(round.getPlayedAssistants());

        round.removeAssistant(0,7);
        round.removeAssistant(1,5);

        c.setActionPhaseOrder();

        assertEquals(1, c.getPlayerOrder()[0]);
        assertEquals(0, c.getPlayerOrder()[1]);
    }

    @Test
    public void testSwitchToPianificationPhase() throws InvalidIndexException, EffectCannotBeActivatedException {
        c.activateEffect(1, round);

        Game gameTest = new Game(2, Arrays.stream(new String[]{"player0","player1"}).collect(Collectors.toList()));
        gameTest.startRound();
        round.getGame().startRound();

        round.removeAssistant(0,7);
        round.removeAssistant(1,5);

        gameTest.getRound().removeAssistant(0,7);
        gameTest.getRound().removeAssistant(1,5);

        gameTest.getRound().setPianificationPhaseOrder();
        int[] expectedPlayerOrder = gameTest.getRound().getPlayerOrder();
        c.switchToPianificationPhase();

        assertEquals(expectedPlayerOrder.length, c.getPlayerOrder().length);

        for(int i=0; i<round.getPlayerOrder().length; i++)
            assertEquals(expectedPlayerOrder[i], round.getPlayerOrder()[i]);
    }

    @Test
    public void testSwitchToActionPhase() throws InvalidIndexException, EffectCannotBeActivatedException {
        c.activateEffect(1, round);

        Game gameTest = new Game(2, Arrays.stream(new String[]{"player0","player1"}).collect(Collectors.toList()));
        gameTest.startRound();
        round.getGame().startRound();

        round.removeAssistant(0,7);
        round.removeAssistant(1,5);

        gameTest.getRound().removeAssistant(0,7);
        gameTest.getRound().removeAssistant(1,5);

        gameTest.getRound().setActionPhaseOrder();
        int[] expectedPlayerOrder = gameTest.getRound().getPlayerOrder();
        c.switchToActionPhase();

        assertEquals(expectedPlayerOrder.length, round.getPlayerOrder().length);

        for(int i=0; i<round.getPlayerOrder().length; i++)
            assertEquals(expectedPlayerOrder[i], c.getPlayerOrder()[i]);
    }

    @Test
    public void testAssistantNoChoice() throws InvalidIndexException, EffectCannotBeActivatedException {
        c.activateEffect(1, round);

        Player p0 = round.getGame().getPlayer(0);
        Player p1 = round.getGame().getPlayer(1);

        assertNotNull(p0.getAssistants());
        assertNotNull(p1.getAssistants());

        round.getGame().startRound();

        assertTrue(c.assistantNoChoice(p1.getAssistants(),p0.getAssistants()));

        p0.playAssistant(1);
        p1.playAssistant(2);

        assertFalse(c.assistantNoChoice(p1.getAssistants(),p0.getAssistants()));
    }

    @Test
    public void removeAssistant() throws EffectCannotBeActivatedException, InvalidIndexException {
        c.activateEffect(1, round);

        c.removeAssistant(0, 1);
        assertEquals(round.getPlayedAssistants()[0].getAssistant(), new Assistant(1, 1));
        assertEquals("Assistant played", c.getGame().getPlayer(0).getPlayerMessageCli());
    }

    @Test
    public void testAddStudentOnIsland() throws EffectCannotBeActivatedException, InvalidIndexException {
        int[] playerOrder={0,1};
        int playerId=0;
        int studentIndex=0;
        int islandIndex = round.getGame().getGameTable().getMotherNaturePosition()+1;

        round.getGame().getPlayer(playerId).addGameTable(round.getGame().getGameTable());
        round.getGame().getPlayer(playerId).addSchoolBoard(schoolBoards[playerId]);
        round.getGame().getPlayer(playerId+1).addGameTable(round.getGame().getGameTable());
        round.getGame().getPlayer(playerId+1).addSchoolBoard(schoolBoards[playerId+1]);
        round.getGame().startRound(playerOrder);
        round.getGame().getRound().setRoundState(1);
        round.getGame().getRound().setMovesCounter(playerId, 0);
        c.activateEffect(0, round);
        c.addStudentOnIsland(playerId, studentIndex, islandIndex);
        assertNotNull( round.getGame().getGameTable().getIslands().get(islandIndex).getStudents().get((studentIndex)%round.getGame().getGameTable().getIslands().get(islandIndex).getStudents().size()%12));
    }

    @Test
    public void testGetGame(){
        c.setRound(round);
        assertEquals(round.getGame(), c.getGame());
    }

    @Test
    public void testGetRoundState(){
        round.setRoundState(2);
        c.setRound(round);
        assertEquals(2, c.getRoundState());
    }

    @Test
    public void testCheckEndGameAndSetWinner() throws EffectCannotBeActivatedException, InvalidIndexException, NoMoreTowersException {
        c.activateEffect(1, round);
        round.getGame().getGameTable().getSchoolBoards()[0].removeTowers(7);
        c.checkEndgameAndSetTheWinner();
        assertEquals(TowerColor.WHITE, round.getGame().getWinner());
        round.getGame().startRound();
        List<Tower> tta = new ArrayList<>();
        for (int i=0; i<7; i++)
            tta.add(new Tower(TowerColor.WHITE));
        round.getGame().getGameTable().getSchoolBoards()[0].addTowers(tta);
        round.getGame().getGameTable().getSchoolBoards()[0].removeTowers(5);
        round.getGame().getGameTable().getSchoolBoards()[1].removeTowers(5);
        c.checkEndgameAndSetTheWinner();
        assertTrue(round.getGame().isDraw());
    }

    @Test
    public void testChangeMotherNaturePosition() throws EffectCannotBeActivatedException, InvalidIndexException {
        int[] playerOrder={0,1};
        int playerId=0;
        int islandIndex=(round.getGame().getGameTable().getMotherNaturePosition())%round.getGame().getGameTable().getNumberOfIslands();;
        int expectedPosition=islandIndex;

        round.getGame().getPlayer(playerId).addGameTable(round.getGame().getGameTable());
        round.getGame().getPlayer(playerId+1).addGameTable(round.getGame().getGameTable());

        round.getGame().startRound(playerOrder);
        round.removeAssistant(playerId,7);
        round.removeAssistant(playerId+1,9);
        c.activateEffect(1, round);
        round.setRoundState(2);
        c.changeMotherNaturePosition(playerId, islandIndex);
        assertEquals((expectedPosition)%12, c.getGame().getGameTable().getMotherNaturePosition());
    }

    @Test
    public void testGetStudentsFromCloud() throws InvalidIndexException, FullTableException, EffectCannotBeActivatedException {

        int[] playerOrder = {0, 1};
        int playerId = 0;
        int cloudIndex = 1;
        int expectedNumberOfStudents;

        round.getGame().getPlayer(playerId + 1).addGameTable(round.getGame().getGameTable());
        round.getGame().getPlayer(playerId).addGameTable(round.getGame().getGameTable());
        round.getGame().getPlayer(playerId).addSchoolBoard(round.getGame().getGameTable().getSchoolBoards()[playerId]);
        round.getGame().getPlayer(playerId + 1).addSchoolBoard(round.getGame().getGameTable().getSchoolBoards()[playerId + 1]);

        expectedNumberOfStudents = round.getGame().getGameTable().getClouds()[cloudIndex].getNumberOfStudents();

        c.activateEffect(1, round);

        for (int i = 0; i < round.getGame().getGameTable().getSchoolBoards()[playerId].getNumberOfStudentsOnEntrance() - 1; i++) {
            round.getGame().getPlayer(playerId).moveStudentOnTable(i);
            round.getGame().getPlayer(playerId + 1).moveStudentOnTable(i);
        }

        for (int i = 0; i < round.getGame().getGameTable().getSchoolBoards()[playerId].getNumberOfStudentsOnEntrance() - 1; i++) {
            assertNull(round.getGame().getPlayer(playerId).getStudentsFormEntrance()[i]);
            assertNull(round.getGame().getPlayer(playerId + 1).getStudentsFormEntrance()[i]);
        }

        round.getGame().startRound(playerOrder);
        round.setRoundState(3);
        c.getStudentsFromCloud(playerId, cloudIndex);

        round.setRoundState(3);
        c.getStudentsFromCloud(playerId, cloudIndex);
        assertEquals("You are not the current player", round.getGame().getPlayer(playerId).getPlayerMessageCli());

        playerId = 1;
        round.setRoundState(2);
        c.getStudentsFromCloud(playerId, cloudIndex);
        assertEquals("You cannot get students from cloud now\n" +
                "Mother nature position: " + round.getGame().getGameTable().getMotherNaturePosition() + "\n" +
                "Select an island where mother nature has to move: ", round.getGame().getPlayer(playerId).getPlayerMessageCli());
    }

    @Test
    public void testGetOldState(){
        assertEquals(0, c.getOldState());
    }

    @Test
    public void testSetOldState(){
        c.setOldState(1);
        assertEquals(1, c.getOldState());
    }

    @Test
    public void testSetRound(){
        c.setRound(round);
        assertEquals(round, c.getRound());
    }

    @Test
    public void testTestGetID() {
        assertEquals(c.getID(), 1);
    }

    @Test
    public void testTestGetCost() {
        assertEquals(2, c.getCost());
    }

    @Test
    public void testTestGetFirstUse() {
        assertFalse(c.getFirstUse());
        c.setFirstUse();
        assertTrue(c.getFirstUse());
    }

    @Test
    public void testTestSetFirstUse() {
        c.setFirstUse();
        assertTrue(c.getFirstUse());
    }

    @Test
    public void testActivateEffect() throws EffectCannotBeActivatedException {
        assertEquals(c.activateEffect(0, round), c);
    }

    @Test
    public void testDeactivateEffect() throws EffectCannotBeActivatedException {
        int state = round.getRoundState();
        c.activateEffect(0, round);
        c.deactivateEffect(false);
        assertEquals(round.getGame().getRound().getRoundState(), state);
        assertEquals(round, round.getGame().getRound());
        state = round.getRoundState();
        c.activateEffect(0, round);
        c.deactivateEffect(true);
        assertEquals(round.getGame().getRound().getRoundState(), state);
        assertEquals(round, round.getGame().getRound());
    }

    @Test
    public void testDoYourJob() {
        try {
            c.doYourJob(0, 1);
        } catch (InvalidIndexException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetRound() throws EffectCannotBeActivatedException {
        assertNull(c.getRound());
        c.activateEffect(0, round);
        assertNotNull(c.getRound());
    }

    @Test
    public void testGetName(){
        assertEquals("Name", c.getName());
    }
}