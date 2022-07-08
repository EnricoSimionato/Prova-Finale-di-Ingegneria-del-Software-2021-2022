package it.polimi.ingsw.client.gui.guiControllers;

import it.polimi.ingsw.ClientApp;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.gui.GuiClient;
import it.polimi.ingsw.controller.message.ConnectionState;
import it.polimi.ingsw.controller.message.Message;
import it.polimi.ingsw.controller.message.SetupMessage;
import it.polimi.ingsw.controller.message.TerminatorMessage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends GuiController {

    @FXML
    private Label nicknameMessage;
    @FXML
    private TextField nicknameBucket;
    @FXML
    private Button confirmButton;

    /**
     * sets the client if there is a connection
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Client client = new Client(ClientApp.ip, ClientApp.port);
            setClient(client);
            new Thread(getClient()).start();
            getClient().addObserver(this);
        } catch (Exception e) {
            nicknameBucket.setVisible(false);
            confirmButton.setVisible(false);
            nicknameMessage.setTranslateY(50);
            nicknameMessage.setText("Unable to connect to the server");
            //System.out.println("Unable to connect to the server");
        }
    }

    /**
     * changes the scene if necessary based on the message passed by parameter
     * @param message
     */
    @Override
    public void update(Message message) {
        if(message instanceof SetupMessage) {
            SetupMessage setupMessage = (SetupMessage) message;
            if (setupMessage.getConnectionState() == ConnectionState.LOGIN) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        nicknameMessage.setText(setupMessage.getMessage());
                    }
                }
                );
            } else if (setupMessage.getConnectionState() == ConnectionState.MATCHMODE) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/matchModeScene.fxml"));
                        try {
                            Parent root = loader.load();
                            GuiController modeController = loader.getController();
                            getClient().removeObserver(LoginController.this);
                            modeController.setStage(getStage());
                            modeController.setScene(new Scene(root));
                            modeController.setRoot(root);
                            getClient().addObserver(modeController);
                            modeController.setClient(getClient());
                            modeController.getStage().setScene(modeController.getScene());
                            modeController.getStage().show();
                        } catch(IOException e) {
                            //Error
                        }
                    }
                }
                );

            } else {
                //Server error
            }
        } else {
            //Errore
        }
    }

    /**
     * Gets the nickname chosen by the user
     */
    public void login() {
        String nickname = nicknameBucket.getText();
        getClient().asyncWriteToSocket(new SetupMessage(ConnectionState.LOGIN, nickname));
    }
}
