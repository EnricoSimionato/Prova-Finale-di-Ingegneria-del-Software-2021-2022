package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.*;

import java.io.Serializable;
import java.util.*;

public class Round implements Serializable {

    private Game game;
    protected int roundState;
    private int previousState;
    private int[] movesCounter;
    private int indexOfPlayerOnTurn;
    private int[] playerOrder;
    private PlayedAssistant[] playedAssistants;
    private boolean[] alreadyPlayedAssistants;
    private List<Assistant> playedAssistantsPF;
    private boolean alreadyPlayedCharacter;
    private boolean islandMessage = false;

    public Round() { }

    public Round(Game game) {
        indexOfPlayerOnTurn = 0;
        playerOrder = new int[game.getNumberOfPlayers()];
        playerOrder[0] = calculateFirstPlayer(game.getNumberOfPlayers());
        for (int i = 1; i < game.getNumberOfPlayers(); i++) playerOrder[i] = (playerOrder[i - 1] + 1) % game.getNumberOfPlayers();
        roundState = 0;
        previousState = 0;
        movesCounter = new int[game.getNumberOfPlayers()];
        for (int i = 0; i < game.getNumberOfPlayers(); i++)
            movesCounter[i] = 0;
        playedAssistants = new PlayedAssistant[game.getNumberOfPlayers()];
        this.game = game;
        alreadyPlayedCharacter = false;

        alreadyPlayedAssistants = new boolean[game.getNumberOfPlayers()];
        for (int i = 0; i < alreadyPlayedAssistants.length; i++)
            alreadyPlayedAssistants[i] = false;
        playedAssistantsPF = new ArrayList<>();


        setMessageToAPlayerAndWaitingMessageForOthers(playerOrder[0], "Select an assistant", "Select an assistant");
    }

    /**
     * @return if the last move was about the island
     */
    public boolean isIslandMessage() {
        return islandMessage;
    }

    /**
     * @return the player on turn on this moment
     */
    public int getPlayerOnTurn() { return playerOrder[indexOfPlayerOnTurn]; }

    /**
     * Creates a new Round
     * @param game
     * @param playerOrder player order calculated by played Assistant
     */
    public Round(Game game, int[] playerOrder) {
        this(game);
        for(int i = 0; i < game.getNumberOfPlayers(); i++)
            this.playerOrder[i] = playerOrder[i];
        setMessageToAPlayerAndWaitingMessageForOthers(playerOrder[0], "Select an assistant", "Select an assistant");
    }

    /**
     * Returns the instance of itself
     * @return itself
     */
    public Round getRound() { return this; }

    /**
     * Returns the game of which the round is part
     * @return the game of which the round is part
     */
    public Game getGame() {
        return game;
    }

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
         * @return player that had played the assistant
         */
        public int getPlayerIndex() {
            return playerIndex;
        }

