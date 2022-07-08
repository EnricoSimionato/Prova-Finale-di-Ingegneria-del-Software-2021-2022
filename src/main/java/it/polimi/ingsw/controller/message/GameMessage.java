package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.client.gui.guiControllers.BoardController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Character;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameMessage extends Message implements Serializable {
    private int state;
    private int playerId;
    private String playerMessageCli;
    private String playerMessageGui;
    private GameTable gametable;
    private List<Assistant> assistants;
    private int playerOnTurn;
    private int numberOfPLayers;
    private GameMode gameMode;
    private boolean alreadyPlayedCharacter;
    private SchoolBoard[] schoolBoards;
    private Character[] characters;
    private int[] playersCoins;
    private int tableCoins;
    private String[] playersNicknames;
    private Round.PlayedAssistant[] playedAssistants;
    private boolean postmanActive;
    private boolean victory;
    private boolean draw;
    private TowerColor winner;

    /**
     * updates all the attributes whenever a new gameMessage is created with the model information
     * @param model
     * @param playerId
     */
    public GameMessage(Game model, int playerId) {
        state = model.getRound().getRoundState();
        this.playerId = playerId;
        playerMessageCli = model.getPlayer(playerId).getPlayerMessageCli();
        playerMessageGui = model.getPlayer(playerId).getPlayerMessageGui();
        gametable = model.getGameTable();
        assistants = model.getPlayerAssistant(playerId);
        playerOnTurn = model.getRound().getPlayerOnTurn();
        numberOfPLayers = model.getNumberOfPlayers();
        gameMode = model.getGameMode();
        alreadyPlayedCharacter = model.getRound().getAlreadyPLayedCharacter();
        schoolBoards = model.getGameTable().getSchoolBoards();
        if (gameMode == GameMode.EXPERT) {
            characters = ((ExpertGame) model).getCharacters();
            playersCoins = ((ExpertGame) model).getPlayersCoins();
            tableCoins = ((ExpertGame) model).getCoinsOfTheTable();
        } else characters = null;
        playersNicknames = model.getPlayersNicknames();
        playedAssistants = model.getRound().getPlayedAssistants();
        postmanActive = (model.getRound() instanceof Postman) ? true : false;
        victory = model.isVictory();
        draw = model.isDraw();
        winner = model.getWinner();
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
     * returns the winner attribute
     * @return
     */
    public TowerColor getWinner() {
        return winner;
    }

    /**
     * returns the indexes of the players who win
     * @return
     */
    public List<Integer> getWinnersIndexes() {
        List<Integer> winnersIndexes = new ArrayList<>();
        if(state == 100 && victory) {
            for(int i = 0; i < numberOfPLayers; i++) {
                if (winner.equals(gametable.getSchoolBoards()[i].getTowersColor())) {
                    winnersIndexes.add(i);
                }
            }
        }
        return winnersIndexes;
    }

    /**
     * returns the nicknames of the players who win
     * @return
     */
    public List<String> getWinnersNicknames() {
        List<String> winnersNicknames = new ArrayList<>();
        if(state == 100 && victory) {
            for(int i = 0; i < numberOfPLayers; i++) {
                if (winner.equals(gametable.getSchoolBoards()[i].getTowersColor())) {
                    winnersNicknames.add(playersNicknames[i]);
                }
            }
        }
        return winnersNicknames;
    }

    /**
     * returns the playerNicknames attribute
     * @return
     */
    public String[] getPlayersNicknames() {
        return playersNicknames;
    }

    /**
     * displays the board of the controller passed by parameter
     * @param controller
     */
    public void renderWhatNeeded(BoardController controller) {
        controller.displayBoard();
    }

    /**
     * returns the playedAssistants attribute
     * @return
     */
    public Round.PlayedAssistant[] getPlayedAssistants() {
        return playedAssistants;
    }

    /**
     * returns the characters attribute
     * @return
     */
    public Character[] getCharacters() {
        return characters;
    }

    /**
     * returns the numberOfPlayers attribute
     * @return
     */
    public int getNumberOfPLayers() {
        return numberOfPLayers;
    }

    /**
     * returns the gameTable attribute
     * @return
     */
    public GameTable getGametable() {
        return gametable;
    }

    /**
     * returns the assistants attribute
     * @return
     */
    public List<Assistant> getAssistants() {
        return assistants;
    }

    /**
     * returns the state attribute
     * @return
     */
    public int getState() {
        return state;
    }

    /**
     * returns the playerId attribute
     * @return
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * returns the playerMessageCli attribute
     * @return
     */
    public String getPlayerMessageCli() {
        return playerMessageCli;
    }

    /**
     * returns the playerMessageGui attribute
     * @return
     */
    public String getPlayerMessageGui() {
        return playerMessageGui;
    }

    /**
     * calls the gameTable.getClouds() method
     * @return
     */
    public Cloud[] getClouds() {
        return gametable.getClouds();
    }

    /**
     * sets the playerId attribute with the value passed by parameter
     * @param playerId
     */
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    /**
     * returns the postamActive attribute
     * @return
     */
    public boolean isPostmanActive() {
        return postmanActive;
    }

    /**
     * prints on cli all the default parts of the game such as islands, played assistants,
     * assistants, coins on the table (in expert game mode), charachters (in expert game mode), schoolboards
     */
    public void printDefaultOnCli() {
        if (getState() == 100) {
            printWinners();
        } else {
            printIslands(gametable.getIslands());
            printPlayedAssistants(getListPlayedAssistants());
            if (playerId == playerOnTurn && state == 0) printAssistants(assistants);
            if (gameMode == GameMode.EXPERT) {
                printTableCoins();
                printCharacter(characters);
            }
            printAllSchoolboards();
            if (playerId == playerOnTurn && state != 0) printCloud(gametable.getClouds());
            if (gameMode == GameMode.EXPERT && playerOnTurn == playerId && state != 0 && !alreadyPlayedCharacter) System.out.println("Use command 'character' to play a character");
            if (playerId == playerOnTurn) System.out.println(playerMessageCli);
            else System.out.println("You're not the player on turn... Wait for other player!");
        }
    }

    /**
     * returns the assistants that has already been played
     * @return
     */
    public List<Assistant> getListPlayedAssistants() {
        List<Assistant> assistants = new ArrayList<>();
        for (int i = 0; i < numberOfPLayers; i++) {
            if (playedAssistants[i] != null) assistants.add(playedAssistants[i].getAssistant());
        }
        return assistants;
    }

    /**
     * returns the playerOnTurn attribute
     * @return
     */
    public int getPlayerOnTurn() { return playerOnTurn; }

    /**
     * returns the gameMode attribute
     * @return
     */
    public GameMode getGameMode() { return gameMode; }

    /**
     * returns the playersCoin[playerId] attribute with playerId passed as parameter
     * @param playerId
     * @return
     */
    public int getPlayesCoins(int playerId) {
        return playersCoins[playerId];
    }

    /**
     * prints the islands
     * @param islands
     */
    public void printIslands(List<Island> islands) {
        System.out.println("");
        for (int i = 0; i < islands.size(); i++) {
            System.out.print("   " + i + "          ");
        }
        System.out.println("");
        for (int i = 0; i < islands.size(); i++) {
            System.out.print("  /\u203E\u203E\u203E\u203E\u203E\u203E\\    ");
        }
        System.out.println("");
        for (int i = 0; i < islands.size(); i++) {
            if (gametable.getMotherNaturePosition() == i) System.out.print(" / " + "\uD83D\uDCA9" + (islands.get(i).getTowers().size() != 0 ? (islands.get(i).getTowers().size() + returnCircleUnicodeFromColor(islands.get(i).getTowers().get(0).getColor())) : "    ") + " \\   ");
            else if (gametable.getIslands().get(i).isForbidden()) System.out.print(" / " + "\u274C" + (islands.get(i).getTowers().size() != 0 ? (islands.get(i).getTowers().size() + returnCircleUnicodeFromColor(islands.get(i).getTowers().get(0).getColor())) : "    ") + " \\   ");
            else System.out.print(" /    " + (islands.get(i).getTowers().size() != 0 ? (islands.get(i).getTowers().size() + returnCircleUnicodeFromColor(islands.get(i).getTowers().get(0).getColor())) : "  ") +"  \\   ");
        }
        System.out.println("");
        for (int i = 0; i < islands.size(); i++) {
            int numeberOfStudents = islands.get(i).getNumberOfStudentsForColor(PawnColor.RED);
            String circle = returnCircleUnicodeForColor(PawnColor.RED);
            if (numeberOfStudents < 10) System.out.print("/   0" + numeberOfStudents + circle + "   \\  ");
            else System.out.print("/   " + numeberOfStudents + circle + "   \\  ");
        }
        System.out.println("");
        for (int i = 0; i < islands.size(); i++) {
            int numeberOfStudents = islands.get(i).getNumberOfStudentsForColor(PawnColor.BLUE);
            String circle = returnCircleUnicodeForColor(PawnColor.BLUE);
            if (numeberOfStudents < 10) System.out.print("\u258F   0" + numeberOfStudents + circle + "   \u2595  ");
            else System.out.print("\u258F   " + numeberOfStudents + circle + "   \u2595  ");
        }
        System.out.println("");
        for (int i = 0; i < islands.size(); i++) {
            int numeberOfStudents = islands.get(i).getNumberOfStudentsForColor(PawnColor.YELLOW);
            String circle = returnCircleUnicodeForColor(PawnColor.YELLOW);
            if (numeberOfStudents < 10) System.out.print("\u258F   0" + numeberOfStudents + circle + "   \u2595  ");
            else System.out.print("\u258F   " + numeberOfStudents + circle + "   \u2595  ");
        }
        System.out.println("");
        for (int i = 0; i < islands.size(); i++) {
            int numeberOfStudents = islands.get(i).getNumberOfStudentsForColor(PawnColor.GREEN);
            String circle = returnCircleUnicodeForColor(PawnColor.GREEN);
            if (numeberOfStudents < 10) System.out.print("\\   0" + numeberOfStudents + circle + "   /  ");
            else System.out.print("\\    " + numeberOfStudents + circle + "   /  ");
        }
        System.out.println("");
        for (int i = 0; i < islands.size(); i++) {
            int numeberOfStudents = islands.get(i).getNumberOfStudentsForColor(PawnColor.PINK);
            String circle = returnCircleUnicodeForColor(PawnColor.PINK);
            if (numeberOfStudents < 10) System.out.print(" \\  0" + numeberOfStudents + circle + "  /   ");
            else System.out.print(" \\  " + numeberOfStudents + circle + "  /   ");
        }
        System.out.println("");
        for (int i = 0; i < islands.size(); i++) {
            System.out.print("  \\\u005F\u005F\u005F\u005F\u005F\u005F/    ");
        }
    }

    /**
     * prints the assistants
     * @param assistants
     */
    public void printAssistants(List<Assistant> assistants) {
        if (assistants.size() != 0) {
            System.out.println("\n\nAssistants:");
            for (Assistant a : assistants) {
                System.out.print("\u2581\u2581\u2581\u2581\u2581\u2581\u2581\u2581     ");
            }
            System.out.println("");
            for (Assistant a : assistants) System.out.print("\u258F      \u2595     ");
            System.out.println("");
            for (Assistant a : assistants) {
                if (a.getCardValue() < 10) System.out.print("\u258F CV:" + a.getCardValue() +   " \u2595     ");
                else System.out.print("\u258F CV:" + a.getCardValue() +   "\u2595     ");
            }
            System.out.println("");
            for (Assistant a : assistants) System.out.print("\u258F MV:" + a.getMotherNatureMoves() + " \u2595     ");
            System.out.println("");
            for (Assistant a : assistants)  System.out.print("\u258F      \u2595     ");
            System.out.println("");
            for (Assistant a : assistants) System.out.print("\u258F      \u2595     ");
            System.out.println("");
            for (Assistant a : assistants) {
                System.out.print("\u2594\u2594\u2594\u2594\u2594\u2594\u2594\u2594     ");
            }
        }
    }

    /**
     * returns the tableCoins attribute
     * @return
     */
    public int getTableCoins() {
        return tableCoins;
    }

    /**
     * prints the already played islands
     * @param assistants
     */
    public void printPlayedAssistants(List<Assistant> assistants) {
        if (assistants.size() != 0) {
            System.out.println("\n\nPlayed Assistants:");
            for (int i = 0; i < numberOfPLayers; i++) {
                if (playedAssistants[i] != null) {
                    System.out.print("" + playersNicknames[playedAssistants[i].getPlayerIndex()]);
                    for (int  j = 0; j < (13 - playersNicknames[playedAssistants[i].getPlayerIndex()].length()); j++) System.out.print(" ");
                }

            }
            System.out.println("");
            for (Assistant a : assistants) {
                System.out.print("\u2581\u2581\u2581\u2581\u2581\u2581\u2581\u2581     ");
            }
            System.out.println("");
            for (Assistant a : assistants) System.out.print("\u258F      \u2595     ");
            System.out.println("");
            for (Assistant a : assistants) {
                if (a.getCardValue() < 10) System.out.print("\u258F CV:" + a.getCardValue() +   " \u2595     ");
                else System.out.print("\u258F CV:" + a.getCardValue() +   "\u2595     ");
            }
            System.out.println("");
            for (Assistant a : assistants) System.out.print("\u258F MV:" + a.getMotherNatureMoves() + " \u2595     ");
            System.out.println("");
            for (Assistant a : assistants)  System.out.print("\u258F      \u2595     ");
            System.out.println("");
            for (Assistant a : assistants) System.out.print("\u258F      \u2595     ");
            System.out.println("");
            for (Assistant a : assistants) {
                System.out.print("\u2594\u2594\u2594\u2594\u2594\u2594\u2594\u2594     ");
            }
        }
    }

    /**
     * prints the clouds
     * @param clouds
     */
    public void printCloud(Cloud[] clouds) {
        System.out.println("");
        for (int i = 0; i < clouds.length; i++) {
            System.out.print("   Cloud " + i + "    ");
        }
        System.out.println("");
        for (int i = 0; i < clouds.length; i++) {
            System.out.print("  /\u203E\u203E\u203E\u203E\u203E\u203E\\    ");
        }
        System.out.println("");
        for (int i = 0; i < clouds.length; i++) {
            int numeberOfStudents = clouds[i].getNumberOfStudentsForColor(PawnColor.RED);
            String circle = returnCircleUnicodeForColor(PawnColor.RED);
            if (numeberOfStudents < 10) System.out.print(" /  0" + numeberOfStudents + circle +  "  \\   ");
            else System.out.print(" /  " + numeberOfStudents + circle + "  \\   ");
        }
        System.out.println("");
        for (int i = 0; i < clouds.length; i++) {
            int numeberOfStudents = clouds[i].getNumberOfStudentsForColor(PawnColor.BLUE);
            String circle = returnCircleUnicodeForColor(PawnColor.BLUE);
            if (numeberOfStudents < 10) System.out.print("/   0" + numeberOfStudents + circle + "   \\  ");
            else System.out.print("/   " + numeberOfStudents + circle + "   \\  ");
        }
        System.out.println("");
        for (int i = 0; i < clouds.length; i++) {
            int numeberOfStudents = clouds[i].getNumberOfStudentsForColor(PawnColor.YELLOW);
            String circle = returnCircleUnicodeForColor(PawnColor.YELLOW);
            if (numeberOfStudents < 10) System.out.print("\u258F   0" + numeberOfStudents + circle + "   \u2595  ");
            else System.out.print("\u258F   " + numeberOfStudents + circle + "   \u2595  ");
        }
        System.out.println("");
        for (int i = 0; i < clouds.length; i++) {
            int numeberOfStudents = clouds[i].getNumberOfStudentsForColor(PawnColor.GREEN);
            String circle = returnCircleUnicodeForColor(PawnColor.GREEN);
            if (numeberOfStudents < 10) System.out.print("\\   0" + numeberOfStudents + circle + "   /  ");
            else System.out.print("\\    " + numeberOfStudents + circle + "   /  ");
        }
        System.out.println("");
        for (int i = 0; i < clouds.length; i++) {
            int numeberOfStudents = clouds[i].getNumberOfStudentsForColor(PawnColor.PINK);
            String circle = returnCircleUnicodeForColor(PawnColor.PINK);
            if (numeberOfStudents < 10) System.out.print(" \\  0" + numeberOfStudents + circle + "  /   ");
            else System.out.print(" \\  " + numeberOfStudents + circle + "  /   ");
        }
        System.out.println("");
        for (int i = 0; i < clouds.length; i++) {
            System.out.print("  \\\u005F\u005F\u005F\u005F\u005F\u005F/    ");
        }
        System.out.println("");
    }

    /**
     * prints all the schoolboards
     */
    public void printAllSchoolboards () {
        System.out.print("\n");
        if (schoolBoards.length == 2) {
            printSchoolboard(0);
            printSchoolboard(1);
        } else if (schoolBoards.length == 3) {
            printSchoolboard(0);
            printSchoolboard(1);
            printSchoolboard(2);
        } else {
            printSchoolboard(0);
            printSchoolboard(1);
            printSchoolboard(2);
            printSchoolboard(3);
        }
    }

    /**
     * prints the schoolboard of the player passed by parameter
     * @param playerId
     */
    public void printSchoolboard (int playerId) {
        SchoolBoard s = schoolBoards[playerId];
        Student[] entrance = schoolBoards[playerId].getStudentsFromEntrance();
        PawnColor[] color = new PawnColor[entrance.length];
        for (int i = 0; i < entrance.length; i++) {
            if (entrance[i] != null) color[i] = entrance[i].getColor();
        }
        TowerColor towerColor = s.getTowersColor();
        String unicodeTower = returnCircleUnicodeFromColor(towerColor);
        if (gameMode == GameMode.NORMAL) System.out.println("Player: " + playersNicknames[playerId]);
        else System.out.println("Player: " + playersNicknames[playerId] + "\t" + playersCoins[playerId] + "\uD83E\uDE99");
        if (numberOfPLayers == 3) {
            if (playerId == 2) {
                System.out.print("      ╔════════╦═════════════════════════════════╦════╦═══════╗\n" +
                        "0 - 1 ║ " + ((color[0] != null) ? returnCircleUnicodeForColor(color[0]) : "  ") + "  " + ((color[1] != null) ? returnCircleUnicodeForColor(color[1]) : "  ") + " ║ " + ((1 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((2 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((3 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((4 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((5 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((6 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((7 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((8 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((9 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((10 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + "  ║ " + (s.getProfessors().contains(PawnColor.GREEN) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " ║ " + (1 <= s.getTowers().size() ? unicodeTower : " ") + "  " + (2 <= s.getTowers().size() ? unicodeTower : " ") + "  ║\n" +
                        "2 - 3 ║ " + ((color[2] != null) ? returnCircleUnicodeForColor(color[2]) : "  ") + "  " + ((color[3] != null) ? returnCircleUnicodeForColor(color[3]) : "  ") + " ║ " + ((1 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((2 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((3 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((4 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((5 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((6 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((7 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((8 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : " ") + "  " + ((9 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((10 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + "  ║ " + (s.getProfessors().contains(PawnColor.RED) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " ║ " + (3 <= s.getTowers().size() ? unicodeTower : " ") + "  " + (4 <= s.getTowers().size() ? unicodeTower : " ") + "  ║\n" +
                        "4 - 5 ║ " + ((color[4] != null) ? returnCircleUnicodeForColor(color[4]) : "  ") + "  " + ((color[5] != null) ? returnCircleUnicodeForColor(color[5]) : "  ") + " ║ " + ((1 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((2 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((3 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((4 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((5 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((6 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((7 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((8 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((9 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((10 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + "  ║ " + (s.getProfessors().contains(PawnColor.YELLOW) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " ║ " + (5 <= s.getTowers().size() ? unicodeTower : " ") + "  " + (6 <= s.getTowers().size() ? unicodeTower : " ") + "  ║\n" +
                        "6 - 7 ║ " + ((color[6] != null) ? returnCircleUnicodeForColor(color[6]) : "  ") + "  " + ((color[7] != null) ? returnCircleUnicodeForColor(color[7]) : "  ") + " ║ " + ((1 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((2 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((3 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((4 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((5 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((6 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((7 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((8 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((9 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((10 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + "  ║ " + (s.getProfessors().contains(PawnColor.PINK) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " ║ " + (7 <= s.getTowers().size() ? unicodeTower : " ") + "  " + (8 <= s.getTowers().size() ? unicodeTower : " ") + "  ║\n" +
                        "    8 ║ " + ((color[8] != null) ? returnCircleUnicodeForColor(color[8]) : "  ") + "     ║ " +  ((1 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " +  ((2 <= s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((3 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((4 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((5 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((6 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((7 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((8 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((9 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((10 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + "  ║ " + (s.getProfessors().contains(PawnColor.BLUE) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " ║       ║\n" +
                        "      ╚════════╩═════════════════════════════════╩════╩═══════╝\n");
            } else {
                System.out.print("      ╔════════╦═════════════════════════════════╦════╦═══════╗\n" +
                        "0 - 1 ║ " + ((color[0] != null) ? returnCircleUnicodeForColor(color[0]) : "  ") + "  " + ((color[1] != null) ? returnCircleUnicodeForColor(color[1]) : "  ") + " ║ " + ((1 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((2 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((3 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((4 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((5 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((6 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((7 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((8 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((9 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((10 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + "  ║ " + (s.getProfessors().contains(PawnColor.GREEN) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " ║ " + (1 <= s.getTowers().size() ? unicodeTower : "  ") + " " + (2 <= s.getTowers().size() ? unicodeTower : "  ") + " ║\n" +
                        "2 - 3 ║ " + ((color[2] != null) ? returnCircleUnicodeForColor(color[2]) : "  ") + "  " + ((color[3] != null) ? returnCircleUnicodeForColor(color[3]) : "  ") + " ║ " + ((1 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((2 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((3 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((4 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((5 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((6 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((7 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((8 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : " ") + "  " + ((9 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((10 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + "  ║ " + (s.getProfessors().contains(PawnColor.RED) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " ║ " + (3 <= s.getTowers().size() ? unicodeTower : "  ") + " " + (4 <= s.getTowers().size() ? unicodeTower : "  ") + " ║\n" +
                        "4 - 5 ║ " + ((color[4] != null) ? returnCircleUnicodeForColor(color[4]) : "  ") + "  " + ((color[5] != null) ? returnCircleUnicodeForColor(color[5]) : "  ") + " ║ " + ((1 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((2 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((3 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((4 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((5 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((6 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((7 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((8 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((9 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((10 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + "  ║ " + (s.getProfessors().contains(PawnColor.YELLOW) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " ║ " + (5 <= s.getTowers().size() ? unicodeTower : "  ") + " " + (6 <= s.getTowers().size() ? unicodeTower : "  ") + " ║\n" +
                        "6 - 7 ║ " + ((color[6] != null) ? returnCircleUnicodeForColor(color[6]) : "  ") + "  " + ((color[7] != null) ? returnCircleUnicodeForColor(color[7]) : "  ") + " ║ " + ((1 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((2 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((3 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((4 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((5 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((6 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((7 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((8 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((9 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((10 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + "  ║ " + (s.getProfessors().contains(PawnColor.PINK) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " ║ " + (7 <= s.getTowers().size() ? unicodeTower : "  ") + " " + (8 <= s.getTowers().size() ? unicodeTower : "  ") + " ║\n" +
                        "    8 ║ " + ((color[8] != null) ? returnCircleUnicodeForColor(color[8]) : "  ") + "     ║ " +  ((1 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " +  ((2 <= s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((3 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((4 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((5 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((6 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((7 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((8 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((9 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((10 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + "  ║ " + (s.getProfessors().contains(PawnColor.BLUE) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " ║       ║\n" +
                        "      ╚════════╩═════════════════════════════════╩════╩═══════╝\n");
            }
        } else {
            System.out.print("      ╔════════╦═════════════════════════════════╦════╦═══════╗\n" +
                             "0 - 1 ║ " + ((color[0] != null) ? returnCircleUnicodeForColor(color[0]) : "  ") + "  " + ((color[1] != null) ? returnCircleUnicodeForColor(color[1]) : "  ") + " ║ " + ((1 <=  s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " +  ((2 <= s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((3 <=  s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((4 <=  s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((5 <=  s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((6 <=  s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((7 <=  s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((8 <=  s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((9 <=  s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + ((10 <=  s.getNumberOfStudentsOnTable(PawnColor.GREEN)) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " " + "  ║ " + (s.getProfessors().contains(PawnColor.GREEN) ? returnCircleUnicodeForColor(PawnColor.GREEN) : "  ") + " ║ " + (1 <= s.getTowers().size() ? unicodeTower : "  ") + " " + (2 <= s.getTowers().size() ? unicodeTower : "  ") + " ║\n" +
                             "2 - 3 ║ " + ((color[2] != null) ? returnCircleUnicodeForColor(color[2]) : "  ") + "  " + ((color[3] != null) ? returnCircleUnicodeForColor(color[3]) : "  ") + " ║ " +  ((1 <=  s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " +  ((2 <= s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((3 <=  s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((4 <=  s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((5 <=  s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((6 <=  s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((7 <=  s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((8 <=  s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : " ") + "  " + ((9 <=  s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + ((10 <=  s.getNumberOfStudentsOnTable(PawnColor.RED)) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " " + "  ║ " + (s.getProfessors().contains(PawnColor.RED) ? returnCircleUnicodeForColor(PawnColor.RED) : "  ") + " ║ " + (3 <= s.getTowers().size() ? unicodeTower : "  ") + " " + (4 <= s.getTowers().size() ? unicodeTower : "  ") + " ║\n" +
                             "4 - 5 ║ " + ((color[4] != null) ? returnCircleUnicodeForColor(color[4]) : "  ") + "  " + ((color[5] != null) ? returnCircleUnicodeForColor(color[5]) : "  ") + " ║ " +  ((1 <=  s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " +  ((2 <= s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((3 <=  s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((4 <=  s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((5 <=  s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((6 <=  s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((7 <=  s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((8 <=  s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((9 <=  s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + ((10 <=  s.getNumberOfStudentsOnTable(PawnColor.YELLOW)) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " " + "  ║ " + (s.getProfessors().contains(PawnColor.YELLOW) ? returnCircleUnicodeForColor(PawnColor.YELLOW) : "  ") + " ║ " + (5 <= s.getTowers().size() ? unicodeTower : "  ") + " " + (6 <= s.getTowers().size() ? unicodeTower : "  ") + " ║\n" +
                             "    6 ║ " + ((color[6] != null) ? returnCircleUnicodeForColor(color[6]) : "  ") + "     ║ " + ((1 <=  s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " +  ((2 <= s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((3 <=  s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((4 <=  s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((5 <=  s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((6 <=  s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((7 <=  s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((8 <=  s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((9 <=  s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + ((10 <=  s.getNumberOfStudentsOnTable(PawnColor.PINK)) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " " + "  ║ " + (s.getProfessors().contains(PawnColor.PINK) ? returnCircleUnicodeForColor(PawnColor.PINK) : "  ") + " ║ " + (7 <= s.getTowers().size() ? unicodeTower : "  ") + " " + (8 <= s.getTowers().size() ? unicodeTower : "  ") + " ║\n" +
                             "      ║        ║ " +  ((1 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " +  ((2 <= s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((3 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((4 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((5 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((6 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((7 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((8 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((9 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + ((10 <=  s.getNumberOfStudentsOnTable(PawnColor.BLUE)) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " " + "  ║ " + (s.getProfessors().contains(PawnColor.BLUE) ? returnCircleUnicodeForColor(PawnColor.BLUE) : "  ") + " ║       ║\n" +
                             "      ╚════════╩═════════════════════════════════╩════╩═══════╝\n");
        }
    }

    /**PINK
     * prints the characters passed by parameter
     * @param characters
     */
    public void printCharacter(Character[] characters) {
        System.out.println("Characters:");
        System.out.println("    0               1                2");
        for (Character c : characters) {
            System.out.print("\u2581\u2581\u2581\u2581\u2581\u2581\u2581\u2581\u2581\u2581\u2581     ");
        }
        System.out.println("");
        for (Character c : characters){
            System.out.print("\u258F" + c.getName());
            int count = (9 - c.getName().length());
            for (int i = 0; i < count; i++) System.out.print(" ");
            System.out.print("\u2595     ");
        }
        System.out.println("");
        for (Character c : characters) {
            System.out.print("\u258FCost:" + c.getCost() +   "   \u2595     ");
        }

        System.out.println("");
        for (Character c : characters) {
            if (c instanceof Monk || c instanceof Princess) System.out.print("\u258F" + ((((CharacterWithStudent) c).getStudentsOnCard()[0] != null) ? returnCircleUnicodeForColor(((CharacterWithStudent) c).getStudentsOnCard()[0].getColor()) : "  ") + "    " + ((((CharacterWithStudent) c).getStudentsOnCard()[1] != null) ? returnCircleUnicodeForColor(((CharacterWithStudent) c).getStudentsOnCard()[1].getColor()) : "  ") +" \u2595     ");
            else if (c instanceof Jester) System.out.print("\u258F" + ((((CharacterWithStudent) c).getStudentsOnCard()[0] != null) ? returnCircleUnicodeForColor(((CharacterWithStudent) c).getStudentsOnCard()[0].getColor()) : "  ") + " " + ((((CharacterWithStudent) c).getStudentsOnCard()[1] != null) ? returnCircleUnicodeForColor(((CharacterWithStudent) c).getStudentsOnCard()[1].getColor()) : "  ") + " " + ((((CharacterWithStudent) c).getStudentsOnCard()[2] != null) ? returnCircleUnicodeForColor(((CharacterWithStudent) c).getStudentsOnCard()[2].getColor()) : "  ") +" \u2595     ");
            else if (c instanceof Healer) System.out.print("\u258F" + ((((Healer) c).getNumberOfProibitionCard() >= 1) ? "\u274C" : "  ") + "    " + ((((Healer) c).getNumberOfProibitionCard() >= 2) ? "\u274C" : "  ") + " \u2595     ");
            else System.out.print("\u258F         \u2595     ");
        }
        System.out.println("");
        for (Character c : characters) {
            if (c instanceof Monk || c instanceof Princess) System.out.print("\u258F" + ((((CharacterWithStudent) c).getStudentsOnCard()[2] != null) ? returnCircleUnicodeForColor(((CharacterWithStudent) c).getStudentsOnCard()[2].getColor()) : "  ") + "    " + ((((CharacterWithStudent) c).getStudentsOnCard()[3] != null) ? returnCircleUnicodeForColor(((CharacterWithStudent) c).getStudentsOnCard()[3].getColor()) : "  ") +" \u2595     ");
            else if (c instanceof Jester) System.out.print("\u258F" + ((((CharacterWithStudent) c).getStudentsOnCard()[3] != null) ? returnCircleUnicodeForColor(((CharacterWithStudent) c).getStudentsOnCard()[3].getColor()) : "  ") + " " + ((((CharacterWithStudent) c).getStudentsOnCard()[4] != null) ? returnCircleUnicodeForColor(((CharacterWithStudent) c).getStudentsOnCard()[4].getColor()) : "  ") + " " + ((((CharacterWithStudent) c).getStudentsOnCard()[5] != null) ? returnCircleUnicodeForColor(((CharacterWithStudent) c).getStudentsOnCard()[5].getColor()) : "  ") +" \u2595     ");
            else if (c instanceof Healer) System.out.print("\u258F" + ((((Healer) c).getNumberOfProibitionCard() >= 3) ? "\u274C" : "  ") + "    " + ((((Healer) c).getNumberOfProibitionCard() >= 4) ? "\u274C" : "  ") + " \u2595     ");
            else System.out.print("\u258F         \u2595     ");
        }
        System.out.println("");
        for (Character c : characters) {
            System.out.print("\u2594\u2594\u2594\u2594\u2594\u2594\u2594\u2594\u2594\u2594\u2594     ");
        }
    }

    /**
     * returns the unicode for the color passed by parameter for pawns
     * @param color
     * @return
     */
    public String returnCircleUnicodeForColor (PawnColor color) {
        switch (color.getIndex()) {
            case 0:
                return "\uD83D\uDFE2";
            case 1:
                return "\uD83D\uDD34";
            case 2:
                return "\uD83D\uDFE1";
            case 3:
                return "\uD83D\uDFE3";
            case 4:
                return "\uD83D\uDD35";
        }
        return "";
    }

    /**
     * returns the unicode for the color passed by parameter for towers
     * @param color
     * @return
     */
    public String returnCircleUnicodeFromColor (TowerColor color) {
        switch (color.getIndex()) {
            case 0:
                return "\u26AA";
            case 1:
                return "\u26AB";
            case 2:
                return "\u25EF";
        }
        return "";
    }

    /**
     * prints the table coins
     */
    public void printTableCoins() {
        System.out.println("\n\nTable coins: " + getTableCoins() + "\uD83E\uDE99");
    }

    /**
     * prints the winner's nicknames
     */
    public void printWinners() {
        if (getWinnersIndexes().contains(playerId)) System.out.println("You WIN!!");
        else {
            System.out.println("Winners: ");
            for (String s : getWinnersNicknames()) System.out.print(s + "\t");
        }
    }

}
