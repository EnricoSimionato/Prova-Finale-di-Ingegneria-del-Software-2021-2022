package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.message.*;
import it.polimi.ingsw.model.exception.*;

import java.io.Serializable;
import java.util.*;

public class Round implements Serializable {

    private Game game;
    protected int roundState;
    private int previousState;
    private int[] playerOrder;
    private int indexOfPlayerOnTurn;
    private int[] movesCounter;
    private PlayedAssistant[] playedAssistants;
    private boolean[] alreadyPlayedAssistants;
    private List<Assistant> playedAssistantsPF;
    private boolean alreadyPlayedCharacter;
    private boolean islandMessage;

    public class PlayedAssistant implements Serializable {
        private int playerIndex;
        private Assistant assistant;

        /**
         * Creates a new played assistant
         * @param playerIndex
         * @param assistant
         */
        public PlayedAssistant(int playerIndex, Assistant assistant) {
            this.playerIndex = playerIndex;
            this.assistant = assistant;
        }

        /**
         * Returns
         * @return player that had played the assistant
         */
        public int getPlayerIndex() {
            return playerIndex;
        }

        /**
         * Returns the chosen assistant
         * @return chosen assistant
         */
        public Assistant getAssistant() {
            return assistant;
        }
    }

    /**
     * Creates a new round with every attribute initialized to null
     */
    public Round() { }

    /**
     * Creates a new round which can be played
     * @param game instance of the current match
     */
    public Round(Game game) {
        this.game = game;
        roundState = 0;
        previousState = 0;

        playerOrder = new int[game.getNumberOfPlayers()];
        playerOrder[0] = calculateFirstPlayer(game.getNumberOfPlayers());
        for (int i = 1; i < game.getNumberOfPlayers(); i++) playerOrder[i] = (playerOrder[i - 1] + 1) % game.getNumberOfPlayers();
        indexOfPlayerOnTurn = 0;

        movesCounter = new int[game.getNumberOfPlayers()];
        for (int i = 0; i < game.getNumberOfPlayers(); i++)
            movesCounter[i] = 0;

        playedAssistants = new PlayedAssistant[game.getNumberOfPlayers()];
        alreadyPlayedAssistants = new boolean[game.getNumberOfPlayers()];
        for (int i = 0; i < alreadyPlayedAssistants.length; i++)
            alreadyPlayedAssistants[i] = false;
        playedAssistantsPF = new ArrayList<>();

        alreadyPlayedCharacter = false;

        islandMessage = false;

        setMessageToAPlayerAndWaitingMessageForOthers(playerOrder[0], "Select an assistant", "Select an assistant");
    }

    /**
     * Randomly calculates the index of the first player which has to play the round
     * @param numberOfPlayers number of players playng the game
     * @return index of the first player which has to play the round
     */
    public int calculateFirstPlayer(int numberOfPlayers){
        Random generator = new Random();
        int firstPlayer = generator.nextInt(numberOfPlayers);
        return firstPlayer;
    }

    /**
     * Creates a new round which can be played
     * @param game instance of the current match
     * @param playerOrder player order which comes from the last round
     */
    public Round(Game game, int[] playerOrder) {
        this(game);
        for(int i = 0; i < game.getNumberOfPlayers(); i++)
            this.playerOrder[i] = playerOrder[i];
        setMessageToAPlayerAndWaitingMessageForOthers(playerOrder[0], "Select an assistant", "Select an assistant");
    }

    /*-----------------------------------------------------------------------------------------\
    |                                                                                          |
    |                                   SETTER AND GETTER                                      |
    |                                                                                          |
    \-----------------------------------------------------------------------------------------*/

    /**
     * Returns the instance of the round itself
     * @return the instance of the round itself
     */
    public Round getRound() { return this; }

    /**
     * Returns the game of which the round is part
     * @return the game of which the round is part
     */
    public Game getGame() {
        return game;
    }

    /**
     * Returns the current round state
     * @return the current round state
     */
    public int getRoundState(){
        return roundState;
    }

    /**
     * Sets the new round state
     * @param state new round state
     */
    public void setRoundState(int state){
        this.roundState = state;
    }

