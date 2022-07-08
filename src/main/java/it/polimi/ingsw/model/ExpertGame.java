package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletionService;

public class ExpertGame extends Game {
    private Game game;
    private GameMode gameMode;
    private Character[] characters;
    private int coinsOfTheTable;
    private int[] playersCoins;

    /**
     * Creates an expert mode game instance.
     * @param numberOfPlayers number of players that will play the match
     * @param nicknames nicknames of the players in the match
     */
    public ExpertGame(int numberOfPlayers, List<String> nicknames) {
        super(numberOfPlayers, nicknames);
        gameMode = GameMode.EXPERT;
        game = new Game(numberOfPlayers, nicknames);
        coinsOfTheTable = 20 - numberOfPlayers;
        playersCoins = new int[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) playersCoins[i] = 1;
        try {
            characters = new Character[3];
            createCharacters();
            for (int i = 0; i < 3; i++) {
                if (characters[i].getID() == 5) {
                    characters[i].setRound(this.getRound());
                    setRound(characters[i]);
                }
            }
        } catch (EmptyBagException e) {
            e.printStackTrace();
        }
    }

    /**
     * returns the characters attribute
     * @return
     */
    public Character[] getCharacters(){
        return characters;
    }

    /**
     * returns the gameMOde attribute
     * @return
     */
    public GameMode getGameMode() {return gameMode;}

    /**
     * sets the attribute characters with the value passed by parameter
     * @param characters
     */
    public void setCharacters(Character[] characters){
        this.characters = characters;
    }

    /**
     * Sets a single character of the expert mode match. If the index is not valid the method doesn't do anything
     * @param character character to be set
     * @param position index of the character, in the 3 character array, that has to be associated to the given one
     */
    public void setASingleCharacter(Character character, int position) { characters[position] = character; }

    /**
     * Creates the instances of the three characters of the current match, choosing three random characters of the deck
     * @throws EmptyBagException if a character, which has students placed on it, tries to draw a student from an empty bag
     */
    public void createCharacters() throws EmptyBagException {
        List<Character> c = new ArrayList<>();

        String characters = CharactersStorage.getCharacters();
        String[] charactersParsed = characters.split(";");
        for (String s : charactersParsed) {
            String[] character = s.split(",");
            int ID = Integer.parseInt(character[0]);
            int cost = Integer.parseInt(character[1]);
            switch (ID) {
                case 1:
                    Monk c1 = new Monk(ID, cost, 4);
                    c1.addStudents(game.getGameTable().getBag().drawStudents(4));
                    c.add(c1);
                    break;
                case 2:
                    InnKeeper c2 = new InnKeeper(ID, cost);
                    c.add(c2);
                    break;
                case 3:
                    Herald c3 = new Herald(ID, cost);
                    c.add(c3);
                    break;
                case 4:
                    Postman c4 = new Postman(ID, cost);
                    c.add(c4);
                    break;
                case 5:
                    Healer c5 = new Healer(ID, cost);
                    c.add(c5);
                    break;
                case 6:
                    Centaur c6 = new Centaur(ID, cost);
                    c.add(c6);
                    break;
                case 7:
                    Jester c7 = new Jester(ID, cost, 6);
                    c7.addStudents(game.getGameTable().getBag().drawStudents(6));
                    c.add(c7);
                    break;
                case 8:
                    Knight c8 = new Knight(ID, cost);
                    c.add(c8);
                    break;
                case 9:
                    Villager c9 = new Villager(ID, cost);
                    c.add(c9);
                    break;
                case 10:
                    Minstrel c10 = new Minstrel(ID, cost);
                    c.add(c10);
                    break;
                case 11:
                    Princess c11 = new Princess(ID, cost, 4);
                    c11.addStudents(game.getGameTable().getBag().drawStudents(4));
                    c.add(c11);
                    break;
                case 12:
                    Thief c12 = new Thief(ID, cost);
                    c.add(c12);
                    break;
            }
        }

        Random rnd = new Random();
        int numberOfCharacter = 12;
        for (int i = 0; i < 3; i++) {
            this.characters[i] = c.remove(rnd.nextInt(numberOfCharacter));
            numberOfCharacter--;
        }
    }

    /**
     * returns the id of the card identified by indexCard, passed by parameter
     * @param indexCard
     * @return
     * @throws InvalidIndexException
     */
    public int getIdCharacter(int indexCard) throws InvalidIndexException {
        if (indexCard < 0 || indexCard > 3) throw new InvalidIndexException("Error character index gameTable");
        else return this.characters[indexCard].getID();
    }

