package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exception.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player player = new Player("Mike", 1, new ArrayList<>());
    private GameTable gameTable;

    @Test
    @Before
    public void testAddGameTable() {
        SchoolBoard[] schoolBoards= new SchoolBoard[2];
        schoolBoards[0] = new SchoolBoard(6, TowerColor.WHITE, 8);
        schoolBoards[1] = new SchoolBoard(6, TowerColor.BLACK, 8);
        this.gameTable = new GameTable(2, schoolBoards, new Bag());
        player.addGameTable(gameTable);
        player.addSchoolBoard(schoolBoards[0]);
    }

    @Test
    public void testIsError(){
        assertFalse(player.isError());
    }

    @Test
    public void testSetError(){
        player.setError(true);
        assertTrue(player.isError());
    }

    @Test
    public void testGetNickName(){
        assertEquals("Mike", player.getNickname());
    }

    @Test
    public void testSetWizard(){
        Wizard w = Wizard.BLUE_WIZARD;
        player.setWizard(w);

        assertEquals(Wizard.BLUE_WIZARD, player.getWizard());
    }

    @Test
    public void testGetWizard(){
        Wizard w = Wizard.BLUE_WIZARD;
        player.setWizard(w);

        assertEquals(Wizard.BLUE_WIZARD, player.getWizard());
    }

    @Test
    public void testAddSchoolBoard() {
        SchoolBoard s = new SchoolBoard(6, TowerColor.BLACK, 8);
        player.addSchoolBoard(s);
    }

    @Test
    public void testPlayAssistant() throws InvalidIndexException {
        List<Assistant> assistants = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            assistants.add(new Assistant(i, i%5));
        }
        player.addAssistants(assistants);
        for(Assistant s : assistants){
            assertEquals(s, player.playAssistant(0));
        }
    }

    @Test
    public void testGetAssistant() throws InvalidIndexException {
        List<Assistant> assistants = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            assistants.add(new Assistant(i, i%5));
        }
        player.addAssistants(assistants);
        int i = 0;
        for(Assistant s : assistants){
            assertEquals(s, player.getAssistant(i));
            assertNotNull(player.getAssistant(i));
            i++;
        }
    }

    @Test
    public void testgetAssistats(){
        List<Assistant> assistants = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            assistants.add(new Assistant(i, i%5));
        }
        player.addAssistants(assistants);
        assertEquals(10, player.getAssistants().size());
        for (Assistant a : player.getAssistants()) assertNotNull(a);
    }

    @Test
    public void testMoveStudentOnTable() throws FullTableException, InvalidIndexException {
        player.moveStudentOnTable(0);
    }

    @Test
    public void testMoveStudentOnIsland() throws InvalidIndexException {
        Student[] entrance = player.getStudentsFormEntrance();
        player.moveStudentOnIsland(0, gameTable.getMotherNaturePosition());
        List<Island> islands = gameTable.getIslands();
        assertEquals(islands.get(gameTable.getMotherNaturePosition()).getStudents().get(0), entrance[0]);
        assertEquals(1, islands.get(gameTable.getMotherNaturePosition()).getStudents().size());
    }

    @Test
    public void testGetPlayerMessage(){
        player.setPlayerMessageCli("Error!");
        assertEquals(player.getPlayerMessageCli(), "Error!");
    }

    @Test
    public void testSetPlayerMessage(){
        player.setPlayerMessageCli("Error!");
        assertEquals(player.getPlayerMessageCli(), "Error!");
    }

    @Test
    public void testTakeStudentsFromCloud() throws EmptyBagException, EmptyCloudException, FullTableException, InvalidIndexException {
        Student[] entrance;
        Student[] entrance2;

        gameTable.addStudentsOnClouds();
        for(int i = 0; i < 3; i++){
            player.moveStudentOnTable(i);
        }

        entrance = player.getStudentsFormEntrance();
        player.takeStudentsFromCloud(1);
        entrance2 = player.getStudentsFormEntrance();
        for(int i = 0; i < entrance.length; i++){
            if(i < 3) assertNotEquals(entrance[i], entrance2[i]);
            else assertEquals(entrance[i], entrance2[i]);
        }

    }

    @Test
    public void testAddAssistants() throws InvalidIndexException {
        List<Assistant> assistants = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            assistants.add(new Assistant(i, i%5));
        }
        player.addAssistants(assistants);
        int i = 0;
        for(Assistant s : assistants){
            assertEquals(s, player.getAssistant(i));
            assertNotNull(player.getAssistant(i));
            i++;
        }
    }

    @Test
    public void testGetStudentsFromEntrance(){
        assertEquals(6, player.getStudentsFormEntrance().length);
        for (Student s : player.getStudentsFormEntrance()) assertNotNull(s);
    }
}