    /**
     * Returns the previous round state
     * @return the previous round state
     */
    public int getPreviousState() {
        return previousState;
    }

    /**
     * Sets the previous state of the round
     * @param previousState previous state of the round
     */
    public void setPreviousState(int previousState) {
        this.previousState = previousState;
    }

    /**
     * Returns the order of playing which has to be followed by the players in this turn
     * @return order of playing of this turn
     */
    public int[] getPlayerOrder(){
        return this.playerOrder.clone();
    }

    /**
     * Returns the index in the playing order of the player on turn
     * @return the index in the playing order of the player on turn
     */
    public int getIndexOfPlayerOnTurn() {
        return indexOfPlayerOnTurn;
    }

    /**
     * Sets the index in the playing order of the player on turn
     * @param index index in the playing order of the player on turn
     */
    public void setIndexOfPlayerOnTurn(int index) {
        if(index >= 0 && index < game.getNumberOfPlayers())
            indexOfPlayerOnTurn = index;
    }

    /**
     * Returns an array containing the number of students moved by every player in this round
     * @return an array containing the number of students moved by every player in this round
     */
    public int[] getMovesCounter() {
        return movesCounter;
    }

    /**
     * Sets the number of students moved by the player in this round
     * @param playerId identifier of the player whose moves has to be set
     * @param moves number of students moved by the player with the given identifier
     */
    public void setMovesCounter(int playerId, int moves) {
        movesCounter[playerId] = moves;
    }

    /**
     * Returns all the assistants played until this moment
     * @return all the assistants played until this moment
     */
    public PlayedAssistant[] getPlayedAssistants(){
        return playedAssistants;
    }

    /**
     * Sets the attribute which says whether the player on turn has played tan character card or not
     * @param alreadyPlayedCharacter true if the character has been played, false otherwise
     */
    public void setAlreadyPlayedCharacter(boolean alreadyPlayedCharacter) {
        this.alreadyPlayedCharacter = alreadyPlayedCharacter;
    }

    /**
     * Returns true if the last message moves a student from a schoolboard to an island, false otherwise
     * @return true if the last message moves a student from a schoolboard to an island
     */
    public boolean isIslandMessage() {
        return islandMessage;
    }

    /**
     * Returns the identifier of the player on turn at this moment
     * @return the identifier of the player on turn at this moment
     */
    public int getPlayerOnTurn() { return playerOrder[indexOfPlayerOnTurn]; }

    /*-----------------------------------------------------------------------------------------\
    |                                                                                          |
    |                       METHODS FOR CHECKING THE ALLOWANCE OF THE MOVE                     |
    |                                                                                          |
    \-----------------------------------------------------------------------------------------*/

    /**
     * Checks if the player who wants to make the move is the player on turn
     * @param playerId identifier of player that want to make the move
     * @throws PlayerNotOnTurnException if the player who tries to play is not the player who has to move
     */
    public void checkPlayerOnTurn(int playerId) throws PlayerNotOnTurnException {
        if(playerOrder[indexOfPlayerOnTurn] != playerId) {
            setPlayerMessageCli(playerId, "You are not the current player");
            setPlayerMessageGui(playerId, "You are not the current player");
            /* It may be interesting to add a way for notifying only the player who made the bad move */
            game.sendGame();
            throw new PlayerNotOnTurnException();
        }
    }

    /**
     * Checks if it is possible to do the specific move inside the current round state
     * @param methodId identifier of the method which has been called from the message sent by the player
     * @throws InvalidMethodException if the method cannot be executed inside the current round state
     */
    public void checkStatusAndMethod(int methodId) throws InvalidMethodException {
        if (methodId != roundState) throw new InvalidMethodException();
    }

    /**
     * Checks if the player has already made all the possible movements of students from the entrance
     * @param playerId identifier of the player for which is asked the check
     * @throws TooManyMovesException if the player has done 3 or more movements of students (4 or more if the match is played by 3 players)
     */
    public void checkNumberOfMoves(int playerId) throws TooManyMovesException {
        if((movesCounter[playerId] > 3 && game.getNumberOfPlayers() != 3) || (movesCounter[playerId] > 4 && game.getNumberOfPlayers() == 3)) throw new TooManyMovesException();
    }

