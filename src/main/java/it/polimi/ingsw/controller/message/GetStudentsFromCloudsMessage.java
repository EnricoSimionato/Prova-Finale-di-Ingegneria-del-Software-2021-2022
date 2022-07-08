package it.polimi.ingsw.controller.message;

import it.polimi.ingsw.model.Game;

import java.io.Serializable;

public class GetStudentsFromCloudsMessage extends PlayerMessage implements Serializable {
    private static final long serialVersionUID = 5L;
    private int cloudIndex;

    /**
     * message which permits to get students from the selected cloud
     * @param playerId
     * @param cloudIndex
     */
    public GetStudentsFromCloudsMessage(int playerId, int cloudIndex) {
        super(playerId);
        this.cloudIndex = cloudIndex;
    }

    /**
     * calls the model method to get the students from cloud
     * @param game
     */
    @Override
    public void performMove(Game game) { game.getRound().getStudentsFromCloud(getPlayerId(), cloudIndex); }

}
