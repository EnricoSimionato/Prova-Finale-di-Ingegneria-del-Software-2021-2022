package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.gui.guiControllers.GuiController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;

public class GuiClient extends Application {

    private Client client;

    /**
     * starts the match setting all the starting elements in the initial scene
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginScene.fxml"));
        Parent root = loader.load();
        stage.setTitle("Eriantys");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        Image logo = new Image("/images/BLLoghi.png", 100, 300, true, false);
        stage.getIcons().add(logo);
        GuiController loginController = loader.getController();
        loginController.setStage(stage);
        loginController.setScene(scene);
        loginController.setRoot(root);

        client = loginController.getClient();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        stage.show();
    }

    /**
     * returns the client parameter
     * @return
     */
    public Client getClient() {
        return client;
    }
}
