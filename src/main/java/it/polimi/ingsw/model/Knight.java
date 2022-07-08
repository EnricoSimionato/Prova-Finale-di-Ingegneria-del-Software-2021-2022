package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.*;

public class Knight extends Character {

    private int teamWithTwoMorePoints;
    /**
     * Creates a character card with the given two values
     *
     * @param id   integer that identifies the character card
     * @param cost amount of money needed to activate the card effect
     */
    public Knight(int id, int cost) {
        super(id, cost, "Knight");
    }

    @Override
    /**
     * Activate the effect of the character decorating the current round
     * @param playerID player ID of the player which wants to activate the effect
     * @param oldRound round to decorate
     * @return the decorated round
     */
    public Round activateEffect(int playerID, Round oldRound) throws EffectCannotBeActivatedException {
        teamWithTwoMorePoints = ((oldRound.getGame().getNumberOfPlayers() == 3) ? playerID : (playerID % 2));
        return super.activateEffect(playerID, oldRound);
    }

    /**
     * adds two additional influence points at the player who played this card
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
                while (i < getPlayedAssistants().length) {
                    if (getPlayedAssistants()[i].getPlayerIndex() == playerId) break;
                    i++;
                }
                if(!isANewAllowedPositionForMotherNature(getPlayedAssistants()[i].getAssistant(), islandIndex)) throw new TooFarIslandException();
                getGame().getGameTable().changeMotherNaturePosition(islandIndex);
                int[] influenceValues = getGame().getGameTable().calculateInfluenceValuesGivenByStudents();
                for (i = 0; i < influenceValues.length; i++) {
                    influenceValues[i] += getGame().getGameTable().calculateInfluenceValuesGivenByTowers()[i];
                    if (i == teamWithTwoMorePoints) influenceValues[i] += 2;
                }
                try {
                    getRound().getGame().getGameTable().putTowerOrChangeColorIfNecessary(influenceValues);
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
                deactivateEffect(false);
                calculateNextPlayer();
            } catch (TooFarIslandException e) {
                setPlayerMessageCli(playerId, "You cannot put mother nature in the chosen island");
                setPlayerMessageGui(playerId, "You cannot put mother nature in the chosen island");
                getGame().sendGame();
            } catch (InvalidIndexException e) {
            }
        } catch (PlayerNotOnTurnException e) {

        } catch (InvalidMethodException e) {
            setPlayerMessageCli(playerId, "You cannot move mother nature now");
            setPlayerMessageGui(playerId, "You cannot move mother nature now");
            getGame().sendGame();
        }
    }
}