    /*-----------------------------------------------------------------------------------------\
    |                                                                                          |
    |    METHODS FOR CREATING AND SETTING THE MESSAGES WHICH HAS TO BE SHOWN TO THE CLIENTS    |
    |                                                                                          |
    \-----------------------------------------------------------------------------------------*/

    /**
     * Creates a specific command line message on the base of the round state
     * @return the command line message related to the current round state
     */
    public String getStateMessageCli() {
        String message = null;
        if (roundState == 0) message = "Select an assistant";
        else if (roundState == 1) message = "Make your move:\n1 : Move a student from entrance to table\n2 : Move a student from entrance to an island";
        else if (roundState == 2) message = "Mother nature position: " + game.getGameTable().getMotherNaturePosition() + "\nSelect an island where mother nature has to move: ";
        else if (roundState == 3) message = "Select a cloud";
        // Maybe some more messages can be added for other states
        return message;
    }

    /**
     * Creates a specific message on the base of the round state for the graphic use interface
     * @return the message related to the current round state the graphic use interface
     */
    public String getStateMessageGui() {
        String message = null;
        if (roundState == 0) message = "Select an assistant";
        else if (roundState == 1) message = "Move a student from entrance to table or from entrance to an island";
        else if (roundState == 2) message = "Select an island where mother nature has to move";
        else if (roundState == 3) message = "Select a cloud";
        // Maybe some more messages can be added for other states
        return message;
    }

    /**
     * Sets the new player message which will be shown on the command line
     * @param playerId identifier of the player who will read the message
     * @param message message which will be shown on the command line
     */
    public void setPlayerMessageCli(int playerId, String message) {
        game.getPlayer(playerId).setPlayerMessageCli(message);
    }

    /**
     * Sets the new player message which will be shown on the graphic user interface
     * @param playerId identifier of the player who will read the message
     * @param message message which will be shown on the graphic user interface
     */
    public void setPlayerMessageGui(int playerId, String message) {
        game.getPlayer(playerId).setPlayerMessageGui(message);
    }

    /**
     * Sets the message which has to be sent to the players.
     * Sets a specific message for the player under interest and sets a different message for the opponents
     * @param playerId identifier of the player who will read a different message from the others
     * @param singlePlayerCliMessage message which will be shown on the command line of the player with the given playerId
     * @param singlePlayerGuiMessage message which will be shown on the graphic user interface of the player with the given playerId
     * @param otherPlayersCliMessage message which will be shown on the command line of the other players
     * @param otherPlayersGuiMessage message which will be shown on the graphic user interface of the other players
     */
    public void setDifferentMessagesToPlayers(int playerId, String singlePlayerCliMessage, String singlePlayerGuiMessage, String otherPlayersCliMessage, String otherPlayersGuiMessage) {
        setPlayerMessageCli(playerId, singlePlayerCliMessage);
        setPlayerMessageGui(playerId, singlePlayerGuiMessage);
        for (int i = 0; (i < game.getNumberOfPlayers()) && i != playerId; i++) {
            setPlayerMessageCli(i, otherPlayersCliMessage);
            setPlayerMessageGui(i, otherPlayersGuiMessage);
        }
    }

    /**
     * Sets the message which has to be sent to the players.
     * Sets a specific message for the player which has to play and sets a message which imposes to wait for the opponents
     * @param playerId identifier of the player who will read the message
     * @param messageCli message which will be shown on the command line
     * @param messageGui message which will be shown on the graphic user interface
     */
    public void setMessageToAPlayerAndWaitingMessageForOthers(int playerId, String messageCli, String messageGui) {
        setDifferentMessagesToPlayers(playerId, messageCli, messageGui, "The player on turn is " + game.getPlayer(playerId).getNickname(), "The player on turn is " + game.getPlayer(playerId).getNickname());
    }

