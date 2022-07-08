package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exception.*;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

public class SchoolBoardTest {

    private SchoolBoard schoolBoard = new SchoolBoard(9, TowerColor.BLACK, 8);
    private Bag bag = new Bag();

    @Test
    public void testAddStudentOnTable() throws FullTableException, EmptyBagException, InvalidIndexException {
        schoolBoard.addStudentsOnEntrance(bag.drawStudents(9));
        Student[] entrance = schoolBoard.getStudentsFromEntrance();
        int[] numberOfStudentsForColor = new int[5];
        for (int i = 0; i < 9; i++) {
            numberOfStudentsForColor[entrance[i].getColor().getIndex()]++;
            schoolBoard.addStudentOnTable(i);
        }

        for (PawnColor color : PawnColor.values()) {
            assertEquals(numberOfStudentsForColor[color.getIndex()], schoolBoard.getNumberOfStudentsOnTable(color));
        }
    }

    @Test
    public void testGetTotalNumberOfStudentsOnTables() throws FullTableException {
        schoolBoard.addStudentOnTable(new Student(PawnColor.YELLOW));
        schoolBoard.addStudentOnTable(new Student(PawnColor.GREEN));
        schoolBoard.addStudentOnTable(new Student(PawnColor.BLUE));
        schoolBoard.addStudentOnTable(new Student(PawnColor.GREEN));
        schoolBoard.addStudentOnTable(new Student(PawnColor.BLUE));
        assertEquals(5, schoolBoard.getTotalNumberOfStudentsOnTables());
    }

    @Test
    public void testTestAddStudentOnTable() throws FullTableException, EmptyBagException {
        schoolBoard.addStudentsOnEntrance(bag.drawStudents(9));
        Student[] entrance = schoolBoard.getStudentsFromEntrance();
        int[] numberOfStudentsForColor = new int[5];
        for (Student s : entrance) {
            schoolBoard.addStudentOnTable(s);
            numberOfStudentsForColor[s.getColor().getIndex()]++;
        }

        for (PawnColor color : PawnColor.values()) {
            assertEquals(numberOfStudentsForColor[color.getIndex()], schoolBoard.getNumberOfStudentsOnTable(color));
        }
    }

    @Test
    public void testAddStudentsOnEntrance() throws EmptyBagException, InvalidIndexException {
        Student[] entrance = schoolBoard.getStudentsFromEntrance();
        schoolBoard.addStudentsOnEntrance(bag.drawStudents(9));
        for (int i = 0; i < 9; i++) {
            schoolBoard.removeStudentFromEntrance(i);
        }
        schoolBoard.addStudentsOnEntrance(bag.drawStudents(9));
        for (int i = 0; i < 9; i++) {
            assertNotEquals(entrance[i], schoolBoard.getStudentsFromEntrance()[i]);
            assertNotNull(schoolBoard.getStudentsFromEntrance()[i]);
        }
    }

    @Test
    public void testGetProfessors() {
        assertEquals(0, schoolBoard.getProfessors().size());
        schoolBoard.setProfessor(PawnColor.YELLOW, true);
        schoolBoard.setProfessor(PawnColor.BLUE, true);

        List<PawnColor> professors = schoolBoard.getProfessors();
        assertEquals(PawnColor.YELLOW, professors.remove(0));
        assertEquals(PawnColor.BLUE, professors.remove(0));

        schoolBoard.setProfessor(PawnColor.BLUE, false);
        professors = schoolBoard.getProfessors();
        assertEquals(PawnColor.YELLOW, professors.remove(0));
    }

    @Test
    public void testSetProfessor() {
        for(PawnColor color : PawnColor.values()) {
            schoolBoard.setProfessor(color, true);
        }
        List<PawnColor> professors = schoolBoard.getProfessors();
        for (PawnColor color : PawnColor.values()) {
            assertEquals(color, professors.remove(0));
        }

        for(PawnColor color : PawnColor.values()) {
            schoolBoard.setProfessor(color, false);
        }
        assertEquals(0, schoolBoard.getProfessors().size());
    }

    @Test
    public void testGetTowers() throws InvalidIndexException, NoMoreTowersException {
        for (Tower t : schoolBoard.getTowers()){
            assertNotNull(t);
            assertEquals(TowerColor.BLACK, t.getColor());
        }

        List<Tower> towers = schoolBoard.removeTowers(7);
        assertEquals(1, schoolBoard.getTowers().size());
    }