    /**
     * returns the cost of the card identified by indexCard, passed by parameter
     * @param indexCard
     * @return
     * @throws InvalidIndexException
     */
    public int getCostCharacter(int indexCard) throws InvalidIndexException {
        if (indexCard < 0 || indexCard > 3) throw new InvalidIndexException("Error character index gameTable");
        else return this.characters[indexCard].getCost();
    }

    /**
     * returns the students on the card specified by indexCard, passed by parameter,
     * in the positions studentsPositions, passed by parameter
     * @param indexCard
     * @param studentsPositions
     * @return
     */
    public List<Student> getStudentFromCard(int indexCard, List<Integer> studentsPositions) {
        List<Student> returnStudents = new ArrayList<>();
        try {
            if (characters[indexCard] instanceof CharacterWithStudent) {
                returnStudents.addAll(((CharacterWithStudent) characters[indexCard]).getStudents(studentsPositions));
                ((CharacterWithStudent) characters[indexCard]).addStudents(game.getGameTable().getBag().drawStudents(studentsPositions.size()));
            }
        } catch(EmptyBagException e) {
            setRound(new LastRound(this, getRound().getPlayerOrder(), true));
        }catch (InvalidIndexException e) {
            e.printStackTrace();
        }
        return returnStudents;
    }

    /**
     * adds new students on the card
     * @param cardIndex
     * @param newStudents
     */
    public void addStudentsOnCard(int cardIndex, List<Student> newStudents) {
        if(characters[cardIndex] instanceof CharacterWithStudent)
            ((CharacterWithStudent)this.characters[cardIndex]).addStudents(newStudents);
    }

    /**
     * returns the attribute character[indexCard] with indexCard passed by parameter
     * @param indexCard
     * @return
     */
    public Character getCharacter(int indexCard) {
        return this.characters[indexCard];
    }

    /**
     * activate the effect of the character identified by its card index, passed by parameter
     * @param playerId
     * @param indexCard
     * @throws EffectCannotBeActivatedException
     * @throws NotEnoughCoins
     */
    @Override
    public void activateEffect(int playerId, int indexCard) throws EffectCannotBeActivatedException, NotEnoughCoins {
        try {
            int cost = getCostCharacter(indexCard);
            if (playersCoins[playerId] < cost) throw new NotEnoughCoins();
            if (!getCharacter(indexCard).getFirstUse()) getCharacter(indexCard).setFirstUse();
            setRound(characters[indexCard].activateEffect(playerId, getRound()));
            removeCoinsFromAPlayer(playerId, cost);
            addCoinsToTheTable(cost);
        } catch (InvalidIndexException e) {
            e.printStackTrace();
        }
    }

    /**
     * returns the attribute coinsOfTheTable
     * @return
     */
    public int getCoinsOfTheTable() {
        return coinsOfTheTable;
    }

    /**
     * returns the attribute playersCoins
     * @return
     */
    public int[] getPlayersCoins() {
        return playersCoins;
    }

    /**
     * adds a coin to the player passed  by parameter
     * @param playerIndex
     * @throws NotEnoughCoins
     */
    public void addCoinToAPlayer(int playerIndex) throws NotEnoughCoins {
        if (getCoinsOfTheTable() <= 0) throw new NotEnoughCoins();
        playersCoins[playerIndex]++;
    }

    /**
     * removes a number of coins passed by parameter from the player passed by parameter
     * @param playerIndex
     * @param numberOfCoinsToRemove
     * @throws NotEnoughCoins
     */
    public void removeCoinsFromAPlayer(int playerIndex, int numberOfCoinsToRemove) throws NotEnoughCoins {
        if(playersCoins[playerIndex] < numberOfCoinsToRemove) throw new NotEnoughCoins();
        playersCoins[playerIndex] -= numberOfCoinsToRemove;
    }

    /**
     * adds a coin to the table
     * @param numberOfCoinsToAdd
     */
    public void addCoinsToTheTable(int numberOfCoinsToAdd) {
        coinsOfTheTable += numberOfCoinsToAdd;
    }

    /**
     * removes a coin from the table
     * @throws NotEnoughCoins
     */
    public void removeCoinFromTheTable() throws NotEnoughCoins {
        if(coinsOfTheTable < 1) throw new NotEnoughCoins();
        coinsOfTheTable -= 1;
    }
}