    /*-----------------------------------------------------------------------------------------\
    |                                                                                          |
    |                 METHODS FOR IDENTIFYING THE DIFFERENT PHASES OF A ROUND                  |
    |                                                                                          |
    \-----------------------------------------------------------------------------------------*/

    /**
     * Returns whether the pianification phase is ended or not
     * @return true, if the pianification phase is ended; false, otherwise
     */
    public boolean isPianificationPhaseEnded() {
        if (roundState == 0)
            if (indexOfPlayerOnTurn == game.getNumberOfPlayers() - 1) return true;
        return false;
    }

    /**
     * Returns whether the action phase is ended or not
     * @return true, if the action phase is ended; false, otherwise
     */
    public boolean isActionPhaseEnded() {
        if (roundState == 3 && (indexOfPlayerOnTurn == game.getNumberOfPlayers() - 1)) return true;
        return false;
    }

    /**
     * Returns whether it is time to move another student or not
     * @return true, if it is time to move another student; false, otherwise
     */
    public boolean isTimeToChooseTheNextStudent() {
        if((roundState == 1 && movesCounter[playerOrder[indexOfPlayerOnTurn]] < 3 && game.getNumberOfPlayers() != 3) || (roundState == 1 && movesCounter[playerOrder[indexOfPlayerOnTurn]] < 4 && game.getNumberOfPlayers() == 3))
            return true;
        return false;
    }

    /**
     * Returns whether it is time to move mother nature or not
     * @return true, if it is time to move mother nature; false, otherwise
     */
    public boolean isTimeToMoveMotherNature() {
        if ((roundState == 1 && 3 <= movesCounter[playerOrder[indexOfPlayerOnTurn]] && game.getNumberOfPlayers() != 3) || (roundState == 1 && 4 <= movesCounter[playerOrder[indexOfPlayerOnTurn]] && game.getNumberOfPlayers() == 3))
            return true;
        return false;
    }

    /**
     * Returns whether it is time to choose a cloud or not
     * @return true, if it is time to choose a cloud; false, otherwise
     */
    public boolean isTimeToChooseACloud() {
        if (roundState == 2) return true;
        return false;
    }

    /**
     * @return true if a cloud has been chosen, false otherwise
     */
    public boolean cloudHasBeenChosen() {
        if (roundState == 3) return true;
        return false;
    }

    /**
     * @return if the game is ended
     */
    public boolean isTheGameEnded() {
        if(roundState == 100) return true;
        return false;
    }

    /**
     * Calculates the new pianification phase order based on the last played assistants
     */
    public void setPianificationPhaseOrder() {
        int minimumAssistantValue = 11;
        int nextTurnFirstPlayer = 0;
        for(int i = 0; i < playedAssistants.length; i++) {
            if(playedAssistants[i].getAssistant().getCardValue() < minimumAssistantValue) {
                minimumAssistantValue = playedAssistants[i].getAssistant().getCardValue();
                nextTurnFirstPlayer = playedAssistants[i].playerIndex;
            }
        }
        playerOrder[0] = nextTurnFirstPlayer;

        for(int i = 1; i < playedAssistants.length; i++)
            playerOrder[i] = (playerOrder[i - 1] + 1) % playedAssistants.length;
    }

    /**
     * Calculates the action phase based on the new played assistants
     */
    public void setActionPhaseOrder() {
        PlayedAssistant[] playedAssistantsOrdered = new PlayedAssistant[playedAssistants.length];
        for (int i = 0; i < playedAssistants.length; i++) {
            int j = 0;
            while (j < i && playedAssistants[i].getAssistant().getCardValue() >= playedAssistantsOrdered[j].getAssistant().getCardValue())
                j++;
            int k = i;
            while(k > j) {
                playedAssistantsOrdered[k] = playedAssistantsOrdered[k - 1];
                k--;
            }
            playedAssistantsOrdered[j] = playedAssistants[i];
        }
        for (int i = 0; i < game.getNumberOfPlayers(); i++)
            playerOrder[i] = playedAssistantsOrdered[i].getPlayerIndex();
    }

