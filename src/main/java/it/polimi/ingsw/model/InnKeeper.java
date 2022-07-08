package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.*;

public class InnKeeper extends Character{

    /**
     * Creates a character card with the given two values
     *
     * @param id   integer that identifies the character card
     * @param cost amount of money needed to activate the card effect
     */
    public InnKeeper(int id, int cost) {
        super(id, cost, "Innkeeper");
    }

    /**
     * activates the effect of the InnKeeper calling changeProfessorEffect( playerID, round)
     * @param playerID
     * @param round
     * @return
     * @throws EffectCannotBeActivatedException
     */
    @Override
    public Round activateEffect(int playerID, Round round) throws EffectCannotBeActivatedException {
        super.activateEffect(playerID, round);
        changeProfessorEffect(playerID);
        return this;
    }

    /**
     * Wherever there are the same number of students on two game tables, the player who played the card
     * achieves the correspondent professor if it's not already achieved
     * @param playerId
     */
    public void changeProfessorEffect( int playerId ){
        for (int i = 0; i < getGame().getNumberOfPlayers(); i++){
            if (i != playerId){
                for (PawnColor professor : getGame().getGameTable().getSchoolBoards()[i].getProfessors()) {
                    if (getGame().getGameTable().getSchoolBoards()[i].getNumberOfStudentsOnTable(professor) == getGame().getGameTable().getSchoolBoards()[playerId].getNumberOfStudentsOnTable(professor)){
                        getGame().getGameTable().getSchoolBoards()[i].setProfessor(professor, false);
                        getGame().getGameTable().getSchoolBoards()[playerId].setProfessor(professor, true);
                    }
                }
            }
        }
    }
}