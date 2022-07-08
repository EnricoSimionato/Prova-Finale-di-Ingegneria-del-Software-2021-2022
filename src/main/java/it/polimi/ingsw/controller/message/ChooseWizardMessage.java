package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.model.Game;
import java.io.Serializable;

public class ChooseWizardMessage extends PlayerMessage implements Serializable {

    //private static final long serialVersionUID = 7L;

    private int wizardChoose;
    public ChooseWizardMessage(int playerId, int wizardChoose) {
        super(playerId);
        this.wizardChoose = wizardChoose;
    }

    @Override
    public void performMove(Game game) {
        //game.getRound().chooseWizard(getPlayerId(), wizardChoose);
    }
}
