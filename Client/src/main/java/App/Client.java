package App;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Optional;

import Services.Request;
import Services.Response;

public class Client {

    private static final int PORT = 9000;
    private static final int BUF_SIZE = 32768;
    private DatagramSocket socket;
    private DatagramPacket packet;
    private InetAddress host;

    public Client() {
        try {
            host = InetAddress.getByName("localhost");
            socket = new DatagramSocket();
            socket.setSoTimeout(5000);
            System.out.println("Client start working");
        } catch (SocketException e) {
            System.out.println("Connection error");
            System.exit(-1);
        } catch (UnknownHostException e) {
            System.out.println("Connection error: host hot found");
            System.exit(-1);
        }
    }

    public Optional<Response> receive() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE);
            packet = new DatagramPacket(buffer.array(), buffer.array().length);
            socket.receive(packet);
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            return Optional.of((Response)objectInputStream.readObject());
        } catch (IOException e) {
            System.out.println("Receiving error. Did not received answer from server");
            return Optional.empty();
        } catch (ClassNotFoundException e) {
            System.out.println("Receiving error. Not found class of reading object");
            return Optional.empty();
        }
    }

    public void send(Request request) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(request);
            socket.send(new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.toByteArray().length, InetAddress.getByName("localhost"), PORT));
        } catch (IOException e) {
            System.out.println("Sending error. Request cannot be sent" + e.getMessage());
        }
    }


}
