package it.polimi.ingsw;

import it.polimi.ingsw.client.cli.ClientCli;
import it.polimi.ingsw.client.gui.GuiClient;
import it.polimi.ingsw.controller.message.ConfigurationMessage;
import it.polimi.ingsw.controller.message.ConnectionSide;
import javafx.application.Application;

import java.io.*;
import java.net.*;
import java.util.Enumeration;

public class ClientApp {

    public static String ip = "127.0.0.1";
    public static int port = 50000;
    private static String mode = "gui";

    /**
     * Main method used for launching the client needed for playing Eriantys
     * @param args input parameters
     */
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            if (args.length % 2 == 0 && args.length <= 6) {
                int occurrences = obtainTheMode(args);
                if (occurrences > 1 || occurrences < 0) throw new InvalidFormatException();
                if (occurrences == 0) mode = "gui";
                occurrences = 0;
                occurrences = obtainIpAddress(args);
                if (occurrences > 1) throw new InvalidFormatException();
                if (occurrences == 1 && ip.equals("local")) ip = "127.0.0.1";
                if (occurrences == 0) {
                    String myIp = calculateMyIp();
                    socket = new DatagramSocket();
                    socket.setBroadcast(true);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    objectOutputStream.writeObject(new ConfigurationMessage(ConnectionSide.CLIENT, myIp, socket.getLocalPort()));
                    DatagramPacket packet = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size(), InetAddress.getByName("255.255.255.255"), 40000);
                    socket.send(packet);

                    byte[] inputBuffer = new byte[1024];
                    while (true) {
                        DatagramPacket receivedPacket = new DatagramPacket(inputBuffer, inputBuffer.length);
                        socket.receive(receivedPacket);
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputBuffer);
                        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                        Object message = objectInputStream.readObject();
                        if (message instanceof ConfigurationMessage && ((ConfigurationMessage) message).getConnectionSide() == ConnectionSide.SERVER) {
                            ip = ((ConfigurationMessage) message).getIp();
                            port = ((ConfigurationMessage) message).getPort();
                            break;
                        }
                    }
                    if (socket != null)
                        socket.close();
                }
                occurrences = 0;
                occurrences = obtainPortNumber(args);
                if (occurrences > 1) throw new InvalidFormatException();
                if (occurrences == 0) port = 50000;

                if (mode.equals("cli")) {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    ClientCli client = new ClientCli(ip, port);
                    try {
                        client.run();
                    } catch (IOException e) {
                        System.out.println("The server is currently not running on the given address");
                    }
                } else if (mode.equals("gui")) {
                    Application.launch(GuiClient.class);
                } else {
                    System.out.println("You are not sending the correct information. The format is: 'java -jar path_to_file/eriantys-client.jar [-mode (gui | cli)] [-ip (ip_address | local)] [-port port_number]'");
                }
            } else {
                System.out.println("You are not sending the correct information. The format is: 'java -jar path_to_file/eriantys-client.jar [-mode (gui | cli)] [-ip (ip_address | local)] [-port port_number]'");
            }
        } catch (InvalidFormatException e) {
            System.out.println("You are not sending the correct information. The format is: 'java -jar path_to_file/eriantys-client.jar [-mode (gui | cli)] [-ip (ip_address | local)] [-port port_number]'");
        } catch (NumberFormatException e) {
            System.out.println("You insert a wrong information. The port must be a number");
        } catch (SocketException e) {
            System.out.println("Some connection error occurs, retry later");
        } catch (IOException e) {
            System.out.println("Some connection error occurs, retry later");
        } catch (ClassNotFoundException e) {
            System.out.println("Some connection error occurs, retry later");
        } finally {
            if (socket != null)
                socket.close();
        }
    }

    /**
     * Calculates the ip of the device which is running the client
     * @return the ip address of the device from which the player is trying to connect to the server
     */
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
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return ip;
    }

    /**
     * Extracts the mode selection of the player ('gui' or 'cli') from the input string written on the command line
     * @param arguments string written on the command line by who launched the client
     * @return number of times the string ' -mode ' appear in the input string. -1 if the string next to ' -mode ' is different from gui and cli
     */
    private static int obtainTheMode(String[] arguments) {
        int modeOccurrences = 0;
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].equals("-mode")) {
                if (arguments[i + 1].equals("gui") || arguments[i + 1].equals("cli")) {
                    mode = arguments[i + 1];
                    modeOccurrences++;
                } else {
                    modeOccurrences = -1;
                    break;
                }
            }
        }
        return modeOccurrences;
    }

    /**
     * Extracts the ip of the Eriantys server written by the player on the command line
     * @param arguments string written on the command line by who launched the client
     * @return number of times the string ' -ip ' appear in the input string
     */
    private static int obtainIpAddress(String[] arguments) {
        int ipAddressOccurrences = 0;
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].equals("-ip")) {
                ip = arguments[i + 1];
                ipAddressOccurrences++;
            }
        }
        return ipAddressOccurrences;
    }

    /**
     * Extracts the port of the Eriantys server written by the player on the command line
     * @param arguments string written on the command line by who launched the client
     * @return number of times the string ' -port ' appear in the input string
     */
    private static int obtainPortNumber(String[] arguments) throws NumberFormatException {
        int portNumberOccurrences = 0;
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].equals("-port")) {
                port = Integer.valueOf(arguments[i + 1]);
                portNumberOccurrences++;
            }
        }
        return portNumberOccurrences;
    }
}

/**
 * Notifies that the string written on the command line for launching the client app has a wrong format and that for this reason the game cannot begin.
 */
class InvalidFormatException extends Exception { }
