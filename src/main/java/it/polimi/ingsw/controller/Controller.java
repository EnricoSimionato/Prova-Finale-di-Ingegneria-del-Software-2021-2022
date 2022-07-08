package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.controller.message.PlayerMessage;
import it.polimi.ingsw.observer.Observer;

public class Controller implements Observer<PlayerMessage> {
    private Game model;

    /**
     * initialize the model parameter
     * @param model
     */
    public Controller(Game model) {
        this.model = model;
    }

    /**
     * calls the method performMove of the playerMessage passed by parameter
     * @param playerMessage
     */
    public void performMove(PlayerMessage playerMessage) {
        playerMessage.performMove(model);
    }

    /**
     * calls the method performMove(messsage) with message passed by parameter
     * @param message
     */
    @Override
    public void update(PlayerMessage message) {
        performMove(message);
    }
}
