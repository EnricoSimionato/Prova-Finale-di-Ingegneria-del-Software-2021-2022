package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SchoolBoard implements Serializable {
    private final int numberOfStudentsOnEntrance;
    private final Student[] entrance;
    private final int numberOfTables = 5;
    private final Table[] tables;
    private final int numberOfTowers;
    private final TowerColor towersColor;
    private final List<Tower> towers;

    /**
     * Creates a school board
     * @param numberOfStudentsOnEntrance maximum number of student the entrance of the school board cn contain
     * @param schoolBoardTowerColor color of the school board. Every tower of the school board will have this color
     * @param numberOfTowers number of towers on the initial school board configuration
     */
    public SchoolBoard(int numberOfStudentsOnEntrance, TowerColor schoolBoardTowerColor, int numberOfTowers) {
        this.numberOfStudentsOnEntrance = numberOfStudentsOnEntrance;
        entrance = new Student[this.numberOfStudentsOnEntrance];

        tables = new Table[numberOfTables];
        for(PawnColor tableColor : PawnColor.values())
            tables[tableColor.getIndex()] = new Table(tableColor);

        this.numberOfTowers = numberOfTowers;
        towersColor = schoolBoardTowerColor;
        towers = new ArrayList<>();
        for(int i = 0; i < this.numberOfTowers; i++) {
            towers.add(new Tower(schoolBoardTowerColor));
        }
    }

    /**
     * Adds a student on the table of his color
     * @param index index of entrance where is placed the student which has to be moved
     * @throws FullTableException if the table of the student's color has no empty seats
     */
    public void addStudentOnTable(int index) throws FullTableException, InvalidIndexException {
        if(index < 0 || index >= numberOfStudentsOnEntrance) throw new InvalidIndexException("The student doesn't exist");
        if (entrance[index] == null) throw new InvalidIndexException("You already moved the student");
        tables[entrance[index].getColor().getIndex()].addStudent(entrance[index]);
        entrance[index] = null;
    }

    /**
     * @return the total number of students of all tables
     */
    public int getTotalNumberOfStudentsOnTables() {
        int sum = 0;
        for (PawnColor color : PawnColor.values()) {
            sum += getNumberOfStudentsOnTable(color);
        }
        return sum;
    }

    /**
     * Adds a student on the table of his color
     * @param student student which has to be put on the table
     * @throws FullTableException if the table of the student's color has no empty seats
     */
    public void addStudentOnTable(Student student) throws FullTableException {
        this.tables[student.getColor().getIndex()].addStudent(student);
    }

    /**
     * Removes a student from a table
     * @param color color of the student to remove
     * @return removed student
     * @throws EmptyTableException if there are no more student on the table, the table is empty
     */
    public Student removeStudentFromTable(PawnColor color) throws EmptyTableException {
        if(tables[color.getIndex()].getNumberOfStudents() <= 0) throw new EmptyTableException();
        return tables[color.getIndex()].removeStudentFromTable();
    }


    /**
     * Removes a student from a table
     * @param tableIndex index of the table from which it has to be removed a student
     * @return removed student
     * @throws EmptyTableException if there are no more student on the table, the table is empty
     */
    public Student removeStudentFromTable(int tableIndex) throws EmptyTableException {
        if(tables[tableIndex].getNumberOfStudents() <= 0) throw new EmptyTableException();
        return tables[tableIndex].removeStudentFromTable();
    }

    /**
     * Adds all the students on the list on the empty position on the entrance
     * @param newStudents list of the students to add on the entrance
     */
    public void addStudentsOnEntrance(List<Student> newStudents) {
        for(int entranceIndex = 0; entranceIndex < entrance.length; entranceIndex++){
            if(entrance[entranceIndex] == null){
                entrance[entranceIndex] = newStudents.remove(0);
                if (newStudents.size() == 0) break;
            }
        }
    }

    /**
     * Returns all the professors that the schoolBoard has.
     * @return a list with all the professors
     */
    public List<PawnColor> getProfessors() {
        List<PawnColor> professors = new ArrayList<>();
        for(PawnColor color : PawnColor.values()) {
            if(tables[color.getIndex()].hasProfessor()) {
                professors.add(color);
            }
        }
        return professors;
    }

    /**
     * Sets the value for the professor
     * @param color color of the professor to set
     * @param value value for the professor. True = I have the professor - False = I don't have the professor
     */
    public void setProfessor(PawnColor color, boolean value) {
        tables[color.getIndex()].setProfessor(value);
    }

    /**
     * @return all the towers on the schoolBoard
     */
    public List<Tower> getTowers() {
        return towers;
    }

    /**
     * Removes towers from the schoolBoard and it returns them.
     * @param numberOfTowers number of towers to removed
     * @return all the towers removed
     * @throws InvalidIndexException if the number of tower to be removed is negative
     * @throws NoMoreTowersException if the number of tower to be removed is higher than the number of tower on the schoolboard
     */
    public List<Tower> removeTowers(int numberOfTowers) throws InvalidIndexException, NoMoreTowersException {
        if(numberOfTowers < 0) throw new InvalidIndexException("Numero non valido");
        if(numberOfTowers >= towers.size()) throw new NoMoreTowersException(towersColor);
        List<Tower> removedTowers = new ArrayList<>();
        for(int i = 0; i < numberOfTowers; i++)
            removedTowers.add(towers.remove(0));
        return removedTowers;
    }

    /**
     * Adds towers to the schoolBoard
     * @param towerToAdd a list of the tower that it has to add
     */
    public void addTowers(List<Tower> towerToAdd) {
        if (towers.size() < 8 && (towerToAdd.size() + towers.size() <= 8))  towers.addAll(towerToAdd);
    }

    /**
     * Removes a student from the entrance and it returns that.
     * @param index of the student to be removed
     * @return the removed student
     * @throws InvalidIndexException if the index is out of boud or if there isn't the student on this position
     */
    public Student removeStudentFromEntrance(int index) throws InvalidIndexException{
        if (entrance[index] == null) throw new InvalidIndexException("No student on entrance position");
        if(index < 0 || index >= entrance.length) throw new InvalidIndexException("There isn't a student on this position");
        else{
            Student studentToBeRemoved = entrance[index];
            entrance[index] = null;
            return studentToBeRemoved;
        }
    }

    /**
     * @param tableColor
     * @return the number of students on the table with the inout color
     */
    public int getNumberOfStudentsOnTable(PawnColor tableColor) {
        return tables[tableColor.getIndex()].getNumberOfStudents();
    }

    /**
     * @param index of table
     * @return total number of students on a specific table
     */
    public int getNumberOfStudentsOnTable(int index) {
        return tables[index].getNumberOfStudents();
    }

    /**
     * @return the tower color on the schoolBoard
     */
    public TowerColor getTowersColor() {
        return towersColor;
    }

    /**
     * Returns a copy of the entrance
     * @return an array that is the copy of the entrance
     */
    public Student[] getStudentsFromEntrance(){
        Student[] returnEntrance = new Student[numberOfStudentsOnEntrance];
        System.arraycopy(entrance, 0, returnEntrance, 0, numberOfStudentsOnEntrance);
        return returnEntrance;
    }

    /**
     * @return the max number of students on the entrance
     */
    public int getNumberOfStudentsOnEntrance(){
        return numberOfStudentsOnEntrance;
    }

}
