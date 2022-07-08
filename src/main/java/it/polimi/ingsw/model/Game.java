package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.EffectCannotBeActivatedException;
import it.polimi.ingsw.model.exception.EmptyBagException;
import it.polimi.ingsw.model.exception.InvalidIndexException;
import it.polimi.ingsw.model.exception.NotEnoughCoins;
import it.polimi.ingsw.observer.Observable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game extends Observable<Game> implements Serializable {

    private final int numberOfPlayers;
    private GameMode gameMode;
    private GameTable gameTable;
    private List<Assistant>[] assistants;
    private List<Wizard> alreadyChosenWizards;
    private Player[] players;
    private Round round;
    private boolean victory;
    private boolean draw;
    private TowerColor winner;

    /**
     *
     * @param numberOfPlayers
     */
    public Game(int numberOfPlayers) { this.numberOfPlayers = numberOfPlayers; }

    /**
     *
     * @param numberOfPlayers
     * @param nicknames
     */
    public Game(int numberOfPlayers, List<String> nicknames) {
        this.numberOfPlayers = numberOfPlayers;
        gameMode = GameMode.NORMAL;
        gameTable = createGameTable(numberOfPlayers);

        alreadyChosenWizards = new ArrayList<>();

        assistants = new ArrayList[numberOfPlayers];
        List<Assistant> assistantsList = createAssistants();
        for (int i = 0; i < numberOfPlayers; i++) {
            assistants[i] = new ArrayList<>();
            assistants[i].addAll(assistantsList);
        }

        players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] = new Player(nicknames.get(i), i, assistantsList);
            players[i].addSchoolBoard(gameTable.getSchoolBoards()[i]);
            players[i].addGameTable(gameTable);
        }

        round = startRound();

        victory = false;
        draw = false;
        winner = null;
    }

    /**
     * returns the gameMode attribute
     * @return
     */
    public GameMode getGameMode() { return gameMode; }

    /**
     * returns the numberOfplayers attribute
     * @return
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * returns the gameTable attribute
     * @return
     */
    public GameTable getGameTable() {
        return gameTable;
    }

    /**
     * sets the gametable attribute
     * @param gameTable
     */
    public void setGameTable(GameTable gameTable) {
        this.gameTable = gameTable;
    }

    /**
     * returns the players[playerId attribute] with playerId passed by parameter
     * @param playerId
     * @return
     */
    public Player getPlayer(int playerId) {
        try {
            if (playerId < 0 || playerId >= numberOfPlayers) throw new InvalidIndexException("This player doesn't exit");
        } catch (InvalidIndexException e) {
            return null;
        }
        return this.players[playerId];
    }

    /**
     * returns the round attribute
     * @return
     */
    public Round getRound() {
        return round;
    }

    /**
     * sets the round attribute with the value passed as a parameter
     * @param round
     */
    public void setRound(Round round) {
        this.round = round;
    }

    /**
     * returns the victory attribute
     * @return
     */
    public boolean isVictory() {
        return victory;
    }

    /**
     * returns the draw attribute
     * @return
     */
    public boolean isDraw() {
        return draw;
    }

    /**
     * sets the victory attribute with the value passed as a parameter
     */
    public void setVictory(){
        victory = true;
    }

    /**
     * sets the draw attribute with the value passed as a parameter
     */
    public void setDraw(){
        draw = true;
    }

    /**
     * returns the winner attribute
     * @return
     */
    public TowerColor getWinner() {
        return winner;
    }

    /**
     * sets the winner attribute with the value passed as a parameter
     * @param winner
     */
    public void setWinner(TowerColor winner) {
        this.winner = winner;
    }

    /**
     * creates a certain number of school boards with different features based on the number of players in the game
     * @param numberOfPlayers
     * @return
     */
    public GameTable createGameTable(int numberOfPlayers) {
        SchoolBoard[] schoolBoards = new SchoolBoard[numberOfPlayers];

        int numberOfStudentsOnEntrance = 7;
        int numberOfTowers = 8;
        if (numberOfPlayers == 3) {
            numberOfStudentsOnEntrance = 9;
            numberOfTowers = 6;
        } else {
            numberOfStudentsOnEntrance = 7;
            numberOfTowers = 8;
        }

        switch (numberOfPlayers) {
            case 2:
                schoolBoards[0] = new SchoolBoard(numberOfStudentsOnEntrance, TowerColor.WHITE, numberOfTowers);
                schoolBoards[1] = new SchoolBoard(numberOfStudentsOnEntrance, TowerColor.BLACK, numberOfTowers);
                break;
            case 3:
                schoolBoards[0] = new SchoolBoard(numberOfStudentsOnEntrance, TowerColor.WHITE, numberOfTowers);
                schoolBoards[1] = new SchoolBoard(numberOfStudentsOnEntrance, TowerColor.BLACK, numberOfTowers);
                schoolBoards[2] = new SchoolBoard(numberOfStudentsOnEntrance, TowerColor.GREY, numberOfTowers);
                break;
            case 4:
                schoolBoards[0] = new SchoolBoard(numberOfStudentsOnEntrance, TowerColor.WHITE, numberOfTowers);
                schoolBoards[1] = new SchoolBoard(numberOfStudentsOnEntrance, TowerColor.BLACK, numberOfTowers);
                schoolBoards[2] = new SchoolBoard(numberOfStudentsOnEntrance, TowerColor.WHITE, 0);
                schoolBoards[3] = new SchoolBoard(numberOfStudentsOnEntrance, TowerColor.BLACK, 0);
                break;
        }
        Bag bag = createBag();
        return new GameTable(numberOfPlayers, schoolBoards, bag);
    }

    /**
     * returns a new bag
     * @return
     */
    public Bag createBag() {
        return new Bag();
    }

    /**
     * returns a List<Assistant> containing all the assistants
     * @return
     */
    public List<Assistant> createAssistants(){
        List<Assistant> l = new ArrayList<>();

        String assistants = AssistantStorage.getAssistants();
        String[] assistantsParsed = assistants.split(";");
        for (String s : assistantsParsed) {
            String[] assistant = s.split(",");
            int cardValue = Integer.parseInt(assistant[0]);
            int motherNatureMoves = Integer.parseInt(assistant[1]);
            l.add(new Assistant(cardValue, motherNatureMoves));
        }
        return l;
    }

    /**
     *
     * @param position
     * @return
     */
    public boolean isAValidPositionForMotherNature(int position) {
        if (0 <= position && position < gameTable.getIslands().size()) return true;
        return false;
    }

    /**
     * starts the first round
     * @return
     */
    public Round startRound() {
        round = new Round(this);
        try {
            gameTable.addStudentsOnClouds();
        } catch (EmptyBagException e) {
            round = new Round(this);
        }
        return round;
    }

    /**
     * starts a new round with the playerOrder passed by parameter. If there is just one remaining assistant the next round will be the last round
     * @param playerOrder
     * @return
     */
    public Round startRound(int[] playerOrder) {
        Round round = null;
        if (getPlayer(0).getAssistants().size() == 1)
            round = new LastRound(this, playerOrder, false);
        else
            round = new Round(this, playerOrder);
        try {
            gameTable.addStudentsOnClouds();
        } catch (EmptyBagException e) {
            round = new LastRound(this, playerOrder, true);
        }
        this.round = round;
        sendGame();
        return round;
    }

    /**
     * Chech if the game is ended or not
     * @return true, if the game has to end, due to different causes; false, if the game must continue
     */
    public boolean isGameEnded() {
        if (isVictory() || isDraw()) return true;
        return false;
    }
    /**
     * Does the operations related to the end of the game
     */
    public void endTheMatch() {

    }

    /**
     *
     * @param playerIndex
     * @param indexCard
     * @throws EffectCannotBeActivatedException
     * @throws NotEnoughCoins
     */
    public void activateEffect(int playerIndex, int indexCard) throws EffectCannotBeActivatedException, NotEnoughCoins {
    }

    /**
     * calls the method notify passing as a parameter this game
     */
    public void sendGame() {
        notify(this);
    }

    /**
     * returns the players nicknames
     * @return
     */
    public String[] getPlayersNicknames() {
        String[] nicknames = new String[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++) nicknames[i] = getPlayer(i).getNickname();
        return nicknames;
    }

    /**
     * returns the assistant played by the player identified by playerIndex, passed by parameter
     * @param playerIndex
     * @return
     */
    public List<Assistant> getPlayerAssistant(int playerIndex) {
        return players[playerIndex].getAssistants();
    }
}