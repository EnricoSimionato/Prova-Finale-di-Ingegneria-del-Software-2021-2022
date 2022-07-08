package it.polimi.ingsw.controller.message;

public class TerminatorMessage extends Message {
    private String message;

    /**
     * message used to terminate the game
     * @param message
     */
    public TerminatorMessage(String message) {
        this.message = message;
    }

    /**
     * returns the message attribute
     * @return
     */
    public String getMessage() {
        return message;
    }
}
