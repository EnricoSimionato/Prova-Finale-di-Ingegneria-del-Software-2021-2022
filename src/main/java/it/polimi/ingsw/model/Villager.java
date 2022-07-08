package it.polimi.ingsw.model;
import it.polimi.ingsw.model.exception.*;

public class Villager extends Character {
    private PawnColor studentColor;

    /**
     * Creates a character card with the given two values
     * @param id   integer that identifies the character card
     * @param cost amount of money needed to activate the card effect
     */
    public Villager(int id, int cost) {
        super(id, cost, "Villager");
        studentColor = null;
    }

    /**
     * take and store the student color that will be used during the effect
     * @param playerId
     * @param parameter
     */
    @Override
    public void doYourJob(int playerId, int parameter) {
        if (getRoundState() == 4) {
            try {
                if (parameter < 0 || parameter > 4) throw new InvalidIndexException("The chosen student color doesn't exist! Choose another one");
                getGame().getRound().setRoundState(getOldState());
                setPlayerMessageCli(playerId, getStateMessageCli());
                setPlayerMessageGui(playerId, getStateMessageGui());
                studentColor = PawnColor.associateIndexToPawnColor(parameter);
            } catch (InvalidIndexException e) {
                setPlayerMessageCli(playerId, e.getMessage());
                setPlayerMessageGui(playerId, e.getMessage());
            }
        }
    }

    /**
     * @return color chosen during activation effect
     */
    public PawnColor getStudentColor(){
        return studentColor;
    }

    /**
     * Active Villager effect and return the new decorated round
     * @param playerID
     * @param round
     * @return the new decorated round
     * @throws EffectCannotBeActivatedException
     */
    public Round activateEffect(int playerID, Round round) throws EffectCannotBeActivatedException {
        super.activateEffect(playerID, round);
        setPlayerMessageCli(playerID,"Select a student color { 0:GREEN - 1:RED - 2:YELLOW - 3:PINK - 4:BLUE}");
        setPlayerMessageGui(playerID,"Select a student color");
        setRoundState(4);
        return this;
    }

    /**
     * Calculates influence, after changed mother nature position, without consider the chosen Student color
     * @param playerId
     * @param islandIndex
     */
    @Override
    public void changeMotherNaturePosition (int playerId, int islandIndex) {
        try {
            checkPlayerOnTurn(playerId);
            checkStatusAndMethod(2);
            try {
                int i = 0;
                while (i < getRound().getPlayedAssistants().length) {
                    if (getRound().getPlayedAssistants()[i].getPlayerIndex() == playerId)
                        break;
                    i++;
                }
                if (!isANewAllowedPositionForMotherNature(getRound().getPlayedAssistants()[i].getAssistant(), islandIndex))
                    throw new TooFarIslandException();
                getGame().getGameTable().changeMotherNaturePosition(islandIndex);

                int[] influenceValues = getGame().getGameTable().calculateInfluenceValuesGivenByStudentsExceptOne( studentColor );

                for (i = 0; i < influenceValues.length; i++)
                    influenceValues[i] += getGame().getGameTable().calculateInfluenceValuesGivenByTowers()[i];
                try {
                    getGame().getGameTable().putTowerOrChangeColorIfNecessary(influenceValues);
                } catch (NoMoreTowersException e) {
                    getGame().setVictory();
                    getGame().setWinner(e.getEmptySchoolboardColor());
                } catch (ThreeOrLessIslandException e) {
                    checkEndgameAndSetTheWinner();
                }
                if(getGame().isGameEnded()) {
                    roundState = 100;
                }
                calculateNextPlayer();
                deactivateEffect(false);
            } catch (TooFarIslandException e) {
                setPlayerMessageCli(playerId, "You cannot put mother nature in the chosen island\n" + getStateMessageCli());
                setPlayerMessageGui(playerId, "You cannot put mother nature in the chosen island\n" + getStateMessageGui());
                getGame().sendGame();
            } catch (InvalidIndexException e) {
                setPlayerMessageCli(playerId, "You cannot put mother nature in the chosen island, it does not exist");
                setPlayerMessageGui(playerId, "You cannot put mother nature in the chosen island, it does not exist");
                getGame().sendGame();
            }
        } catch (PlayerNotOnTurnException e) {

        } catch (InvalidMethodException e) {
            setPlayerMessageCli(playerId, "You cannot move mother nature now");
            setPlayerMessageGui(playerId, "You cannot move mother nature now");
            getGame().sendGame();
        }
    }

}