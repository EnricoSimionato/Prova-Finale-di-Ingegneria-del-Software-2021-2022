package it.polimi.ingsw.controller.message;

public class ConfigurationMessage extends Message {
    private ConnectionSide connectionSide;
    private String ip;
    private int port;

    /**
     * Creates a configuration message for sending the information of the ip and port where the code is running
     * @param connectionSide the type of sender which sends the message
     * @param ip ip of the machine where the client/server is running
     * @param port port where the client/server is running
     */
    public ConfigurationMessage(ConnectionSide connectionSide, String ip, int port) {
        this.connectionSide = connectionSide;
        this.ip = ip;
        this.port = port;
    }

    /**
     * Returns the ip of the machine which sends the message
     * @return the ip of the machine which sends the message
     */
    public String getIp() {
        return ip;
    }

    /**
     * Returns the port the process which sends the message
     * @return the port the process which sends the message
     */
    public int getPort() {
        return port;
    }

    /**
     * Returns ConnectionSide.SERVER, if the message is sent by the server; returns ConnectionSide.CLIENT, if the message is sent by the client
     * @return the type of sender which sends the message
     */
    public ConnectionSide getConnectionSide() {
        return connectionSide;
    }
}
