package it.polimi.ingsw;

import it.polimi.ingsw.client.cli.ClientCli;
import it.polimi.ingsw.client.gui.GuiClient;
import it.polimi.ingsw.controller.message.ConfigurationMessage;
import it.polimi.ingsw.controller.message.ConnectionSide;
import javafx.application.Application;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Scanner;

public class ClientApp {

    public static String ip = "";
    public static int port = 50000;

    public static void main(String[] args){
        DatagramSocket socket = null;
        try {
            if(args.length > 0 && args.length < 4) {
                if (args.length == 1) {
                    String myIp = calculateMyIp();
                    socket = new DatagramSocket();
                    socket.setBroadcast(true);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    objectOutputStream.writeObject(new ConfigurationMessage(ConnectionSide.CLIENT, myIp, socket.getLocalPort()));
                    DatagramPacket packet = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size(), InetAddress.getByName("255.255.255.255"), 40000);
                    //System.out.println(InetAddress.getLocalHost().getHostAddress() + " " + byteArrayOutputStream.toByteArray().toString() + " " + String.valueOf(byteArrayOutputStream.size()) + " " + InetAddress.getByName("255.255.255.255") + " " + 40000);
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
                } else if (args.length == 2 || args.length == 3) {
                    ip = args[1];
                    if (args.length == 3) {
                        port = Integer.valueOf(args[2]);
                        System.out.println("The chosen port is the number " + port);
                    }
                }
                if (args[0].equals("cli")) {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    ClientCli client = new ClientCli(ip, port);
                    try {
                        client.run();
                    } catch (IOException e) {
                        System.out.println("The server is currently not running on the given address");
                    }
                } else if (args[0].equals("gui")) {
                    Application.launch(GuiClient.class);
                } else {
                    System.out.println("The option is not valid. Input format : ./file.class MODE\nMODE : { CLI to play by command line - GUI to play by graphic user interface }");
                }
            } else {
                System.out.println("You are not sending the correct information. The format is: 'java -jar path_to_file/eriantys-client.jar gui [ip_address [port_number]]' (instead of gui you can use 'cli')");
            }
        } catch (NumberFormatException e) {
            System.out.println("You insert a wrong information. The default port is 50000");
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
}
