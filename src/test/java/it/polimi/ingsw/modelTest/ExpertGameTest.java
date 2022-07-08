package it.polimi.ingsw.modelTest;
import it.polimi.ingsw.model.*;
import java.util.*;


import it.polimi.ingsw.model.Character;
import it.polimi.ingsw.model.exception.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;

public class ExpertGameTest {

    private ExpertGame exp_game2p;

    @Before
    public void setUp(){
        List<String> nicknames = new ArrayList<>();
        nicknames.add("mike");
        nicknames.add("enri");
        exp_game2p = new ExpertGame(2, nicknames);
    }

    @Test
    public void testGetCharacters(){
        Character[] cs=new Character[]{new Character(6,3, "Name")};
        exp_game2p.setCharacters(cs);
        for (int i = 0; i < cs.length; i++) assertEquals(cs[i], exp_game2p.getCharacters()[i]);
    }

    @Test
    public void testSetCharacters(){
        Character[] cs = new Character[]{new Character(6,3, "Name")};
        exp_game2p.setCharacters(cs);
        for (int i = 0; i < cs.length; i++) assertEquals(cs[i], exp_game2p.getCharacters()[i]);
    }

    @Test
    public void testCreateCharacters() throws EmptyBagException {
        exp_game2p.createCharacters();
        Character[] testCharacters = exp_game2p.getCharacters();
        assertEquals(3, testCharacters.length);
        for(Character c : testCharacters){
            assertNotNull(c);
            assertNotNull(c.getCost());
            assertNotNull(c.getID());
            if(c.getID()==1 || c.getID()==11 || c.getID()==7){
                assertThat(c, instanceOf(CharacterWithStudent.class));
            }
        }
    }

    @Test
    public void testGetIdCharacter() throws EmptyBagException, InvalidIndexException {

        int indexCard=1;
        assertNotEquals(0, exp_game2p.getIdCharacter(indexCard));

        indexCard=4;
        try{
            exp_game2p.getIdCharacter(indexCard);
        }catch (InvalidIndexException e){assertTrue(true);}
    }

    @Test
    public void testGetCostCharacter() throws InvalidIndexException {
        int indexCard=1;
        assertNotEquals(0, exp_game2p.getCostCharacter(indexCard));

        indexCard=4;
        try{
            exp_game2p.getCostCharacter(indexCard);
        }catch (InvalidIndexException e){assertTrue(true);}
    }

    @Test
    public void testGetStudentFromCard() throws EmptyBagException, InvalidIndexException {

        int numberOfStudents = 0;
        int characterIndex = 0;
        boolean outOfBound=false;
        List<Integer> studentPositions = new ArrayList<>();
        List<Student> receivedStudents = new ArrayList<>();
        studentPositions.add(0);

        exp_game2p.createCharacters();

        List<CharacterWithStudent> testCharacters = new ArrayList<>();
        for (Character c : exp_game2p.getCharacters())
            if (c instanceof CharacterWithStudent)
                testCharacters.add((CharacterWithStudent) c);

        try {
            for (int i = 0; i < exp_game2p.getCharacters().length; i++)
                if (exp_game2p.getCharacters()[i].getID() == testCharacters.get(0).getID()) {
                    characterIndex = i;
                    break;
                }
        }catch (IndexOutOfBoundsException e){ outOfBound=true; }

        if(outOfBound==false) {
            assertEquals(exp_game2p.getCharacters()[characterIndex], testCharacters.get(0));
            numberOfStudents = testCharacters.get(0).getHowManyStudents();
            exp_game2p.getStudentFromCard(characterIndex, studentPositions);
            assertNotNull(receivedStudents);
            assertEquals(numberOfStudents, testCharacters.get(0).getHowManyStudents());
        }
        else{
            assertTrue(true);
        }
    }

    @Test
    public void testAddStudentsOnCard() throws InvalidIndexException {

        int cardIndex=0;
        List<Student> newStudents = new ArrayList<>();
        newStudents.add(new Student(PawnColor.YELLOW));
        newStudents.add(new Student(PawnColor.RED));
        newStudents.add(new Student(PawnColor.BLUE));
        newStudents.add(new Student(PawnColor.GREEN));
        List<Student> newStudentsCopy = new ArrayList<>();
        newStudentsCopy.addAll(newStudents);
        List<Integer> students = new ArrayList<>();
        students.add(1);

        CharacterWithStudent expected = new CharacterWithStudent(11, 2, 4, "Name");
        Character[] testCharacter={expected};
        exp_game2p.setCharacters(testCharacter);

        try{
            assertNull(expected.getStudents(students));
        }catch (InvalidIndexException e){assertTrue(true);}
        assertEquals(expected, exp_game2p.getCharacters()[cardIndex]);

        exp_game2p.addStudentsOnCard(cardIndex, newStudents);

        assertEquals(newStudentsCopy.get(students.get(0)), exp_game2p.getStudentFromCard(cardIndex, students).get(0));
    }

    @Test
    public void testGetCharacter() {
        for(int i=0; i<exp_game2p.getCharacters().length; i++) {
            assertEquals(exp_game2p.getCharacters()[i], exp_game2p.getCharacter(i));
        }
    }

    @Test
    public void testEffectActivation() {
    } //TODO

    @Test
    public void testGetCoinsOfTheTable() {
        assertTrue(exp_game2p.getCoinsOfTheTable()==20- exp_game2p.getNumberOfPlayers());
    }

    @Test
    public void testGetPlayersCoins() {
        for(int i=0; i< exp_game2p.getPlayersCoins().length; i++) {
            //assertEquals(1, exp_game2p.getPlayersCoins()[i]);
        }
    }

    @Test
    public void testAddCoinToAPlayer() throws NotEnoughCoins {
        int playerIndex=0;
        //assertEquals(1, exp_game2p.getPlayersCoins()[playerIndex]);
        exp_game2p.addCoinToAPlayer(playerIndex);
        //assertEquals(2, exp_game2p.getPlayersCoins()[playerIndex]);
    }

    @Test
    public void testRemoveCoinsFromAPlayer() throws NotEnoughCoins {
        int playerIndex=0;
        int numberOfCoins=1;
        int tmp=0;
        try{
            exp_game2p.removeCoinsFromAPlayer(playerIndex, numberOfCoins);
        }catch (NotEnoughCoins e){assertTrue(true);}
        exp_game2p.addCoinToAPlayer(playerIndex);
        exp_game2p.addCoinToAPlayer(playerIndex);
        tmp=exp_game2p.getPlayersCoins()[playerIndex];
        exp_game2p.removeCoinsFromAPlayer(playerIndex, numberOfCoins);
        assertTrue(exp_game2p.getPlayersCoins()[playerIndex]==tmp-numberOfCoins);
    }

    @Test
    public void testAddCoinsToTheTable(){
        int numberOfCoins=2;
        int tmp= exp_game2p.getCoinsOfTheTable();
        exp_game2p.addCoinsToTheTable(numberOfCoins);
        assertEquals(tmp+numberOfCoins, exp_game2p.getCoinsOfTheTable());
    }

    @Test
    public void testRemoveCoinsFromTheTable() throws NotEnoughCoins{
        try{
            exp_game2p.removeCoinFromTheTable();
        }catch (NotEnoughCoins e){assertTrue(true);}
        exp_game2p.addCoinsToTheTable(5);
        int tmp = exp_game2p.getCoinsOfTheTable();
        exp_game2p.removeCoinFromTheTable();
        assertEquals(tmp-1, exp_game2p.getCoinsOfTheTable());
    }


}