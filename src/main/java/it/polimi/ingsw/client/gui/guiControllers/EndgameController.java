package it.polimi.ingsw.client.gui.guiControllers;

import it.polimi.ingsw.controller.message.GameMessage;
import it.polimi.ingsw.controller.message.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class EndgameController extends GuiController {

    @FXML Label winners;
    @FXML
    Label message;
    @FXML
    ImageView image;
    private GameMessage board;
    private int myPlayerId;

    /**
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * returns the board parameter
     * @return
     */
    public GameMessage getBoard() {
        return board;
    }

    /**
     * sets the board and eventually displays who is the winner
     * @param board
     */
    public void setBoard(GameMessage board) {
        this.board = board;
        myPlayerId = board.getPlayerId();
        System.out.println("MyPlayerId " + board.getPlayerId());
        for (int i = 0; i < board.getWinnersIndexes().size(); i++) {
            System.out.println("Indice : " + board.getWinnersIndexes().get(i));
            System.out.println("Nome : " + board.getWinnersNicknames().get(i));
        }
        if(board.isVictory()) {
            if(board.getWinner() != null) {
                switch (board.getWinner()) {
                    case WHITE:
                        image.setImage(new Image("/images/Board/Schoolboards/Towers/whiteTower.png"));
                        break;
                    case BLACK:
                        image.setImage(new Image("/images/Board/Schoolboards/Towers/blackTower.png"));
                        break;
                    case GREY:
                        image.setImage(new Image("/images/Board/Schoolboards/Towers/greyTower.png"));
                        break;
                }
                image.setFitHeight(100);
                image.setPreserveRatio(true);
            }
            if(board.getWinnersIndexes().contains(myPlayerId)) {
                message.setText("You win!");
                winners.setText("You are the master of Eriantys world");
            } else {
                message.setText("You lose!");
                winners.setText("The winner is " + board.getWinnersNicknames().get(0) + "\nPlay again to improve your abilities and become an Eriantys master");
            }
            if (board.getNumberOfPLayers() == 4)
                winners.setText("The winners are " + board.getWinnersNicknames().get(0) + " and " + board.getWinnersNicknames().get(1));
        } else if (board.isDraw()) {
            message.setText("The match is draw");
            winners.setText("You and your opponent are at the same level. Play again to improve your abilities and become an Eriantys master");
        }
    }

    /**
     *
     * @param message
     */
    @Override
    public void update(Message message) {

    }
}
