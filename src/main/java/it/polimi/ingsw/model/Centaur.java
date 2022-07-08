package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.*;

public class Centaur extends Character {

    /**
     * Creates a character card with the given two values
     * @param id   integer that identifies the character card
     * @param cost amount of money needed to activate the card effect
     */
    public Centaur(int id, int cost) {
        super(id, cost, "Centaur");
    }

    /**
     * Implements a different logic for the influence computation. When the centaur is active and the influence has to be computed, the towers will be ignored
     * @param playerId player ID of the player which want to make the move
     * @param islandIndex index of the island on which the player wants to move mother nature
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
                e.printStackTrace();
            }
        } catch (PlayerNotOnTurnException e) {
            e.printStackTrace();
        } catch (InvalidMethodException e) {
            setPlayerMessageCli(playerId, "You cannot move mother nature now");
            setPlayerMessageGui(playerId, "You cannot move mother nature now");
            getGame().sendGame();
        }
    }
}