        /**
         * @return assistant played
         */
        public Assistant getAssistant() {
            return assistant;
        }
    }

    /**
     * @return all the Assistant played until this moment
     */
    public PlayedAssistant[] getPlayedAssistants(){
        return  playedAssistants;
    }

    /**
     * @return player order on this turn
     */
    public int[] getPlayerOrder(){
        return this.playerOrder.clone();
    }

    /**
     * Controls if the player that want to make the move is the player on turn
     * @param playerId of player that want to make the move
     * @throws PlayerNotOnTurnException
     */
    public void checkPlayerOnTurn(int playerId) throws PlayerNotOnTurnException {
        if(playerOrder[indexOfPlayerOnTurn] != playerId) {
            setPlayerMessageCli(playerId, "You are not the current player");
            game.sendGame();
            throw new PlayerNotOnTurnException();
        }
    }

    /**
     * Set the new round state
     * @param state
     */
    public void setRoundState(int state){
        this.roundState = state;
    }

    /**
     * @return the current round state
     */
    public int getRoundState(){
        return roundState;
    }

    /**
     * @return the previous round state
     */
    public int getPreviousState() {
        return previousState;
    }

    /**
     * Sets the new previous round state
     * @param previousState
     */
    public void setPreviousState(int previousState) {
        this.previousState = previousState;
    }

    /**
     * Returns the number of move made until this specific moment
     * @return
     */
    public int[] getMovesCounter(){
        return movesCounter;
    }

    /**
     * Sets the number of moves made by a specific player
     * @param playerId
     * @param moves
     */
    public void setMovesCounter(int playerId, int moves){
        movesCounter[playerId] = moves;
    }

    /**
     * @return player on turn
     */
    public int getIndexOfPlayerOnTurn(){
        return indexOfPlayerOnTurn;
    }

    /**
     * Sets the player on turn
     * @param index
     */
    public void setIndexOfPlayerOnTurn(int index){
        if(index>=0 && index<game.getNumberOfPlayers())
            indexOfPlayerOnTurn=index;
    }

    /**
     * Sets if the current player have already played a character
     * @param alreadyPlayedCharacter
     */
    public void setAlreadyPlayedCharacter(boolean alreadyPlayedCharacter) {
        this.alreadyPlayedCharacter = alreadyPlayedCharacter;
    }

    /**
     * Calculates random the index of the first player for the first round
     * @param numberOfPlayers
     * @return
     */
    public int calculateFirstPlayer(int numberOfPlayers){
        Random generator = new Random();
        int firstPlayer = generator.nextInt(numberOfPlayers);
        return firstPlayer;
    }

    /**
     * Controls if is it possible the specific move in this specific round state
     * @param methodId
     * @throws InvalidMethodException
     */
    public void checkStatusAndMethod(int methodId) throws InvalidMethodException {
        if (methodId != roundState) throw new InvalidMethodException();
    }

    /**
     * Controls if the player has already made all the possible moves
     * @param playerId
     * @throws TooManyMovesException
     */
    public void checkNumberOfMoves(int playerId) throws TooManyMovesException {
        if((movesCounter[playerId] > 3 && game.getNumberOfPlayers() != 3) || (movesCounter[playerId] > 4 && game.getNumberOfPlayers() == 3)) throw new TooManyMovesException();
    }

    /**
     * Sets the new player message for CLI
     * @param playerId
     * @param message
     */
    public void setPlayerMessageCli(int playerId, String message) {
        game.getPlayer(playerId).setPlayerMessageCli(message);
    }

    /**
     * Sets the new player message for GUI
     * @param playerId
     * @param message
     */
    public void setPlayerMessageGui(int playerId, String message) {
        game.getPlayer(playerId).setPlayerMessageGui(message);
    }


    public void setMessageToAPlayerAndWaitingMessageForOthers(int playerId, String messageCli, String messageGui) {
        setPlayerMessageCli(playerId, messageCli);
        setPlayerMessageGui(playerId, messageGui);
        for (int i = 0; i < game.getNumberOfPlayers(); i++) {
            if (i != playerId) {
                setPlayerMessageCli(i, "The player on turn is " + game.getPlayer(playerId).getNickname());
                setPlayerMessageGui(i, "The player on turn is " + game.getPlayer(playerId).getNickname());
            }
        }
    }

    /**
     * @return the CLI message for the current round state
     */
    public String getStateMessageCli() {
        String message = null;
        if (roundState == 0) message = "Select an assistant";
        else if (roundState == 1) message = "Make your move:\n1 : Move a student from entrance to table\n2 : Move a student from entrance to an island";
        else if (roundState == 2) message = "Mother nature position: " + game.getGameTable().getMotherNaturePosition() + "\nSelect an island where mother nature has to move: ";
        else if (roundState == 3) message = "Select a cloud";
        return message;
    }

    /**
     * @return the GUI message for the current round state
     */
    public String getStateMessageGui() {
        String message = null;
        if (roundState == 0) message = "Select an assistant";
        else if (roundState == 1) message = "Move a student from entrance to table or from entrance to an island";
        else if (roundState == 2) message = "Select an island where mother nature has to move";
        else if (roundState == 3) message = "Select a cloud";
        return message;
    }

    /**
     * @return if the pianification phase is ended
     */
    public boolean isPianificationPhaseEnded() {
        if (roundState == 0) {
            if (indexOfPlayerOnTurn == game.getNumberOfPlayers() - 1) return true;
        }
        return false;
    }

    /**
     * @return true if the action phase is ended, false otherwise
     */
    public boolean isActionPhaseEnded() {
        if (roundState == 3 && (indexOfPlayerOnTurn == game.getNumberOfPlayers() - 1)) return true;
        return false;
    }

    /**
     * @return true if you can move another student from entrance, false otherwise
     */
    public boolean isTimeToChooseTheNextStudent() {
        if((roundState == 1 && movesCounter[playerOrder[indexOfPlayerOnTurn]] < 3 && game.getNumberOfPlayers() != 3) || (roundState == 1 && movesCounter[playerOrder[indexOfPlayerOnTurn]] < 4 && game.getNumberOfPlayers() == 3))
            return true;
        return false;
    }

    /**
     * @return true if you have to move mother nature, false otherwise
     */
    public boolean isTimeToMoveMotherNature() {
        if ((roundState == 1 && 3 <= movesCounter[playerOrder[indexOfPlayerOnTurn]] && game.getNumberOfPlayers() != 3) || (roundState == 1 && 4 <= movesCounter[playerOrder[indexOfPlayerOnTurn]] && game.getNumberOfPlayers() == 3))
            return true;

        return false;
    }

    /**
     * @return true if you can choose a cloud, false otherwise
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
        }
    }

    public boolean assistantNoChoice(List<Assistant> outer, List<Assistant> inner) {
        if (outer.size() < inner.size())
            return false;
        return outer.containsAll(inner);
    }

    /**
     * Removes an assistant from a specific player
     * @param playerId
     * @param assistantPosition
     * @throws InvalidIndexException
     */
    public void removeAssistant(int playerId, int assistantPosition) throws InvalidIndexException {
        Assistant assistantToPlay = game.getPlayer(playerId).getAssistant(assistantPosition);

        for (int i = 0; i < playedAssistants.length; i++) {
            if (alreadyPlayedAssistants[i] && i != playerId) {
                if (assistantToPlay.equals(playedAssistants[i].getAssistant())) {
                    if (!assistantNoChoice(playedAssistantsPF, game.getPlayer(playerId).getAssistants())) throw new InvalidIndexException("Someone has already choose that assistant. Select a different one");
                }
            }
        }
        game.getPlayer(playerId).setPlayerMessageCli("Assistant played");
        game.getPlayer(playerId).setPlayerMessageGui("Assistant played");
        assistantToPlay = game.getPlayer(playerId).removeAssistant(assistantPosition);
        playedAssistants[playerId] = new PlayedAssistant(playerId, assistantToPlay);
        alreadyPlayedAssistants[playerId] = true;
        playedAssistantsPF.add(assistantToPlay);
    }

    /**
     * PLays the selected assistant and control if is it possible. Otherwise, it returns an error message
     * @param playerId
     * @param idAssistant
     */
    public void playAssistant(int playerId, int idAssistant) {
        try {
            checkPlayerOnTurn(playerId);
            checkStatusAndMethod(0);
            removeAssistant(playerId, idAssistant);
            calculateNextPlayer();
        } catch (PlayerNotOnTurnException e) {

        } catch (InvalidMethodException e) {
            setPlayerMessageCli(playerId, "You cannot play any assistant now\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "You cannot play any assistant now\n" + getStateMessageGui());
            game.sendGame();
        } catch (IndexOutOfBoundsException e) {
            setPlayerMessageCli(playerId,"You can't choose that assistant\n" + getStateMessageCli());
            setPlayerMessageGui(playerId,"You can't choose that assistant\n" + getStateMessageGui());
            game.sendGame();
        } catch (InvalidIndexException e) {
            setPlayerMessageCli(playerId, e.getMessage() + "\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, e.getMessage() + "\n" + getStateMessageGui());
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
            calculateNextPlayer();
        } catch (PlayerNotOnTurnException e) {

        } catch (InvalidMethodException e) {
            setPlayerMessageCli(playerId, "You cannot move students now\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "You cannot move students now\n" + getStateMessageGui());
            game.sendGame();
        } catch (TooManyMovesException e) {
            setPlayerMessageCli(playerId, "You can move no more students\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "You can move no more students\n" + getStateMessageGui());
            game.sendGame();
        } catch (InvalidIndexException e) {
            setPlayerMessageCli(playerId, e.getMessage() + getStateMessageCli());
            setPlayerMessageGui(playerId, e.getMessage() + getStateMessageGui());
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
            calculateNextPlayer();
        } catch (PlayerNotOnTurnException e) {

        } catch (InvalidMethodException e) {
            setPlayerMessageCli(playerId, "You cannot move students now\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "You cannot move students now\n" + getStateMessageGui());
            game.sendGame();
        } catch (TooManyMovesException e) {
            setPlayerMessageCli(playerId, "You can move no more students\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "You can move no more students\n" + getStateMessageGui());
            game.sendGame();
        } catch (FullTableException e) {
            setPlayerMessageCli(playerId, "You can't move that student, his table has no more free seats\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "You can't move that student, his table has no more free seats\n" + getStateMessageGui());
            game.sendGame();
        } catch (NotEnoughCoins e) {
            setPlayerMessageCli(playerId, "You can't take a coin from a table\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "You can't take a coin from a table\n" + getStateMessageGui());
            game.sendGame();
        } catch (InvalidIndexException e) {
            setPlayerMessageCli(playerId, e.getMessage() + "\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, e.getMessage() + "\n" + getStateMessageGui());
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
                }
                calculateNextPlayer();
            } catch (TooFarIslandException e) {
                setPlayerMessageCli(playerId, "You cannot put mother nature in the chosen island\n" + getStateMessageCli());
                setPlayerMessageGui(playerId, "You cannot put mother nature in the chosen island\n" + getStateMessageGui());
                game.sendGame();
            } catch (InvalidIndexException e) {
                setPlayerMessageCli(playerId, "You cannot put mother nature in the chosen island, it does not exist\n" + getStateMessageCli());
                setPlayerMessageGui(playerId, "You cannot put mother nature in the chosen island, it does not exist\n" + getStateMessageGui());
                game.sendGame();
            }
        } catch (PlayerNotOnTurnException e) {

        } catch (InvalidMethodException e) {
            setPlayerMessageCli(playerId, "You cannot move mother nature now");
            setPlayerMessageGui(playerId, "You cannot move mother nature now");
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
            calculateNextPlayer();
        } catch (PlayerNotOnTurnException e) {

        } catch (InvalidMethodException e) {
            setPlayerMessageCli(playerId, "You cannot get students from cloud now\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "You cannot get students from cloud now\n" + getStateMessageGui());
            game.sendGame();
        } catch (InvalidIndexException e) {
            setPlayerMessageCli(playerId, e.getMessage() + "\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, e.getMessage() + "\n" + getStateMessageGui());
            game.sendGame();
        } catch (EmptyCloudException e)  {
            setPlayerMessageCli(playerId, "The chosen cloud is empty. Chose another one!");
            setPlayerMessageGui(playerId, "The chosen cloud is empty. Chose another one!");
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
            getGame().sendGame();
        } catch (InvalidMethodException e) {
            setPlayerMessageCli(playerId, "You can't play a character during the pianification phase\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "You can't play a character during the pianification phase\n" + getStateMessageGui());
            getGame().sendGame();
        } catch (EffectCannotBeActivatedException e) {
            setPlayerMessageCli(playerId, e.getMessage() + "\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, e.getMessage() + "\n" + getStateMessageGui());
            getGame().sendGame();
        } catch (NotEnoughCoins e) {
            setPlayerMessageCli(playerId, "Not enougth coin to play the character\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "Not enougth coin to play the character\n" + getStateMessageGui());
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