package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.InvalidIndexException;
import it.polimi.ingsw.model.exception.IslandAlreadyForbiddenException;

import java.util.ArrayList;
import java.util.List;

public class MergedIslands extends Island {
    private final Island firstIsland;
    private final Island secondIsland;

    /**
     * Creates a new island which is a set of islands. The merged island is composed by the two island passed as parameter
     * @param firstIsland first island to merge
     * @param secondIsland second island to merge
     */
    public MergedIslands(Island firstIsland, Island secondIsland) {
        super(firstIsland.getIndex().get(0));
        this.firstIsland = firstIsland;
        this.secondIsland = secondIsland;
    }

    /**
     * Returns the indexes of the islands contained in the set of merged island
     * @return list of indexes of the merged island
     */
    @Override
    public List<Integer> getIndex() {
        List<Integer> index = new ArrayList<>();
        index.addAll(firstIsland.getIndex());
        index.addAll(secondIsland.getIndex());
        return index;
    }

    /**
     * Returns a list containing the tower which is placed on the island, if there isn't any tower on the island the list is empty
     * @return list containing the tower the island contains
     */
    @Override
    public List<Tower> getTowers() {
        List<Tower> towersOnTheIslands = new ArrayList<>();
        towersOnTheIslands.addAll(firstIsland.getTowers());
        towersOnTheIslands.addAll(secondIsland.getTowers());
        for(Tower tower : towersOnTheIslands) {
            if(tower == null) { } // exception
        }
        return towersOnTheIslands;
    }

    /**
     * Puts the towers contained in the lis
     * @param towersToSet list of towers within which there are the towers to put on the merged island. One tower per island
     */
    @Override
    public void setTowers(List<Tower> towersToSet){
        firstIsland.setTowers(towersToSet);
        secondIsland.setTowers(towersToSet);
    }

    /**
     * Removes the towers placed on the merged island and returns the list containing the removed towers
     * @return list containing the towers removed from the merged island
     */
    @Override
    public List<Tower> removeTowers() {
        List<Tower> towersOnTheIslands = new ArrayList<>();
        towersOnTheIslands.addAll(firstIsland.removeTowers());
        towersOnTheIslands.addAll(secondIsland.removeTowers());
        return towersOnTheIslands;
    }

    /**
     * Adds the student passed as parameter on the island which has the index passed as parameter
     * @param newStudent instance of the student to put on the island
     * @param index index of the island where the student has to be placed
     */
    public void addStudents(Student newStudent, int index) throws InvalidIndexException {
        if (firstIsland.getIndex().contains(index))
            firstIsland.addStudents(newStudent, index);
        else if (secondIsland.getIndex().contains(index))
            secondIsland.addStudents(newStudent, index);
        else
            throw new InvalidIndexException("The island index is wrong, you have to add the student on another island");
    }

    /**
     * Returns a list containing all the students placed on the set of merged island
     * @return list containing all the students placed on the set of merged island
     */
    @Override
    public List<Student> getStudents(){
        List<Student> studentsOnTheIslands = new ArrayList<>();
        studentsOnTheIslands.addAll(firstIsland.getStudents());
        studentsOnTheIslands.addAll(secondIsland.getStudents());
        return studentsOnTheIslands;
    }

    /**
     * Calculates the number of normal islands which are fused together in one merged island
     * @return number of normal islands which are fused together
     */
    @Override
    public int getAggregation(){
        return firstIsland.getAggregation() + secondIsland.getAggregation();
    }

    /**
     * Returns if there is a prohibition card on the merged island
     * @return true, if the island has a prohibition card on it; false, if the island han not a prohibition card on it
     */
    @Override
    public boolean isForbidden() {
        return firstIsland.isForbidden() || secondIsland.isForbidden();
    }

    /**
     * Puts a prohibition card on the island
     */
    @Override
    public void setProhibition() throws IslandAlreadyForbiddenException {
        if (firstIsland.isForbidden() || secondIsland.isForbidden()) throw new IslandAlreadyForbiddenException();
        firstIsland.setProhibition();
        secondIsland.setProhibition();
    }

    /**
     * Removes the prohibition card placed on the island
     */
    @Override
    public void resetProhibition() {
        firstIsland.resetProhibition();
        secondIsland.resetProhibition();
    }

    /**
     * Return the number of students on the merged Island for the selected color
     * @param color
     * @return
     */
    @Override
    public int getNumberOfStudentsForColor(PawnColor color) {
        return (firstIsland.getNumberOfStudentsForColor(color) + secondIsland.getNumberOfStudentsForColor(color));
    }

}
