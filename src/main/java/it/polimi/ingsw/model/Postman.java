package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.*;

public class Postman extends Character {

    /**
     * Creates a character card with the given two values
     *
     * @param id   integer that identifies the character card
     * @param cost amount of money needed to activate the card effect
     */
    public Postman(int id, int cost) {
        super(id, cost, "Postman");
    }

    /**
     * Overrides the method of Round forcing it to allow a movement of one or two island more than the value the assistant allows
     * @param assistant assistant played by the player
     * @param islandIndex island where the player wants to move mother nature
     * @return true if the movement is legal, false otherwise
     */
    @Override
    public boolean isANewAllowedPositionForMotherNature(Assistant assistant, int islandIndex) {
        return super.isANewAllowedPositionForMotherNature(assistant, (islandIndex + super.getGame().getGameTable().getNumberOfIslands() - 2) % super.getGame().getGameTable().getNumberOfIslands()) ||
                super.isANewAllowedPositionForMotherNature(assistant, (islandIndex + super.getGame().getGameTable().getNumberOfIslands() - 1) % super.getGame().getGameTable().getNumberOfIslands());
    }

    /**
     * Change mother nature position giving the possibility to move it two more island additional to the given number by Assistant played
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
                if (!isANewAllowedPositionForMotherNature(getPlayedAssistants()[i].getAssistant(), islandIndex)) throw new TooFarIslandException();
                getGame().getGameTable().changeMotherNaturePosition(islandIndex);
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
