package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.message.*;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exception.ImpossibleToStartTheMatchException;
import it.polimi.ingsw.view.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static int port = 50000;
    private ServerSocket serverSocket;
    private boolean active;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private int nextMatchId;
    private List<Match> pendingMatches = new ArrayList<>();
    private List<Match> runningMatches = new ArrayList<>();
    /**
     * Creates a server instance with a server socket accepting connection on port 40000
     * @throws IOException if there is an input/output error
     */
    public Server(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        active = true;
    }

    /**
     * Returns the state of the server
     * @return state of the server: true, if active; false, if not active
     */
    public synchronized boolean isActive() {
        return active;
    }

    /**
     * Modifies the state of the server
     * @param active new state of the server
     */
    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Accepts multiple connections from clients and creates threads that manage the clients interactions
     */
    public void run() {
        int connections = 0;
        System.out.println("Server is running");
        Thread ipSender = new Thread(() -> {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket(40000);
                socket.setBroadcast(true);
                byte[] inputBuffer = new byte[1024];
                while (isActive()) {
                    DatagramPacket receivedPacket = new DatagramPacket(inputBuffer, inputBuffer.length);
                    socket.receive(receivedPacket);
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputBuffer);
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    Object message = objectInputStream.readObject();
                    if(message instanceof ConfigurationMessage && ((ConfigurationMessage) message).getConnectionSide() == ConnectionSide.CLIENT) {
                        String ip = ((ConfigurationMessage) message).getIp();
                        int port = ((ConfigurationMessage) message).getPort();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                        objectOutputStream.writeObject(new ConfigurationMessage(ConnectionSide.SERVER, calculateMyIp(), Server.port));
                        DatagramPacket packet = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size(), InetAddress.getByName(ip), port);
                        socket.send(packet);
                    }
                }
            } catch (SocketException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println("Morto");
                if(socket != null)
                    socket.close();
            }
        });
        ipSender.start();
        while(isActive()) {
            try {
                Socket newSocket = serverSocket.accept();
                System.out.println("Received connection " + connections + "");
                connections++;
                ClientSocketConnection socketConnection = new ClientSocketConnection(newSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
        try {
            ipSender.join();
        } catch (InterruptedException e) {
            System.out.println("Error joining the ip thread");
        }
    }

    private static String calculateMyIp() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    ip = addr.getHostAddress();
                    //System.out.println(iface.getDisplayName() + " " + ip);
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return ip;
    }

    /**
     *
     * @param clientConnection
     * @param numberOfPlayers
     * @param gameMode
     */
    public synchronized void lobby(GameMode gameMode, int numberOfPlayers, String playerNickname, ClientConnection clientConnection) {
        Match match = searchForMatch(gameMode, numberOfPlayers);
        if (match == null) {
            System.out.println("Just create a match with the id : " + nextMatchId);
            pendingMatches.add(new Match(nextMatchId++, gameMode, numberOfPlayers, playerNickname, clientConnection));
            clientConnection.send(new SetupMessage(ConnectionState.SUCCESS, "Waiting for a match. Get ready to play..."));
        } else {
            match.addPlayer(clientConnection, playerNickname);
            if (match.getNumberOfPlayers() == match.getSockets().size()) {

                View[] playerView = new RemoteView[match.getNumberOfPlayers()];
                for (int i = 0; i < match.getNumberOfPlayers(); i++) {
                    playerView[i] = new RemoteView(i, match.getPlayerNicknames().get(i), match.getSockets().get(i));
                }
                for (int i = 0; i < match.getNumberOfPlayers(); i++) {
                    System.out.println("Player : " + i + " " + match.getPlayerNicknames().get(i) + " " + match.getSockets().get(i).toString());
                }
                System.out.println(match.getGameMode());
                Game model;
                try {
                    if (match.getGameMode() == GameMode.NORMAL)
                        model = new Game(match.getNumberOfPlayers(), match.getPlayerNicknames());
                    else
                        model = new ExpertGame(match.getNumberOfPlayers(), match.getPlayerNicknames());

                    Controller controller = new Controller(model);

                    match.setModel(model);

                    for (int i = 0; i < match.getNumberOfPlayers(); i++) {
                        model.addObserver(playerView[i]);
                        playerView[i].addObserver(controller);

                        GameMessage displayedBoard = new GameMessage(model, i);
                        match.getSockets().get(i).send(displayedBoard);
                    }

                    System.out.println("The match " + match.getMatchId() + " starts");
                    System.out.println("The starting order of match " + match.getMatchId() + " is " + model.getRound().getPlayerOrder().toString());

                    runningMatches.add(match);
                    pendingMatches.remove(match);
                } catch (ImpossibleToStartTheMatchException e) {
                    e.printStackTrace(); ///// da sistemare e invivare un messagggio
                }
            } else {
                clientConnection.send(new SetupMessage(ConnectionState.SUCCESS, "The configuration is done. Get ready to play..."));
            }
        }
    }

    /**
     * Searches
     * @param gameMode
     * @param numberOfPlayers
     * @return
     */
    public Match searchForMatch(GameMode gameMode, int numberOfPlayers) {
        if(pendingMatches.size() == 0) return null;
        for (Match match : pendingMatches) {
            if (match.getGameMode() == gameMode && match.getNumberOfPlayers() == numberOfPlayers)
                return match;
        }
        return null;
    }

    /**
     *
     * @param clientSocketConnection
     * @return
     */
    public Match getMyMatch(ClientConnection clientSocketConnection){
        Match myMatch = null;
        for (Match m : runningMatches)
            if (m.getSockets().contains(clientSocketConnection))
                myMatch = m.getMatch();
        return myMatch;
    }

    /**
     *
     * @param clientSocketConnection
     * @return
     */
    public int getMyId(ClientConnection clientSocketConnection) {
        int playerId = -1;
        for (Match match : runningMatches) {
            if (match.getSockets().contains(clientSocketConnection)) {
                playerId = match.getSockets().indexOf(clientSocketConnection);
            }
        }
        return playerId;
    }

    /**
     *
     * @param clientSocketConnection
     */
    public void exitingPlayer(ClientConnection clientSocketConnection) {
        Match myMatch = getMyMatch(clientSocketConnection);

        if (myMatch != null) {
            for (ClientConnection clientConnection : myMatch.getSockets()) {
                if (clientConnection != null && clientConnection != clientSocketConnection)
                    clientConnection.closeConnection();
            }
        }

        pendingMatches.remove(myMatch);
        runningMatches.remove(myMatch);
    }

    /**
     *
     * @return
     */
    public List<Match> getAllMatchesOnServer() {
        List<Match> matches = new ArrayList<>();
        matches.addAll(pendingMatches);
        matches.addAll(runningMatches);

        return matches;
    }
}
