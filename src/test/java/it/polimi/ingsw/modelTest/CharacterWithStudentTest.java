package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.CharacterWithStudent;
import it.polimi.ingsw.model.exception.EmptyBagException;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.exception.InvalidIndexException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CharacterWithStudentTest {
    private CharacterWithStudent c = new CharacterWithStudent(1, 2, 4, "Name");
    private Bag bag = new Bag();
    @Test
    public void testAddStudents() throws EmptyBagException, InvalidIndexException {
        c.addStudents(bag.drawStudents(4));
        List<Integer> indexStudents = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            indexStudents.add(i);
        }
        for(Student s : c.getStudents(indexStudents)){
            assertNotNull(s);
        }
    }

    @Test
    public void testGetStudents() throws EmptyBagException, InvalidIndexException {
        c.addStudents(bag.drawStudents(4));
        List<Integer> indexStudents = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            indexStudents.add(i);
            assertNotNull(c.getStudents(indexStudents));
            indexStudents.clear();
        }
        assertEquals(0, c.getStudents(indexStudents).size());
    }

    @Test
    public void testGetHowManyStudents(){
        assertEquals(4, c.getHowManyStudents());
    }
}