    @Test
    public void testRemoveTowers() {

        try {
            List<Tower> towers = schoolBoard.removeTowers(8);
            assertEquals(8, towers.size());
            schoolBoard.removeTowers(8);
        }catch (NoMoreTowersException | InvalidIndexException e){
            assertTrue(true);
        }
        try {
            schoolBoard.removeTowers(1);
        }catch (InvalidIndexException | NoMoreTowersException e) {
            assertTrue(true);
        }

    }

    @Test
    public void testAddTowers() throws InvalidIndexException, NoMoreTowersException {
        List<Tower> towersRemoved = schoolBoard.removeTowers(7);
        schoolBoard.addTowers(towersRemoved);
        assertEquals(8, schoolBoard.getTowers().size());
    }

    @Test
    public void testRemoveStudentFromEntrance() {
        try {
            Student[] entrance = schoolBoard.getStudentsFromEntrance();
            for (int i = 0; i < 9; i++) {
                assertEquals(schoolBoard.removeStudentFromEntrance(i).getColor(), entrance[i].getColor());
            }
            for (int i = 0; i < 9; i++) {
                assertNull(schoolBoard.getStudentsFromEntrance()[i]);
            }
            schoolBoard.removeStudentFromEntrance(1);
        }catch (InvalidIndexException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetNumberOfStudentsOnTable() throws FullTableException, EmptyBagException, InvalidIndexException {
        schoolBoard.addStudentsOnEntrance(bag.drawStudents(9));
        Student[] entrance = schoolBoard.getStudentsFromEntrance();
        int[] numberOfStudentsForColor = new int[5];
        for (int i = 0; i < 9; i++) {
            schoolBoard.addStudentOnTable(i);
            numberOfStudentsForColor[entrance[i].getColor().getIndex()]++;
        }

        for(PawnColor color : PawnColor.values()) {
            assertEquals(numberOfStudentsForColor[color.getIndex()], schoolBoard.getNumberOfStudentsOnTable(color));
        }
    }

    @Test
    public void testGetTowersColor() {
        assertEquals(TowerColor.BLACK, schoolBoard.getTowersColor());
    }

    @Test
    public void testGetStudentsFromEntrance() throws InvalidIndexException, EmptyBagException {
        schoolBoard.addStudentsOnEntrance(bag.drawStudents(9));
        for (int i = 0; i < 9; i++) {
            assertNotNull(schoolBoard.getStudentsFromEntrance()[i]);
            schoolBoard.removeStudentFromEntrance(i);
        }
        for (int i = 0; i < 9; i++) {
            assertNull(schoolBoard.getStudentsFromEntrance()[i]);
        }
    }

    @Test
    public void testGetNumberOfStudentsOnEntrance(){
        assertEquals(9, schoolBoard.getNumberOfStudentsOnEntrance());
    }

    @Test
    public void testRemoveStudentFromTableIndexTable() throws FullTableException {
        Student s = new Student(PawnColor.YELLOW);
        schoolBoard.addStudentOnTable(s);
        assertEquals(1, schoolBoard.getNumberOfStudentsOnTable(PawnColor.YELLOW));
        try{
            schoolBoard.removeStudentFromTable(0);
            assertEquals(0, schoolBoard.getNumberOfStudentsOnTable(PawnColor.YELLOW));
            schoolBoard.removeStudentFromTable(0);
        }catch (EmptyTableException e){
            assertTrue(true);
        }

    }

    @Test
    public void testRemoveStudentFromTablePawnColor() throws FullTableException {
        Student s = new Student(PawnColor.YELLOW);
        schoolBoard.addStudentOnTable(s);
        assertEquals(1, schoolBoard.getNumberOfStudentsOnTable(PawnColor.YELLOW));
        try {
            schoolBoard.removeStudentFromTable(PawnColor.YELLOW);
            assertEquals(0, schoolBoard.getNumberOfStudentsOnTable(PawnColor.YELLOW));
            schoolBoard.removeStudentFromTable(PawnColor.YELLOW);
        }catch (EmptyTableException e) {
            assertTrue(true);
        }
    }
}