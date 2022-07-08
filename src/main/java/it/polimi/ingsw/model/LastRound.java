package it.polimi.ingsw.model;

public class LastRound extends Round {

    boolean studentsEnded;

    /**
     * Creates the last round of a match filling every attribute with the default value
     */
    public LastRound() { }

    /**
     * Creates the last round of a match, giving to the round the instance of the game. The player order of the round is the one given as parameter
     * @param game instance of the game of which the round is part
     * @param playerOrder playing order of the new round
     */
    public LastRound(Game game, int[] playerOrder, boolean studentsEnded) {
        super(game, playerOrder);
        this.studentsEnded = studentsEnded;
    }

    /**
     * @return true if the action phase is finished, false otherwise
     */
    @Override
    public boolean isActionPhaseEnded() {
        if(getRoundState() == 2 && (getIndexOfPlayerOnTurn() == getGame().getNumberOfPlayers() - 1)) return true;
        return false;
    }

    /**
     * Calculates who is the next player. If the player turn is over it changes the next player on turn and set the round state to the first possible action,
     * otherwise change the round state to the next permitted action
     */
    @Override
    public void calculateNextPlayer() {
        boolean roundEnded = false;
        if (isTheGameEnded()) {
            getGame().sendGame();
        } else if (isPianificationPhaseEnded()) {
            switchToActionPhase();
        } else if (isTimeToChooseTheNextStudent()) {
            setPreviousState(1);
        } else if (isActionPhaseEnded()) {
            setRoundState(100);
            checkEndgameAndSetTheWinner();
            getGame().sendGame();
        } else if (isTimeToMoveMotherNature()) {
            setPreviousState(1);
            roundState = 2;
        } else if (isTimeToChooseACloud() && studentsEnded) {
            setRoundState(1);
            setIndexOfPlayerOnTurn(getIndexOfPlayerOnTurn() + 1);
            setAlreadyPlayedCharacter(false);
        } else if (isTimeToChooseACloud() && !studentsEnded) {
            setPreviousState(2);
            roundState = 3;
        } else if (cloudHasBeenChosen()) {
            setPreviousState(3);
            roundState = 1;
            setIndexOfPlayerOnTurn(getIndexOfPlayerOnTurn() + 1);
            setAlreadyPlayedCharacter(false);
        } else {
            if (getIndexOfPlayerOnTurn() + 1 < getGame().getNumberOfPlayers()){
                setIndexOfPlayerOnTurn(getIndexOfPlayerOnTurn() + 1);
            }
        }
        if (!roundEnded) {
            setMessageToAPlayerAndWaitingMessageForOthers(getPlayerOrder()[getIndexOfPlayerOnTurn()], getStateMessageCli(), getStateMessageGui());
            getGame().sendGame();
        }
    }
}
