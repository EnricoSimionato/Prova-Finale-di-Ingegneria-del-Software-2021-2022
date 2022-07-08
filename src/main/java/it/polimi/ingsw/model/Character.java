package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.*;

import java.io.Serializable;
import java.util.List;


public class Character extends Round implements Serializable {
    private final int ID;
    private int cost;
    private boolean firstUse;
    private Round round;
    private int oldState;
    private String name;

    /**
     * Creates a character card with the given two values
     * @param id   integer that identifies the character card
     * @param cost amount of money needed to activate the card effect
     */
    public Character(int id, int cost, String name) {
        super();
        this.ID = id;
        this.cost = cost;
        firstUse = false;
        round = null;
        this.name = name;
    }


    /**
     * Returns the identifier of the character card
     * @return the identifier of the character card
     */
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns the cost of activation of the card
     * @return the cost of activavtion of the card
     */
    public int getCost() {
        return cost;
    }

    /**
     * Returns true if the character card has been used one or more times and returns false if its effect hasn't been activated yet
     * @return the value of usage of the character card - false if it hasn't been used yet
     */
    public boolean getFirstUse() {
        return firstUse;
    }

    /**
     * Sets the card as "already been used once" so it costs one more coin to be activated that the initial cost
     */
    public void setFirstUse() {
        firstUse = true;
        cost++;
    }

    /**
     * calls the method getPlayerOnTurn on round
     * @return
     */
    @Override
    public int getPlayerOnTurn() { return round.getPlayerOnTurn(); }

    /**
     * returns the round attribute
     * @return
     */
    @Override
    public Round getRound() {
        return round;
    }

    /**
     * calls the method getGame on round
     * @return
     */
    @Override
    public Game getGame() {
        return round.getGame();
    }

    /**
     * calls the method getPlayedAssistant on round
     * @return
     */
    @Override
    public Round.PlayedAssistant[] getPlayedAssistants(){
        return round.getPlayedAssistants();
    }

    /**
     * calls the method getPlayerOrder on round
     * @return
     */
    @Override
    public int[] getPlayerOrder() { return round.getPlayerOrder(); }

    /**
     * calls the method checkPlayerOnTurn on round with the passed parameters
     * @param playerId
     * @throws PlayerNotOnTurnException
     */
    @Override
    public void checkPlayerOnTurn(int playerId) throws PlayerNotOnTurnException { round.checkPlayerOnTurn(playerId); }

    /**
     * calls the method setRoundState on round with the passed parameters
     * @param state
     */
    @Override
    public void setRoundState(int state) { round.setRoundState(state); }

    /**
     * calls the method getRoundState on round
     * @return
     */
    @Override
    public int getRoundState(){
        return round.getRoundState();
    }

    /**
     * calls the method getMoveCounter on round
     * @return
     */
    @Override
    public int[] getMovesCounter(){
        return round.getMovesCounter();
    }

    /**
     * calls the method setMovesCounter on round with the passed parameters
     * @param playerId
     * @param moves
     */
    @Override
    public void setMovesCounter(int playerId, int moves){
        round.setMovesCounter(playerId, moves);
    }

    /**
     * calls the method setIndexOfPlayerOnTurn on round with the passed parameters
     * @param index
     */
    @Override
    public void setIndexOfPlayerOnTurn(int index)  { round.setIndexOfPlayerOnTurn(index); }

    /**
     * calls the method getIndexOfPlayerOnTurn on round
     * @return
     */
    @Override
    public int getIndexOfPlayerOnTurn(){
        return round.getIndexOfPlayerOnTurn();
    }

    /**
     * calls the method setAlreadyPlayedCharacter on round with the passed parameters
     * @param alreadyPlayedCharacter
     */
    @Override
    public void setAlreadyPlayedCharacter(boolean alreadyPlayedCharacter) { round.setAlreadyPlayedCharacter(alreadyPlayedCharacter); }

    /**
     * calls the method calculateFirstPlayer on round with the passed parameters
     * @param numberOfPlayers
     * @return
     */
    @Override
    public int calculateFirstPlayer(int numberOfPlayers) { return round.calculateFirstPlayer(numberOfPlayers); }

