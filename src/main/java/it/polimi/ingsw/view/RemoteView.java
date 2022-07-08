package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.message.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;

import java.awt.event.ActionEvent;

public class RemoteView extends View {
    private String playerNickname;
    private ClientConnection clientConnection;
    private PlayerMessage playerMessage;

    private class MessageReceiver implements Observer<PlayerMessage> {
        @Override
        public void update(PlayerMessage message) {
            try{
                handleMove(message);
            }catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){

            }
        }

    }
    public RemoteView(int playerId, String playerNickname, ClientConnection clientConnection) {
        super(playerId);
        this.playerNickname = playerNickname;
        this.clientConnection = clientConnection;
        clientConnection.addObserver(new MessageReceiver());
    }

    @Override
    public void update(Game model) {
        GameMessage message = new GameMessage(model, super.getPlayerId());
        /*if (model.getPlayer(super.getPlayerId()).isError()) {
            message = new ErrorMessage(model, super.getPlayerId());
            model.getPlayer(super.getPlayerId()).setError(false);
        } else if  (model.getRound().getRoundState() == 0) {
            message = new PostPlayAssistantMessage(model, super.getPlayerId());
            //System.out.println("PostAssistant");
        } else if (model.getRound().getRoundState() == 1 && model.getRound().getPreviousState() == 0) {
            message = new PostPlayAssistantMessage(model, super.getPlayerId());
            //System.out.println("PostAssistant");
        } else if (model.getRound().getRoundState() == 1 && model.getRound().getPreviousState() == 3) {
            //System.out.println("PostCloudMessage");
            message = new PostGetStudentsFromCloudsMessage(model, super.getPlayerId());
        } else if ((model.getRound().getRoundState() == 1 || model.getRound().getRoundState() == 2) && !model.getRound().isIslandMessage() && model.getRound().getPreviousState() == 1) {
            message = new PostAddStudentOnTableMessage(model, super.getPlayerId());
            //System.out.println("PostAddStudentOnTable");
        } else if ((model.getRound().getRoundState() == 1 || model.getRound().getRoundState() == 2) && !model.getRound().isIslandMessage() && model.getRound().getPreviousState() == 1) {
            message = new PostAddStudentOnIslandMessage(model, super.getPlayerId());
            //System.out.println("PostAddStudentsOnIsland");
        } else if (model.getRound().getRoundState() == 3)  {
            message = new PostChangeMotherNaturePosition(model, super.getPlayerId());
            //System.out.println("PostChangeMotherNaturePosition");
        }
        else if (model.getRound().getRoundState() >= 4) {
            message = new GameMessage(model, super.getPlayerId());
            //System.out.println("PostCharacter o altro, ad esempio Endgame");
        } else {
            //System.out.println("State : " + model.getRound().getRoundState() + " round class : " + model.getRound().getClass() + " (RemoteView)");
        }*/
        if (message != null)
            clientConnection.asyncSend(message);
    }

    void handleMove(PlayerMessage message) {
        playerMessage = message;
        message.setPlayerId(super.getPlayerId());
        notify(message);
    }
}
