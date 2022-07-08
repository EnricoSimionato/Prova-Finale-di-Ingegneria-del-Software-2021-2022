package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.message.GameMessage;
import it.polimi.ingsw.controller.message.PlayerMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

public abstract class View extends Observable<PlayerMessage> implements Observer<Game> {

    private int playerId;
    public View(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId(){
        return playerId;
    }

    public abstract void update(Game model);

    void handleMove(PlayerMessage message) {
        notify(message);
    }
}