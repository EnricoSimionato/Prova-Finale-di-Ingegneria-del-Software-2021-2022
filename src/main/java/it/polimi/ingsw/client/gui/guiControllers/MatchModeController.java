package it.polimi.ingsw.client.gui.guiControllers;

import it.polimi.ingsw.controller.message.ConnectionState;
import it.polimi.ingsw.controller.message.Message;
import it.polimi.ingsw.controller.message.SetupMessage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MatchModeController extends GuiController {

    @FXML
    Label matchModeMessage;

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
    public void update(Message message) {
        if(message instanceof SetupMessage) {
            SetupMessage setupMessage = (SetupMessage) message;
            if (setupMessage.getConnectionState() == ConnectionState.MATCHMODE) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        matchModeMessage.setText(setupMessage.getMessage());
                    }
                }
                );
            } else if (setupMessage.getConnectionState() == ConnectionState.NUMBEROFPLAYERS) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/matchPlayersScene.fxml"));
                        try {
                            Parent root = loader.load();
                            GuiController playersController = loader.getController();
                            getClient().removeObserver(MatchModeController.this);
                            playersController.setStage(getStage());
                            playersController.setScene(new Scene(root));
                            playersController.setRoot(root);
                            getClient().addObserver(playersController);
                            playersController.setClient(getClient());
                            playersController.getStage().setScene(playersController.getScene());
                            playersController.getStage().show();
                        } catch(IOException e) {
                        //Error
                        }
                    }
                }
                );

            } else {
                //Server Error
            }
        } else {
            //Error
        }
    }

    public void normalMode() {
        getClient().asyncWriteToSocket(new SetupMessage(ConnectionState.MATCHMODE, "0"));
    }

    public void expertMode() {
        getClient().asyncWriteToSocket(new SetupMessage(ConnectionState.MATCHMODE, "1"));
    }
}
