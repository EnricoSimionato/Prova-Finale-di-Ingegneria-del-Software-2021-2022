package it.polimi.ingsw.client.gui.guiControllers;

import it.polimi.ingsw.controller.message.GameMessage;
import it.polimi.ingsw.controller.message.Message;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WaitingController extends GuiController {

    /**
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * changes the scene if necessary based on the message passed by parameter
     * @param message
     */
    @Override
    public void update(Message message) {
        if (message instanceof GameMessage){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/boardPlayers.fxml"));
                    try {
                        Parent root = loader.load();
                        GuiController boardController = loader.getController();
                        getClient().removeObserver(WaitingController.this);
                        boardController.setStage(getStage());
                        boardController.setScene(new Scene(root));
                        boardController.setRoot(root);
                        getClient().addObserver(boardController);
                        boardController.setClient(getClient());
                        boardController.getStage().setScene(boardController.getScene());
                        ((BoardController) boardController).setBoard((GameMessage) message);
                        ((BoardController) boardController).adaptSceneToPlayers();
                        boardController.getStage().show();
                    } catch(IOException e) {
                        //Error
                    }
                }
            });
        }
    }
}