    /**
     * calls the method checkStatusAndMethod on round with the passed parameters
     * @param methodId
     * @throws InvalidMethodException
     */
    @Override
    public void checkStatusAndMethod(int methodId) throws InvalidMethodException { round.checkStatusAndMethod(methodId); }

    /**
     * calls the method checkNumberOfMoves on round with the passed parameters
     * @param playerId
     * @throws TooManyMovesException
     */
    @Override
    public void checkNumberOfMoves(int playerId) throws TooManyMovesException { round.checkNumberOfMoves(playerId); }

    /**
     * calls the method setPlayerMessageCli on round with the passed parameters
     * @param playerId
     * @param message
     */
    @Override
    public void setPlayerMessageCli(int playerId, String message) { round.setPlayerMessageCli(playerId, message); }

    /**
     * calls the method setPlayerMessageGui on round with the passed parameters
     * @param playerId
     * @param message
     */
    @Override
    public void setPlayerMessageGui(int playerId, String message) { round.setPlayerMessageGui(playerId, message); }

    /**
     * calls the method setMessageToAPlayerAndWaitingMessageForOthers on round with the passed parameters
     * @param playerId
     * @param messageCli
     * @param messageGui
     */
    @Override
    public void setMessageToAPlayerAndWaitingMessageForOthers(int playerId, String messageCli, String messageGui) { round.setMessageToAPlayerAndWaitingMessageForOthers(playerId, messageCli, messageGui); }

    /**
     * calls the method getStateMessageCli on round
     * @return
     */
    @Override
    public String getStateMessageCli() { return round.getStateMessageCli(); }

    /**
     * calls the method getStateMessageGui on round
     * @return
     */
    @Override
    public String getStateMessageGui() { return round.getStateMessageGui(); }

    /**
     * calls the method isPianificationPhaseEnded on round
     * @return
     */
    @Override
    public boolean isPianificationPhaseEnded() { return round.isPianificationPhaseEnded(); }

    /**
     * calls the method isActionPhaseEnded on round
     * @return
     */
    @Override
    public boolean isActionPhaseEnded() { return round.isActionPhaseEnded(); }

    /**
     * calls the method isTimeToChooseTheNextStudent on round
     * @return
     */
    @Override
    public boolean isTimeToChooseTheNextStudent() { return round.isTimeToChooseTheNextStudent(); }

    /**
     * calls the method isTimeToMoveMotherNature on round
     * @return
     */
    @Override
    public boolean isTimeToMoveMotherNature() { return round.isTimeToMoveMotherNature(); }

    /**
     * calls the method isTimeToChooseACloud on round
     * @return
     */
    @Override
    public boolean isTimeToChooseACloud() { return round.isTimeToChooseACloud(); }

    /**
     * calls the method cloudHasBeenChosen on round
     * @return
     */
    @Override
    public boolean cloudHasBeenChosen() { return round.cloudHasBeenChosen(); }

    /**
     * calls the method isTheGameEnded on round
     * @return
     */
    @Override
    public boolean isTheGameEnded() { return round.isTheGameEnded(); }

    /**
     * calls the method setPianificationPhaseOrder on round
     */
    @Override
    public void setPianificationPhaseOrder() { round.setPianificationPhaseOrder(); }

    /**
     * calls the method setActionPhaseOrder on round
     */
    @Override
    public void setActionPhaseOrder() { round.setActionPhaseOrder(); }

    /**
     * calls the method switchToPianificationPhase on round
     */
    @Override
    public void switchToPianificationPhase() { round.switchToPianificationPhase();}

    /**
     * calls the method switchToActionPhase on round
     */
    @Override
    public void switchToActionPhase() { round.switchToActionPhase(); }

    /**
     * calls the method calculateNextPlayer on round
     */
    @Override
    public void calculateNextPlayer() {
        round.calculateNextPlayer();
    }

