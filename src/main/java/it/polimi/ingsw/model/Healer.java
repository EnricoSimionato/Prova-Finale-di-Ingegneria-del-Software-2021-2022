package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.*;

public class Healer extends Character {
    private int prohibition;

    public Healer(int id, int cost) {
        super(id, cost, "Healer");
        this.prohibition = 4;
    }

    /**
     * Does not calculate the influence of the island signed with the prohibition mark
     * @param playerId
     * @param parameter
     */
    @Override
    public void doYourJob(int playerId, int parameter) {
        if (getRoundState() == 4) {
            try {
                if (parameter < 0 || parameter > 11) throw new InvalidIndexException("The island doesn't exist!\nChoose another one");
                getGame().getGameTable().getIslandByIndex(parameter).setProhibition();
                prohibition--;
                setRoundState(getOldState());
                setPlayerMessageCli(playerId, getStateMessageCli());
                setPlayerMessageGui(playerId, getStateMessageGui());
            } catch (InvalidIndexException | IslandAlreadyForbiddenException e) {
                setPlayerMessageCli(playerId, e.getMessage());
                setPlayerMessageGui(playerId, e.getMessage());
                getGame().sendGame();
            }
        }
    }

    /**
     * calls the setAlreadyPlayedCharacter round method with the passed parameter
     * @param alreadyPlayedCharacter
     */
    @Override
    public void setAlreadyPlayedCharacter(boolean alreadyPlayedCharacter) {
        getRound().setAlreadyPlayedCharacter(alreadyPlayedCharacter);
    }

    /**
     * activates the effect of this card setting the proper messages and setting the round state at 4.
     * It catches and handles PlayerNotOnTurnException, InvalidMethodException, EffectCannotBeActivatedException, NotEnoughCoins
     * @param playerId
     * @param indexCard
     */
    @Override
    public void activateEffect(int playerId, int indexCard) {
        try {
            checkPlayerOnTurn(playerId);
            if (getRoundState() <= 0 || getRoundState() >= 4) throw new InvalidMethodException();
            if (getAlreadyPLayedCharacter()) throw new AlreadyPlayedCharcaterException();
            setOldState(getRoundState());
            getGame().activateEffect(playerId, indexCard);
            setAlreadyPlayedCharacter(true);
        } catch (PlayerNotOnTurnException e) {
            // The player is not the current player so the round tate doesn't change
        } catch (AlreadyPlayedCharcaterException e) {
            setPlayerMessageCli(playerId, "You already played a character\n" + getRound().getStateMessageCli());
            setPlayerMessageGui(playerId, "You already played a character\n" + getRound().getStateMessageGui());
            getGame().sendGame();
        } catch (InvalidMethodException e) {
            setPlayerMessageCli(playerId, "You can't play a character during the pianification phase");
            setPlayerMessageGui(playerId, "You can't play a character during the pianification phase");
            getGame().sendGame();
        } catch (EffectCannotBeActivatedException e) {
            setPlayerMessageCli(playerId, e.getMessage() + "\n" + getRound().getStateMessageCli() );
            setPlayerMessageGui(playerId, e.getMessage() + "\n" + getRound().getStateMessageGui() );
            getGame().sendGame();
        } catch (NotEnoughCoins e) {
            setPlayerMessageCli(playerId, "Not enougth coin to play the character\n" + getStateMessageCli());
            setPlayerMessageGui(playerId, "Not enougth coin to play the character\n" + getStateMessageGui());
            getGame().sendGame();
        }
    }

    /**
     * activates the effect of this card setting the proper messages and setting the round state at 4
     * @param playerID
     * @param round
     * @return
     * @throws EffectCannotBeActivatedException
     */
    @Override
    public Round activateEffect(int playerID, Round round) throws EffectCannotBeActivatedException {
        if (prohibition <= 0) throw new EffectCannotBeActivatedException("The healer cannot be activated: there are no prohibition cards");
        round.getGame().getPlayer(playerID).setPlayerMessageCli("Select an island where you want to put the prohibition card");
        round.getGame().getPlayer(playerID).setPlayerMessageGui("Select an island where you want to put the prohibition card");
        setRoundState(4);
        return this;
    }

    /**
     * switches to PianificationPhase after setting its order of playing and adding the students on clouds
     */
    @Override
    public void switchToPianificationPhase() {
        setPianificationPhaseOrder();
        try {
            getGame().getGameTable().addStudentsOnClouds();
            setRound(new Round(getGame(), getPlayerOrder()));
        } catch (EmptyBagException e) {
            setRound(new LastRound(getGame(), getPlayerOrder(), true));
        }
        getGame().setRound(this);
    }

    /**
     * changes the position of mother nature not calculating the island with prohibition
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
                    if (getRound().getPlayedAssistants()[i].getPlayerIndex() == playerId) break;
                    i++;
                }
                if (!isANewAllowedPositionForMotherNature(getRound().getPlayedAssistants()[i].getAssistant(), islandIndex)) throw new TooFarIslandException();
                getGame().getGameTable().changeMotherNaturePosition(islandIndex);
                if(!getGame().getGameTable().getIslandByIndex(getGame().getGameTable().getMotherNaturePosition()).isForbidden()) {
                    int[] influenceValues = getGame().getGameTable().calculateInfluenceValuesGivenByStudents();
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
                    if (getGame().isGameEnded()) {
                        roundState = 100;
                    }
                } else {
                    getGame().getGameTable().getIslandByIndex(getGame().getGameTable().getMotherNaturePosition()).resetProhibition();
                    prohibition++;
                }
                calculateNextPlayer();
            } catch (TooFarIslandException e) {
                setPlayerMessageCli(playerId, "You cannot put mother nature in the chosen island\n" + getRound().getStateMessageCli());
                setPlayerMessageGui(playerId, "You cannot put mother nature in the chosen island\n" + getRound().getStateMessageGui());
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

    /**
     * returns the prohibition attribute
     * @return
     */
    public int getNumberOfProibitionCard() {
        return prohibition;
    }
}
