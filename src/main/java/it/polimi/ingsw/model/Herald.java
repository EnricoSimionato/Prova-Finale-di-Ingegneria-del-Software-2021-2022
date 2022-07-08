package it.polimi.ingsw.model;
import it.polimi.ingsw.model.exception.EffectCannotBeActivatedException;
import it.polimi.ingsw.model.exception.InvalidIndexException;
import it.polimi.ingsw.model.exception.NoMoreTowersException;
import it.polimi.ingsw.model.exception.ThreeOrLessIslandException;


public class Herald extends Character{

    public Herald(int id, int cost) {
        super(id, cost, "Herald");
    }

    /**
     * Calculates influence in a selected Island without change mother nature position
     * @param playerId      Index of player that want to play the character
     * @param parameter     Index of island where the player want to calculate the influence
     */
    @Override
    public void doYourJob(int playerId, int parameter) {
        if (getRoundState() == 4) {
            try {
                if (parameter < 0 || parameter > 11) throw new InvalidIndexException("The island doesn't exist!\nChoose another one");
                int islandIndex = parameter;
                int oldPosition = getRound().getGame().getGameTable().getMotherNaturePosition();
                if (getRound().getGame().isAValidPositionForMotherNature(islandIndex)) {
                    getRound().getGame().getGameTable().changeMotherNaturePosition(islandIndex);
                    int[] influences = getRound().getGame().getGameTable().calculateInfluenceValuesGivenByStudents();
                    int[] influencesFromTowers = getRound().getGame().getGameTable().calculateInfluenceValuesGivenByTowers();
                    for (int i = 0; i < influences.length; i++) {
                        influences[i] += influencesFromTowers[i];
                    }
                    try {
                        getRound().getGame().getGameTable().putTowerOrChangeColorIfNecessary(influences);
                    } catch (NoMoreTowersException e) {
                        getRound().getGame().setVictory();
                        getRound().getGame().setWinner(e.getEmptySchoolboardColor());
                    } catch (ThreeOrLessIslandException e) {
                        checkEndgameAndSetTheWinner();
                    }
                    if(getRound().getGame().isGameEnded()) {
                        getRound().setRoundState(100);
                        roundState = 100;
                    }
                }
                getRound().getGame().getGameTable().changeMotherNaturePosition(oldPosition);
                deactivateEffect(true);
            } catch (InvalidIndexException e) {
                setPlayerMessageCli(playerId, e.getMessage());
                setPlayerMessageGui(playerId, e.getMessage());
                getGame().sendGame();
            }
        }
    }

    /**
     * sets the correct messages to cli and gui, calls the activateEffect method of round and sets the round state at 4
     * @param playerID
     * @param round
     * @return
     * @throws EffectCannotBeActivatedException
     */
    @Override
    public Round activateEffect (int playerID, Round round) throws EffectCannotBeActivatedException {
        round.getGame().getPlayer(playerID).setPlayerMessageCli("Select Island");
        round.getGame().getPlayer(playerID).setPlayerMessageGui("Select Island");
        Round r = super.activateEffect(playerID, round);
        setRoundState(4);
        return r;
    }
}
