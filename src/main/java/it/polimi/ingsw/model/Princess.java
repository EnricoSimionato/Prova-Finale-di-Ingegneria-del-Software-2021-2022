package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.*;

import java.util.ArrayList;
import java.util.List;

public class Princess extends CharacterWithStudent  {

    private int studentIndex;

    /**
     * Creates a new Princess as Round
     * @param id
     * @param cost
     * @param numberOfStudent
     */
    public Princess(int id, int cost, int numberOfStudent){
        super(id, cost, numberOfStudent, "Princess");
    }

    /**
     * Performe the Princess effect when it receives the correct input
     * @param playerId
     * @param parameter
     */
    @Override
    public void doYourJob(int playerId, int parameter){

        if (getRoundState() == 4) {
            studentIndex = parameter;
            try{
                if (parameter < 0 || parameter > 3) throw new InvalidIndexException("The chosen student doesn't exists\n Chose another one: ");
                List<Integer> indexStudentsOnCard = new ArrayList<>();
                indexStudentsOnCard.add(studentIndex);
                List<Student> studentsOnCard = new ArrayList<>(getStudents(indexStudentsOnCard));

                if (studentsOnCard.size() <= 0)
                    throw new NoMoreStudentsException();

                getRound().getGame().getGameTable().getSchoolBoards()[playerId].addStudentOnTable(studentsOnCard.get(0));
                getGame().getGameTable().moveProfessorToTheRightPosition(studentsOnCard.get(0).getColor());
                addStudents(getRound().getGame().getGameTable().getBag().drawStudents(1));
                deactivateEffect(true);
            }catch (InvalidIndexException | EmptyBagException e){
                setPlayerMessageCli(playerId, e.getMessage());
                setPlayerMessageGui(playerId, e.getMessage());
                getGame().sendGame();
            }catch (NoMoreStudentsException e){
                setPlayerMessageCli(playerId, "You can't play this card because there are no students");
                setPlayerMessageGui(playerId, "You can't play this card because there are no students");
                getGame().sendGame();
            } catch (FullTableException e) {
                setPlayerMessageCli(playerId, "You can't add the chosen student on table because it is full");
                setPlayerMessageGui(playerId, "You can't add the chosen student on table because it is full");
                getGame().sendGame();
            }
        }
    }

    /**
     * Creates a new Princess and return it has new Round
     * @param playerID
     * @param round
     * @return
     * @throws EffectCannotBeActivatedException
     */
    @Override
    public Round activateEffect (int playerID, Round round) throws EffectCannotBeActivatedException {
        round.getGame().getPlayer(playerID).setPlayerMessageCli("Select student");
        round.getGame().getPlayer(playerID).setPlayerMessageGui("Select student");
        super.activateEffect(playerID, round);
        setRoundState(4);
        return this;
    }
}