    /**
     * calls the method assistantNoChoice on round with the passed parameters
     * @param outer
     * @param inner
     * @return
     */
    @Override
    public boolean assistantNoChoice(List<Assistant> outer, List<Assistant> inner) { return round.assistantNoChoice(outer, inner); }

    /**
     * calls the method removeAssistant on round with the passed parameters
     * @param playerId
     * @param assistantPosition
     * @throws InvalidIndexException
     */
    @Override
    public void removeAssistant(int playerId, int assistantPosition) throws InvalidIndexException { round.removeAssistant(playerId, assistantPosition); }

    /**
     * calls the method playAssistant on round with the passed parameters
     * @param playerId
     * @param assistantPosition
     */
    @Override
    public void playAssistant(int playerId, int assistantPosition) { round.playAssistant(playerId, assistantPosition); }

    /**
     * calls the method addStudentsOnIsland on round with the passed parameters
     * @param playerId
     * @param studentIndex
     * @param islandIndex
     */
    @Override
    public void addStudentOnIsland(int playerId, int studentIndex, int islandIndex) { round.addStudentOnIsland(playerId, studentIndex, islandIndex); }

    /**
     * calls the method addStudentsOnTable on round with the passed parameters
     * @param playerId
     * @param studentIndex
     */
    @Override
    public void addStudentOnTable(int playerId, int studentIndex) { round. addStudentOnTable(playerId, studentIndex); }

    /**
     * calls the method isANewAllowedPositionForMotherNature on round with the passed parameters
     * @param assistant
     * @param islandIndex
     * @return
     */
    @Override
    public boolean isANewAllowedPositionForMotherNature(Assistant assistant, int islandIndex) { return round.isANewAllowedPositionForMotherNature(assistant, islandIndex); }

    /**
     * calls the method checkEndGameAndSetTheWinner on round
     */
    @Override
    public void checkEndgameAndSetTheWinner() { round.checkEndgameAndSetTheWinner(); }

    @Override
    /**
     * Changes mother nature position, calculate the influences of the players on the island and puts or changes the tower on the island
     * @param playerId player ID of the player which want to make the move
     * @param islandIndex index of the island on which the player wants to move mothter nature
     */
    public void changeMotherNaturePosition (int playerId, int islandIndex) { round.changeMotherNaturePosition(playerId, islandIndex); }

    /**
     * calls the method getStudentsFromCloud on round with the passed parameters
     * @param playerId
     * @param cloudIndex
     */
    @Override
    public void getStudentsFromCloud(int playerId, int cloudIndex) { round.getStudentsFromCloud(playerId, cloudIndex); }

    /**
     * It stores the old state value
     * @param playerID
     * @param oldRound
     * @return
     * @throws EffectCannotBeActivatedException
     */
    public Round activateEffect(int playerID, Round oldRound) throws EffectCannotBeActivatedException {
        oldState = oldRound.getRoundState();
        round = oldRound;
        return this;
    }

    /**
     * reset the state that was before the effect activation
     * @param resetState
     */
    public void deactivateEffect(boolean resetState) {
        if (resetState) round.setRoundState(oldState);
        round.getGame().setRound(round);
        round.setPlayerMessageCli(getPlayerOnTurn(), getStateMessageCli());
        round.setPlayerMessageGui(getPlayerOnTurn(), getStateMessageGui());
    }

    /**
     *
     * @param playerId
     * @param parameter
     * @throws InvalidIndexException
     */
    @Override
    public void doYourJob(int playerId, int parameter) throws InvalidIndexException { }

    /**
     * returns the value given by the calling of the method getAlreadyPLayedCharachter on round
     * @return
     */
    @Override
    public boolean getAlreadyPLayedCharacter() {
        return round.getAlreadyPLayedCharacter();
    }

    /**
     * returns the oldState attribute
     * @return
     */
    public int getOldState() {
        return oldState;
    }

    /**
     * sets the oldState attribute with the value passed as a parameter
     * @param oldState
     */
    public void setOldState(int oldState) {
        this.oldState = oldState;
    }

    /**
     * sets the round attribute with the value passed as a parameter
     * @param round
     */
    public void setRound(Round round) {
        this.round = round;
    }
}