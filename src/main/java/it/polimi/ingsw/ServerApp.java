package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;

import java.io.IOException;

public class ServerApp {

    public static void main( String[] args ) {
        Server server;
        try {
            int port = 50000;
            if (args.length >= 1) {
                try {
                    port = Integer.valueOf(args[0]);
                    System.out.println("The chosen port is the number " + port);
                } catch (NumberFormatException e) {
                    System.out.println("You insert a wrong information. The default port is 50000");
                }
            }
            server = new Server(port);
            server.run();
        } catch (IOException e) {
            System.err.println("Impossible to start the server: " + e.getMessage() + "!");
        }
    }
}
