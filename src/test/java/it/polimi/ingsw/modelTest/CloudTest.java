package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CloudTest {

    private Cloud c = new Cloud(3);
    private Bag bag = new Bag();

    @Test
    public void testGetNumberOfStudents() {
        assertTrue(c.getNumberOfStudents() == 3);
    }
    @Test
    public void testGetStudents() throws EmptyBagException {
        c.addStudents(bag.drawStudents(3));
        List<Student> students = new ArrayList<>(c.getStudents());
        assertTrue(students.size() > 0);
        for(Student s : students) assertNotNull(s);
    }
    @Test
    public void testRemoveStudents() throws EmptyBagException {
        c.addStudents(bag.drawStudents(3));
        List<Student> studentsRemoved = new ArrayList<>(c.removeStudents());
        assertEquals(0, c.getStudents().size());
        assertEquals(3, studentsRemoved.size());
        for(Student s : studentsRemoved) assertNotNull(s);
    }

    @Test
    public void testAddStudents() throws EmptyBagException {
        c.addStudents(bag.drawStudents(3));
        for(Student s : c.getStudents()){
            assertNotNull(s);
        }
    }
}