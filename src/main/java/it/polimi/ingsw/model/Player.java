package it.polimi.ingsw.model;
import it.polimi.ingsw.model.exception.EmptyCloudException;
import it.polimi.ingsw.model.exception.FullTableException;
import it.polimi.ingsw.model.exception.InvalidIndexException;

import java.io.Serializable;
import java.util.*;

public class Player implements Serializable {
    private SchoolBoard schoolBoard;
    private GameTable gameTable;
    private final String nickName;
    private final int playerId;
    private List<Assistant> assistants;
    private String messageCli;
    private String messageGui;
    private boolean error;
    private Wizard wizard;

    /**
     * Creates a new PLayer and saves the received nickName, his playerID and his list of Assistants
     * @param nickName
     * @param playerId
     * @param assistants
     */
    public Player(String nickName, int playerId, List<Assistant> assistants) {
        this.nickName = nickName;
        this.playerId = playerId;
        this.assistants = new ArrayList<>();
        this.assistants.addAll(assistants);
        error = false;
        wizard = null;
    }

    /**
     * @return true if there is an error, false otherwise
     */
    public boolean isError() {
        return error;
    }

    /**
     * Sets the received error
     * @param error
     */
    public void setError(boolean error) {
        this.error = error;
    }

    public void setWizard(Wizard wizard){
        this.wizard = wizard;
    }

    public Wizard getWizard(){
        return this.wizard;
    }

    /**
     * @return the player nickName
     */
    public String getNickname(){
        return this.nickName;
    }

    /**
     * Saves the received gameTable as player gameTable
     * @param gameTable
     */
    public void addGameTable(GameTable gameTable){
        this.gameTable = gameTable;
    }

    /**
     * Saves the received schoolboard as player schoolboard
     * @param s
     */
    public void addSchoolBoard(SchoolBoard s){
        this.schoolBoard = s;
    }

    /**
     * Controls if the chosen Assistant exists and, in case, removes it
     * @param pos
     * @return the chosen Assistant
     * @throws InvalidIndexException
     */
    public Assistant playAssistant(int pos) throws InvalidIndexException {
        if(pos < 0 || pos >= assistants.size()) throw new InvalidIndexException("Assistant index out of bound");
        return this.assistants.remove(pos);
    }

    /**
     * Returns the list containing the player's assistants
     * @return list containing the assistants the player hasn't played yet
     */
    public List<Assistant> getAssistants() {
        List<Assistant> returnedAssistants = new ArrayList<>();
        returnedAssistants.addAll(assistants);
        return returnedAssistants;
    }

    /**
     * Return a copy of the chosen Assistant from his Assistants collection
     * @param id of the chosen assistant
     * @return the chosen Assistant
     * @throws InvalidIndexException
     */
    public Assistant getAssistant(int id) throws InvalidIndexException{
        boolean flag = false;
        int pos = -1;
        for(int i = 0; i < assistants.size(); i++) {
            if (assistants.get(i).getCardValue() == id) {
                flag = true;
                pos = i;
                break;
            }
        }
        if (flag) return assistants.get(pos);
        else throw new InvalidIndexException("The assistant doesn't exist!\n");
    }

    /**
     * Remove the chosen Assistant from his Assistants collection
     * @param id of the chosen assistant
     * @return the removed Assistant
     * @throws InvalidIndexException
     */
    public Assistant removeAssistant(int id) throws InvalidIndexException{
        boolean flag = false;
        int pos = -1;
        for(int i = 0; i < assistants.size(); i++) {
            if (assistants.get(i).getCardValue() == id) {
                flag = true;
                pos = i;
                break;
            }
        }
        if (flag) return assistants.remove(pos);
        else throw new InvalidIndexException("The assistant doesn't exist!\n");
    }

    /**
     * Moves the chosen Student on entrance to his table
     * @param pos of entrance student
     * @throws FullTableException
     * @throws InvalidIndexException
     */
    public void moveStudentOnTable(int pos) throws FullTableException, InvalidIndexException {
        this.schoolBoard.addStudentOnTable(pos);
    }

    /**
     * Moves the chosen Student on entrance to the chosen Island
     * @param posStudent
     * @param posIsland
     * @throws InvalidIndexException
     */
    public void moveStudentOnIsland(int posStudent, int posIsland) throws InvalidIndexException {
        Student s = this.schoolBoard.removeStudentFromEntrance(posStudent);
        this.gameTable.addStudentOnIsland(s, posIsland);
    }

    /**
     * @return the message for CLI client
     */
    public String getPlayerMessageCli() {
        return messageCli;
    }

    /**
     * @return the message for GUI client
     */
    public String getPlayerMessageGui() {
        return messageGui;
    }

    /**
     * Sets the message received as message for CLI client
     * @param message
     */
    public void setPlayerMessageCli(String message) {
        messageCli = message;
    }

    /**
     * Sets the message received as message for GUI client
     * @param message
     */
    public void setPlayerMessageGui(String message) {
        messageGui = message;
    }

    /**
     * Takes student from the selected cloud and add them to his entrance
     * @param indexCloud
     * @throws EmptyCloudException
     * @throws InvalidIndexException
     */
    public void takeStudentsFromCloud(int indexCloud) throws EmptyCloudException, InvalidIndexException {
        List<Student> s = this.gameTable.getStudentsOnCloud(indexCloud);
        this.schoolBoard.addStudentsOnEntrance(s);
    }

    /**
     * Adds a List of received assistant to his assistants
     * @param l
     */
    public void addAssistants(List<Assistant> l) {
        if(this.assistants.size() == 0)
            this.assistants.addAll(l);
    }

    /**
     * @return all the students on entrance
     */
    public Student[] getStudentsFormEntrance(){
        return this.schoolBoard.getStudentsFromEntrance();
    }
}
