package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.EffectCannotBeActivatedException;
import it.polimi.ingsw.model.exception.EmptyBagException;
import it.polimi.ingsw.model.exception.InvalidIndexException;
import it.polimi.ingsw.model.exception.NoMoreStudentsException;

import java.util.ArrayList;
import java.util.List;

public class Monk extends CharacterWithStudent {

    private int studentIndex;
    private int islandIndex;

    /**
     * Creates a character that can contain a certain amount of students
     * @param id              integer that identifies the character card
     * @param cost            amount of money needed to activate the card effect
     * @param numberOfStudent maximum number of students the card can contain
     */
    public Monk(int id, int cost, int numberOfStudent) {
        super(id, cost, numberOfStudent, "Monk");
    }

    /**
     * Override of the Round DoYourJob that after received the selected island and the selected student,
     * add the chosen student to the chosen Island
     * @param playerId
     * @param parameter
     */
    @Override
    public void doYourJob(int playerId, int parameter) {
        if (getRoundState() == 4) {
            try {
                if (parameter < 0 || parameter > 3) throw new InvalidIndexException("The student doesn't exists\nChose another one");
                setPlayerMessageCli(playerId,"Select island");
                setPlayerMessageGui(playerId,"Select island");
                studentIndex = parameter;
                setRoundState(5);
            } catch (InvalidIndexException e) {
                setPlayerMessageCli(playerId, e.getMessage());
                setPlayerMessageGui(playerId, e.getMessage());
                getGame().sendGame();
            }
        } else if (getRoundState() == 5) {
            try {
                if (parameter < 0 || parameter > 11) throw new InvalidIndexException("The island doesn't exists\nChose another one");
                islandIndex = parameter;
                List<Integer> indexStudentsOnCard = new ArrayList<>();
                indexStudentsOnCard.add(studentIndex);
                List<Student> studentsOnCard = new ArrayList<>(getStudents(indexStudentsOnCard));
                if (studentsOnCard.size() <= 0) throw new NoMoreStudentsException();
                getRound().getGame().getGameTable().addStudentOnIsland(studentsOnCard.remove(0), islandIndex);
                addStudents(getRound().getGame().getGameTable().getBag().drawStudents(1));
                deactivateEffect(true);
            } catch (NoMoreStudentsException e) {
                setPlayerMessageCli(playerId,"You can't play this card because there are no students");
                setPlayerMessageGui(playerId,"You can't play this card because there are no students");
                getGame().sendGame();
            } catch (InvalidIndexException e) {
                setPlayerMessageCli(playerId, e.getMessage());
                setPlayerMessageGui(playerId, e.getMessage());
                getGame().sendGame();
            } catch (EmptyBagException e) {

            }
        }
    }

    /**
     * Creates a new Monk and return it as new Round. It sets the new message for the player
     * @param playerID
     * @param round
     * @return the new Monk as new Round
     * @throws EffectCannotBeActivatedException
     */
    @Override
    public Round activateEffect (int playerID, Round round) throws EffectCannotBeActivatedException {
        round.getGame().getPlayer(playerID).setPlayerMessageCli("Select Student on card");
        round.getGame().getPlayer(playerID).setPlayerMessageGui("Select Student on card");
        super.activateEffect(playerID, round);
        setRoundState(4);
        return this;
    }
}
