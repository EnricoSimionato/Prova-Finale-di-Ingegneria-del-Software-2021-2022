package it.polimi.ingsw.client.gui.guiControllers;

import it.polimi.ingsw.controller.message.Message;
import it.polimi.ingsw.controller.message.TerminatorMessage;
import javafx.application.Platform;

import java.net.URL;
import java.util.ResourceBundle;

public class TerminatorController extends GuiController {

    /**
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * terminates the game
     * @param message
     */
    @Override
    public void update(Message message) {
        try {
            Thread.sleep(7000);
            Platform.exit();
            System.exit(0);
        } catch (InterruptedException e) {
            Platform.exit();
            System.exit(1);
        }
    }

    /**
     * terminates the game
     */
    public void finishAll() {
        try {
            Thread.sleep(50000);
            Platform.exit();
            System.exit(0);
        } catch (InterruptedException e) {
            Platform.exit();
            System.exit(1);
        }
    }
}
