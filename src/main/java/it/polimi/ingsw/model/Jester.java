package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.EffectCannotBeActivatedException;
import it.polimi.ingsw.model.exception.InvalidIndexException;

import java.util.ArrayList;
import java.util.List;

public class Jester extends CharacterWithStudent{

    private List<Integer> studentsIndexOnCard;
    private List<Integer> studentsIndexOnEntrance;
    private int countCard;
    private int countEntrance;

    /**
     * initializes Jester
     * @param id
     * @param cost
     * @param numberOfStudent
     */
    public Jester(int id, int cost, int numberOfStudent) {
        super(id, cost, numberOfStudent, "Jester");
        studentsIndexOnCard = new ArrayList<>();
        studentsIndexOnEntrance = new ArrayList<>();
    }

    /**
     * Switches up to three students between the ones on the card and the ones on the entrance of the player who played this card
     * @param playerId
     * @param parameter
     */
    @Override
    public void doYourJob(int playerId, int parameter) {
        if (getRoundState() == 4) {
            try{
                if (parameter <= 0 || parameter > 3) throw new InvalidIndexException("The number of students is wrong. How many students do you want to change? {1, 2, 3}");
                setPlayerMessageCli(playerId,"Select student on card");
                setPlayerMessageGui(playerId,"Select student on card");
                countCard = parameter;
                countEntrance = parameter;
                setRoundState(5);
            } catch (InvalidIndexException e) {
                setPlayerMessageCli(playerId, e.getMessage());
                setPlayerMessageGui(playerId, e.getMessage());
            }
        } else if (getRoundState() == 5) {
            if (countCard > 0) {
                try {
                    if (parameter < 0 || parameter > 5) throw new InvalidIndexException("The student doesn't exists\n Chose another one");
                    if (studentsIndexOnCard.contains(parameter)) throw new InvalidIndexException("The student is already chosen\n Chose another one");
                    studentsIndexOnCard.add(parameter);
                    countCard -= 1;
                    setPlayerMessageCli(playerId,"Select student on card");
                    setPlayerMessageGui(playerId,"Select student on card");
                } catch (InvalidIndexException e) {
                    setPlayerMessageCli(playerId, e.getMessage());
                    setPlayerMessageGui(playerId, e.getMessage());
                }
            }
            if(countCard == 0) {
                setRoundState(6);
                setPlayerMessageCli(playerId,"Select student on entrance");
                setPlayerMessageGui(playerId,"Select student on entrance");
            }
        } else if (getRoundState() == 6) {
            if (countEntrance > 0) {
                try{
                    if (parameter < 0 || (parameter > 6 && getGame().getNumberOfPlayers() == 2) || (parameter > 6 && getGame().getNumberOfPlayers() == 4) || (parameter > 8 && getGame().getNumberOfPlayers() == 3) || getGame().getGameTable().getSchoolBoards()[playerId].getStudentsFromEntrance()[parameter] == null) throw new InvalidIndexException("The student doesn't exists\n Chose another one: ");
                    if (studentsIndexOnEntrance.contains(parameter)) throw new InvalidIndexException("The student is already chosen\n Chose another one: ");
                    studentsIndexOnEntrance.add(parameter);
                    countEntrance -= 1;
                    setPlayerMessageCli(playerId,"Select student on entrance");
                    setPlayerMessageGui(playerId,"Select student on entrance");
                } catch (InvalidIndexException e) {
                    setPlayerMessageCli(playerId, e.getMessage());
                    setPlayerMessageGui(playerId, e.getMessage());
                }
            }
            if(countEntrance == 0){
                try {
                    List<Student> newStudentsOnCard = new ArrayList<>();
                    List<Student> newStudentsOnEntrance = new ArrayList<>(getStudents(studentsIndexOnCard));
                    for (Integer i : studentsIndexOnEntrance)
                        newStudentsOnCard.add(getRound().getGame().getGameTable().getSchoolBoards()[playerId].removeStudentFromEntrance(i));
                    addStudents(newStudentsOnCard);
                    getRound().getGame().getGameTable().getSchoolBoards()[playerId].addStudentsOnEntrance(newStudentsOnEntrance);
                    deactivateEffect(true);
                } catch (InvalidIndexException e) {
                    setPlayerMessageCli(playerId, e.getMessage());
                    setPlayerMessageGui(playerId, e.getMessage());
                }
            }
        }
    }

    /**
     * activates the effect of Jester setting the appropriate messages
     * @param playerID
     * @param round
     * @return
     * @throws EffectCannotBeActivatedException
     */
    @Override
    public Round activateEffect (int playerID, Round round) throws EffectCannotBeActivatedException {
        round.getGame().getPlayer(playerID).setPlayerMessageCli("How many Students do you want to change");
        round.getGame().getPlayer(playerID).setPlayerMessageGui("How many Students do you want to change");
        Round r = super.activateEffect(playerID, round);
        setRoundState(4);
        return r;
    }
}
