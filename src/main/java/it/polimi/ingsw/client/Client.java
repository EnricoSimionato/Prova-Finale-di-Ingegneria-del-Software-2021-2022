package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.message.*;
import it.polimi.ingsw.observer.Observable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

public class Client extends Observable<Message> implements Runnable {

    private String ip;
    private int port;
    private boolean active = true;
    private int playerId = 0;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private GameMessage actualBoard;

    /**
     * client constructor, initialize socket, outputStream, inputStream attributes
     * @param ip
     * @param port
     * @throws Exception
     */
    public Client(String ip, int port) throws Exception {
        socket = new Socket(ip, port);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        System.out.println("Connection established");
    }

    /**
     * runs the inputThread calling the asyncReadFromSocket method
     */
    @Override
    public void run() {
        try {
            Thread inputThread = asyncReadFromSocket(inputStream);
            inputThread.join();
        } catch(InterruptedException | NoSuchElementException e) {
            System.out.println("Connection closed from the client side");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Unable to close the socket");
            }

        }
    }

    /**
     * returns the attribute active
     * @return
     */
    public synchronized boolean isActive(){
        return active;
    }

    /**
     * sets the attribute active
     * @param active
     */
    public synchronized void setActive(boolean active){
        this.active = active;
    }

    /**
     * runs the thread that will be used to read from socket
     * @param socketIn
     * @return
     */
    public Thread asyncReadFromSocket(final ObjectInputStream socketIn){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        Object inputObject = socketIn.readObject();
                        if (inputObject instanceof TerminatorMessage) {
                            Client.this.notify((TerminatorMessage) inputObject);
                        } else if(inputObject instanceof SetupMessage) {
                            if(inputObject  != null) {
                                Client.this.notify((SetupMessage) inputObject);
                            }
                        } else if (inputObject instanceof GameMessage) {

                            if(inputObject  != null) {
                                actualBoard = ((GameMessage) inputObject);
                                Client.this.notify(actualBoard);
                            } else {

                            }
                        } else {
                            System.out.println(inputObject.getClass());
                            if(inputObject instanceof String)
                                System.out.println((String) inputObject);
                            throw new IllegalArgumentException();
                        }
                    }
                } catch (Exception e){
                    closeAll();
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    /**
     * runs the thread that will be used to write to socket the messages passed by parameter
     * @param playerMessage
     * @return
     */
    public Thread asyncWriteToSocket(Message playerMessage) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isActive()) {
                        outputStream.writeObject(playerMessage);
                        outputStream.flush();
                        outputStream.reset();
                    }
                } catch(Exception e) {
                    closeAll();
                    setActive(false);
                }
            }
        });
        thread.start();
        return thread;
    }

    /**
     * closes input and output streams
     */
    public void closeAll() {
        try {
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
    }
}