    /**
     * Sets a new Pianification phase and starts a new Round
     */
    public void switchToPianificationPhase() {
        setPianificationPhaseOrder();
        game.startRound(playerOrder);
    }

    /**
     * Creates a new Action phase
     */
    public void switchToActionPhase() {
        setActionPhaseOrder();
        previousState = 0;
        roundState = 1;
        indexOfPlayerOnTurn = 0;
    }

    /**
     * Calculates who is the next player. If the player turn is over it changes the next player on turn and set the round state to the first possible action,
     * otherwise change the round state to the next permitted action
     */
    public void calculateNextPlayer() {
        boolean roundEnded = false;
        if (isTheGameEnded()) {
            game.sendGame();
        } else if (isPianificationPhaseEnded()) {
            switchToActionPhase();
        } else if (isTimeToChooseTheNextStudent()) {
            previousState = 1;
        } else if (isActionPhaseEnded()) {
            roundEnded = true;
            switchToPianificationPhase();
            if (game.getPlayer(playerOrder[indexOfPlayerOnTurn]).getAssistants().size() == 0) {
                roundState = 100;
                checkEndgameAndSetTheWinner();
                game.sendGame();
            }
        } else if (isTimeToMoveMotherNature()) {
            previousState = 1;
            roundState = 2;
        } else if (isTimeToChooseACloud()) {
            previousState = 2;
            roundState = 3;
        } else if (cloudHasBeenChosen()) {
            previousState = 3;
            roundState = 1;
            indexOfPlayerOnTurn++;
            alreadyPlayedCharacter = false;
        } else {
            if (indexOfPlayerOnTurn + 1 < game.getNumberOfPlayers()){
                indexOfPlayerOnTurn++;
            }
        }
        if (!roundEnded) {
            setMessageToAPlayerAndWaitingMessageForOthers(playerOrder[indexOfPlayerOnTurn], getStateMessageCli(), getStateMessageGui());
            game.sendGame();
        } else {
            getGame().setGameMessage(new RoundEndedMessage(getGame(), getPlayerOnTurn()));
        }
    }

    public boolean assistantNoChoice(List<Assistant> outer, List<Assistant> inner) {
        if (outer.size() < inner.size())
            return false;
        return outer.containsAll(inner);
    }

    /**
     * Removes an assistant from a specific player because he choose to play that assistant
     * @param playerId identifier of the player who makes the move
     * @param assistantId identifier of the chosen assistant
     * @throws InvalidIndexException if the chosen assistant cannot be played because someone else already played it
     */
    public void removeAssistant(int playerId, int assistantId) throws InvalidIndexException {
        Assistant assistantToPlay = game.getPlayer(playerId).getAssistant(assistantId);

        for (int i = 0; i < playedAssistants.length; i++) {
            if (alreadyPlayedAssistants[i] && i != playerId) {
                if (assistantToPlay.equals(playedAssistants[i].getAssistant())) {
                    if (!assistantNoChoice(playedAssistantsPF, game.getPlayer(playerId).getAssistants())) throw new InvalidIndexException("Someone has already choose that assistant. Select a different one");
                }
            }
        }
        getGame().getPlayer(playerId).setPlayerMessageCli("Assistant played");
        getGame().getPlayer(playerId).setPlayerMessageGui("Assistant played");
        assistantToPlay = game.getPlayer(playerId).removeAssistant(assistantId);
        playedAssistants[playerId] = new PlayedAssistant(playerId, assistantToPlay);
        alreadyPlayedAssistants[playerId] = true;
        playedAssistantsPF.add(assistantToPlay);
    }

