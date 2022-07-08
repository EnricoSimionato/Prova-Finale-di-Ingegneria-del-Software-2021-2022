package it.polimi.ingsw.controller.message;

import java.io.Serializable;

/**
 * This message can be sent from client either from the server
 * The message is used to exchange setup information, the meaning of the message information is given by the connection state
 */
public class SetupMessage extends Message implements Serializable {
    private ConnectionState connectionState;
    private String playerMessage;

    /**
     *
     * @param connectionState
     * @param playerMessage
     */
    public SetupMessage(ConnectionState connectionState, String playerMessage) {
        this.connectionState = connectionState;
        this.playerMessage = playerMessage;
    }

    public ConnectionState getConnectionState() {
        return connectionState;
    }

    public String getMessage() {
        return playerMessage;
    }

    /**
     * Sets the type of the connection
     * @param connectionState
     */
    public void setConnectionState(ConnectionState connectionState) {
        this.connectionState = connectionState;
    }
}
