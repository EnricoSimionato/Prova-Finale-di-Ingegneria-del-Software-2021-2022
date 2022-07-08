package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.EffectCannotBeActivatedException;
import it.polimi.ingsw.model.exception.EmptyTableException;
import it.polimi.ingsw.model.exception.InvalidIndexException;

import java.util.Arrays;

public class Thief extends Character {

    private PawnColor pawnColor;

    /**
     * Creates a Thief
     * @param id
     * @param cost
     */
    public Thief(int id, int cost){
        super(id, cost, "Thief");
        pawnColor = null;
    }

    /**
     * Saves the chosen Student color and perform the effect
     * @param playerId
     * @param parameter
     */
    @Override
    public void doYourJob(int playerId, int parameter) {
        if (getRoundState() == 4) {
            try {
                if (parameter < 0 || parameter > 4) throw new InvalidIndexException("Invalid color index\nChoose another one:");
                pawnColor = PawnColor.associateIndexToPawnColor(parameter);
                for (int i = 0; i < getRound().getGame().getNumberOfPlayers(); i++)
                    for (int j = 0; j < 3; j++)
                        if (getRound().getGame().getGameTable().getSchoolBoards()[i].getNumberOfStudentsOnTable(pawnColor) > 0)
                            getRound().getGame().getGameTable().getBag().addStudents(Arrays.asList(getRound().getGame().getGameTable().getSchoolBoards()[i].removeStudentFromTable(pawnColor)));
                deactivateEffect(true);
            }catch (InvalidIndexException | EmptyTableException e) {
                setPlayerMessageCli(playerId,e.getMessage());
                setPlayerMessageGui(playerId,e.getMessage());
                getGame().sendGame();
            }
        }
    }

    /**
     * Creates a new Thief as a Round and set the new player message
     * @param playerID
     * @param round
     * @return Thief as new Round
     * @throws EffectCannotBeActivatedException
     */
    @Override
    public Round activateEffect (int playerID, Round round) throws EffectCannotBeActivatedException {
        round.getGame().getPlayer(playerID).setPlayerMessageCli("Select pawn color { 0:GREEN - 1: RED - 2:YELLOW - 3:PINK - 4:BLUE }");
        round.getGame().getPlayer(playerID).setPlayerMessageGui("Select pawn color");
        Round r = super.activateEffect(playerID, round);
        setRoundState(4);
        return r;
    }
}