    /**
     * PLays the selected assistant and control if is it possible. Otherwise, it returns an error message
     * @param playerId
     * @param assistantId
     */
    public void playAssistant(int playerId, int assistantId) {
        try {
            checkPlayerOnTurn(playerId);
            checkStatusAndMethod(0);
            removeAssistant(playerId, assistantId);
            getGame().setGameMessage(new AssistantPlayedMessage(getGame(), playerId));
            calculateNextPlayer();
        } catch (PlayerNotOnTurnException e) {
        } catch (InvalidMethodException e) {
            setPlayerMessageCli(playerId, "You cannot play any assistant now\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "You cannot play any assistant now\n" + getStateMessageGui());
            getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
            game.sendGame();
        } catch (IndexOutOfBoundsException e) {
            setPlayerMessageCli(playerId,"You can't choose that assistant\n" + getStateMessageCli());
            setPlayerMessageGui(playerId,"You can't choose that assistant\n" + getStateMessageGui());
            getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
            game.sendGame();
        } catch (InvalidIndexException e) {
            setPlayerMessageCli(playerId, e.getMessage() + "\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, e.getMessage() + "\n" + getStateMessageGui());
            getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
            game.sendGame();
        }
    }

    /**
     * Makes the add student on island move for the specific player. Otherwise, it returns an error message if is not possible
     * @param playerId
     * @param studentIndex
     * @param islandIndex
     */
    public void addStudentOnIsland(int playerId, int studentIndex, int islandIndex) {
        islandMessage = true;
        try {
            checkPlayerOnTurn(playerId);
            checkStatusAndMethod(1);
            checkNumberOfMoves(playerId);
            game.getPlayer(playerId).moveStudentOnIsland(studentIndex, islandIndex);
            movesCounter[playerId]++;
            getGame().setGameMessage(new StudentMovedFromEntranceToIslandMessage(getGame(), playerId, studentIndex, islandIndex));
            calculateNextPlayer();
        } catch (PlayerNotOnTurnException e) {

        } catch (InvalidMethodException e) {
            setPlayerMessageCli(playerId, "You cannot move students now\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "You cannot move students now\n" + getStateMessageGui());
            getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
            game.sendGame();
        } catch (TooManyMovesException e) {
            setPlayerMessageCli(playerId, "You can move no more students\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "You can move no more students\n" + getStateMessageGui());
            getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
            game.sendGame();
        } catch (InvalidIndexException e) {
            setPlayerMessageCli(playerId, e.getMessage() + getStateMessageCli());
            setPlayerMessageGui(playerId, e.getMessage() + getStateMessageGui());
            getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
            game.sendGame();
        }
    }

    /**
     * Does the add student on table move for the specific player. Otherwise, it returns an error message if is not possible
     * @param playerId
     * @param studentIndex
     */
    public void addStudentOnTable(int playerId, int studentIndex) {
        islandMessage = false;
        Student[] entrance;
        try {
            entrance = getGame().getGameTable().getSchoolBoards()[playerId].getStudentsFromEntrance();
            checkPlayerOnTurn(playerId);
            checkStatusAndMethod(1);
            checkNumberOfMoves(playerId);
            game.getPlayer(playerId).moveStudentOnTable(studentIndex);
            PawnColor color = entrance[studentIndex].getColor();
            if (game instanceof ExpertGame){
                if (game.getGameTable().getSchoolBoards()[playerId].getNumberOfStudentsOnTable(color) % 3 == 0 && game.getGameTable().getSchoolBoards()[playerId].getNumberOfStudentsOnTable(color) != 0) {
                    ((ExpertGame)game).addCoinToAPlayer(playerId);
                    ((ExpertGame) game).removeCoinFromTheTable();
                }
            }
            movesCounter[playerId]++;
            game.getGameTable().moveProfessorToTheRightPosition(color);
            getGame().setGameMessage(new StudentMovedFromEntranceToTableMessage(getGame(), playerId, color, getGame().getGameTable().getSchoolBoards()[playerId].getNumberOfStudentsOnTable(color) - 1, studentIndex));
            calculateNextPlayer();
        } catch (PlayerNotOnTurnException e) {

        } catch (InvalidMethodException e) {
            setPlayerMessageCli(playerId, "You cannot move students now\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "You cannot move students now\n" + getStateMessageGui());
            getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
            game.sendGame();
        } catch (TooManyMovesException e) {
            setPlayerMessageCli(playerId, "You can move no more students\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "You can move no more students\n" + getStateMessageGui());
            getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
            game.sendGame();
        } catch (FullTableException e) {
            setPlayerMessageCli(playerId, "You can't move that student, his table has no more free seats\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "You can't move that student, his table has no more free seats\n" + getStateMessageGui());
            getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
            game.sendGame();
        } catch (NotEnoughCoins e) {
            setPlayerMessageCli(playerId, "You can't take a coin from a table\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "You can't take a coin from a table\n" + getStateMessageGui());
            getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
            game.sendGame();
        } catch (InvalidIndexException e) {
            setPlayerMessageCli(playerId, e.getMessage() + "\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, e.getMessage() + "\n" + getStateMessageGui());
            getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
            game.sendGame();
        }
    }

    /**
     * Controls if the new requested position for mother nature is possible made by last assistant played
     * @param assistant
     * @param islandIndex
     * @return
     */
    public boolean isANewAllowedPositionForMotherNature(Assistant assistant, int islandIndex) {
        int motherNaturePosition = game.getGameTable().getMotherNaturePosition();
        int numberOfIslands = game.getGameTable().getNumberOfIslands();
        if(islandIndex == motherNaturePosition && numberOfIslands > assistant.getMotherNatureMoves())
            return false;
        if (islandIndex < motherNaturePosition) {
            if ((numberOfIslands - motherNaturePosition + islandIndex) <= assistant.getMotherNatureMoves())
                return true;
            else
                return false;
        } else {
            if ((islandIndex - motherNaturePosition) <= assistant.getMotherNatureMoves())
                return true;
            else
                return false;
        }
    }

    /**
     * Checks if someone has won or if the match ends with a draw. The method sets the parameters victory, draw, winner, inside game, according to the state of the match
     */
    public void checkEndgameAndSetTheWinner() {
        List<TowerColor> possibleWinner = game.getGameTable().teamWithLessTowersOnSchoolboards();
        if (possibleWinner.size() > 1) {
            possibleWinner = game.getGameTable().teamWithMoreProfessors(possibleWinner);
            if (possibleWinner.size() > 1) {
                game.setDraw();
            } else {
                game.setVictory();
                game.setWinner(possibleWinner.get(0));
            }
        } else {
            game.setVictory();
            game.setWinner(possibleWinner.get(0));
        }
    }

    /**
     * Changes mother nature position, calculate the influences of the players on the island and puts or changes the tower on the island
     * @param playerId player ID of the player which want to make the move
     * @param islandIndex index of the island on which the player wants to move mothter nature
     */
    public void changeMotherNaturePosition (int playerId, int islandIndex) {
        try {
            checkPlayerOnTurn(playerId);
            checkStatusAndMethod(2);
            try {
                int i = 0;
                while (i < playedAssistants.length) {
                    if (playedAssistants[i].playerIndex == playerId) break;
                    i++;
                }
                if (!isANewAllowedPositionForMotherNature(playedAssistants[i].getAssistant(), islandIndex)) throw new TooFarIslandException();
                game.getGameTable().changeMotherNaturePosition(islandIndex);
                int[] influenceValues = game.getGameTable().calculateInfluenceValuesGivenByStudents();
                for (i = 0; i < influenceValues.length; i++)
                    influenceValues[i] += game.getGameTable().calculateInfluenceValuesGivenByTowers()[i];
                try {
                    game.getGameTable().putTowerOrChangeColorIfNecessary(influenceValues);
                } catch (NoMoreTowersException e) {
                    game.setVictory();
                    game.setWinner(e.getEmptySchoolboardColor());
                    roundState = 100;
                } catch (ThreeOrLessIslandException e) {
                    checkEndgameAndSetTheWinner();
                }
                if(game.isGameEnded()) {
                    roundState = 100;
                    getGame().setGameMessage(new GameMessage(getGame(), playerId));
                } else {
                    getGame().setGameMessage(new MotherNatureMovedMessage(getGame(), playerId, islandIndex));
                }
                calculateNextPlayer();
            } catch (TooFarIslandException e) {
                setPlayerMessageCli(playerId, "You cannot put mother nature in the chosen island\n" + getStateMessageCli());
                setPlayerMessageGui(playerId, "You cannot put mother nature in the chosen island\n" + getStateMessageGui());
                getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
                game.sendGame();
            } catch (InvalidIndexException e) {
                setPlayerMessageCli(playerId, "You cannot put mother nature in the chosen island, it does not exist\n" + getStateMessageCli());
                setPlayerMessageGui(playerId, "You cannot put mother nature in the chosen island, it does not exist\n" + getStateMessageGui());
                getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
                game.sendGame();
            }
        } catch (PlayerNotOnTurnException e) {

        } catch (InvalidMethodException e) {
            setPlayerMessageCli(playerId, "You cannot move mother nature now");
            setPlayerMessageGui(playerId, "You cannot move mother nature now");
            getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
            game.sendGame();
        }
    }

    /**
     * Takes students from chosen cloud and add these students to the player on his entrance
     * @param playerId
     * @param cloudIndex
     */
    public void getStudentsFromCloud(int playerId, int cloudIndex) {
        try {
            checkPlayerOnTurn(playerId);
            checkStatusAndMethod(3);
            game.getPlayer(playerId).takeStudentsFromCloud(cloudIndex);
            getGame().setGameMessage(new StudentMovedFromCloudToEntranceMessage(getGame(), playerId, cloudIndex));
            calculateNextPlayer();
        } catch (PlayerNotOnTurnException e) {

        } catch (InvalidMethodException e) {
            setPlayerMessageCli(playerId, "You cannot get students from cloud now\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "You cannot get students from cloud now\n" + getStateMessageGui());
            getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
            game.sendGame();
        } catch (InvalidIndexException e) {
            setPlayerMessageCli(playerId, e.getMessage() + "\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, e.getMessage() + "\n" + getStateMessageGui());
            getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
            game.sendGame();
        } catch (EmptyCloudException e)  {
            setPlayerMessageCli(playerId, "The chosen cloud is empty. Chose another one!");
            setPlayerMessageGui(playerId, "The chosen cloud is empty. Chose another one!");
            getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
            game.sendGame();
        }
    }

    /**
     * Activates the effect of the chosen card for the player that has requested it. It controls if this is possibile, otherwise it sends an error
     * @param playerId
     * @param indexCard
     */
    public void activateEffect(int playerId, int indexCard) {
        try {
            checkPlayerOnTurn(playerId);
            if (roundState <= 0 || roundState >= 4) throw new InvalidMethodException();
            if (alreadyPlayedCharacter) throw new AlreadyPlayedCharcaterException();
            getGame().activateEffect(playerId, indexCard);
            //if (((ExpertGame) game).getCharacter(indexCard) instanceof InnKeeper) ((ExpertGame) game).getCharacter(indexCard).deactivateEffect(true);
            alreadyPlayedCharacter = true;
        } catch (PlayerNotOnTurnException e) {

        } catch (AlreadyPlayedCharcaterException e) {
            setPlayerMessageCli(playerId, "You already played a character\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "You already played a character\n" + getStateMessageGui());
            getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
            getGame().sendGame();
        } catch (InvalidMethodException e) {
            setPlayerMessageCli(playerId, "You can't play a character during the pianification phase\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "You can't play a character during the pianification phase\n" + getStateMessageGui());
            getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
            getGame().sendGame();
        } catch (EffectCannotBeActivatedException e) {
            setPlayerMessageCli(playerId, e.getMessage() + "\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, e.getMessage() + "\n" + getStateMessageGui());
            getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
            getGame().sendGame();
        } catch (NotEnoughCoins e) {
            setPlayerMessageCli(playerId, "Not enougth coin to play the character\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "Not enougth coin to play the character\n" + getStateMessageGui());
            getGame().setGameMessage(new ErrorMessage(getGame(), playerId));
            getGame().sendGame();
        }
    }

    /**
     * Activates the effect for the character selected
     * @param playerId
     * @param parameter
     * @throws InvalidIndexException
     */
    public void doYourJob(int playerId, int parameter) throws InvalidIndexException { }

    /**
     * @return if the player has already played a character
     */
    public boolean getAlreadyPLayedCharacter() {
        return alreadyPlayedCharacter;
    }
}