package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.exception.EmptyBagException;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.PawnColor;

import org.junit.Test;;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BagTest {
    private Bag bag = new Bag();
    private boolean flag;
    private int arrayCountColor[];

    @Test
    public void testDrowStudent() throws EmptyBagException {
        arrayCountColor = new int[5];

        //test remove all student and controll color
        for(int j = 0; j < 130; j++) {
            flag = false;
            List<Student> s = new ArrayList<>(bag.drawStudents(1));
            assertNotNull(s);

            for (PawnColor c : PawnColor.values()) {
                if (c == s.get(0).getColor()) {
                    arrayCountColor[s.get(0).getColor().getIndex()]++;
                    flag = true;
                    break;
                }
            }
            assertTrue(flag);
        }
        //controll number of student foreach PawnColor
        for(int i = 0; i < 5; i++) assertTrue(arrayCountColor[i] == 26);
    }

    @Test
    public void testAddStudents() throws EmptyBagException {
        List<Student> newStudents = new ArrayList<>();
        newStudents.addAll(bag.drawStudents(130));
        bag.addStudents(newStudents);
        assertEquals(130, bag.drawStudents(130).size());
    }

    @Test
    public void testDrawStudentsByColor() {
        assertEquals(bag.drawStudentsByColor(1, PawnColor.YELLOW).get(0).getColor(), PawnColor.YELLOW);
        assertEquals(bag.drawStudentsByColor(5, PawnColor.RED).size(), 5);
        for (Student s : bag.drawStudentsByColor(3, PawnColor.BLUE)) assertEquals(s.getColor(), PawnColor.BLUE);
    }
}