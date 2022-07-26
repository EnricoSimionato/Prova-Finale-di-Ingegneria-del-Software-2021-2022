package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.InconsistentStateException;
import it.polimi.ingsw.model.exception.InvalidIndexException;
import it.polimi.ingsw.model.exception.IslandAlreadyForbiddenException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Island implements Serializable {

    /**
     * @index is the index/identifier of the island in the group of islands on the game table (in the beginning).
     * The index is created at the beginning of the match and remains the same even the island merges itself to another one
     */
    private final int index;
    private Tower tower;
    private List<Student> students;
    private boolean prohibition;


    /**
     * Creates an island which index is the index passed as parameter
     * @param index index of the new island
     */
    public Island(int index) {
        this.index = index;
        students = new ArrayList<>();
    }

    /**
     * Returns the index of the island in the group of island on the beginning game table
     * @return index of the island in the group of island on the beginning game table
     */
    public List<Integer> getIndex() {
        List<Integer> index = new ArrayList<>();
        index.add(this.index);
        return index;
    }

    /**
     * Returns a list containing the tower which is placed on the island, if there isn't any tower on the island the list is empty
     * @return list containing the tower the island contains
     */
    public List<Tower> getTowers() {
        List<Tower> towersOnTheIsland = new ArrayList<>();
        if ( tower != null ) towersOnTheIsland.add(tower);
        return towersOnTheIsland;
    }

    /**
     * Puts the first tower of the list on the island
     * @param tower list of towers within which, on index 0, there is the tower to put on the island
     */
    public void setTowers(List<Tower> tower){
        this.tower = tower.get(0);
    }

    /**
     * Removes the tower which is placed on the island from it and returns the list containing the removed tower
     * @return list containing the tower removed from the island
     */
    public List<Tower> removeTowers() {
        List<Tower> removedTowers = new ArrayList<>();
        if (tower != null) {
            removedTowers.add(tower);
            tower = null;
        }
        return removedTowers;
    }

    /**
     * Adds the student passed as parameter on the island which has the index passed as parameter
     * @param newStudent instance of the student to put on the island
     * @throws InvalidIndexException if the index passed as parameter doesn't match with the index of the island
     */
    public void addStudents(Student newStudent, int index) throws InconsistentStateException {
        if (this.index != index) throw new InconsistentStateException("The island index is wrong, you have to add the student on another island", toString(), "addStudents(Student newStudent, int index)");
        students.add(newStudent);
    }

    /**
     * Returns a list containing all the students placed on the island
     * @return list containing all the students placed on the island
     */
    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    /**
     * Returns one because the instance of a normal island contains one single island
     * @return one
     */
    public int getAggregationDimension() {
        return 1;
    }

    /**
     * Returns if there is a prohibition card on the island
     * @return true, if the island has a prohibition card on it; false, if the island han not a prohibition card on it
     */
    public boolean isForbidden() {
        return prohibition;
    }

    /**
     * Puts a prohibition card on the island
     */
    public void setProhibition() throws IslandAlreadyForbiddenException {
        if (this.prohibition) throw new IslandAlreadyForbiddenException();
        this.prohibition = true;
    }

    /**
     * Removes the prohibition card placed on the island
     */
    public void resetProhibition() {
        prohibition = false;
    }


    /**
     * Returns the number of students on the island which are of the color passed as parameter
     * @param color color of the students which are looked for
     * @return number of students of the color passed as parameter which are on the island
     */
    public int getNumberOfStudentsForColor(PawnColor color) {
        int count = 0;
        for (Student s : students) {
            if (s.getColor() == color) count++;
        }
        return count;
    }
}
