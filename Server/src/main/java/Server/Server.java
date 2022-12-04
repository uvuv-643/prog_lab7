package Server;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Optional;

import Services.Request;
import Services.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
    private static final Logger logger = LogManager.getLogger("info");
    private final int SERVICE_PORT = 9000;

    private final int BUF_SIZE = 32768;


    private byte[] inputData;

    private DatagramChannel datagramChannel;

    public Server() {

        try {
            this.datagramChannel = DatagramChannel.open();
            this.datagramChannel.bind(new InetSocketAddress(SERVICE_PORT));
            this.datagramChannel.configureBlocking(false);
            logger.info("Server started on port " + SERVICE_PORT);
        } catch (IOException ex) {
            logger.info("Server cannot start working");
        }
    }

    public Optional<Request> receive() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE);
            SocketAddress clientAddress = datagramChannel.receive(buffer);
            if (clientAddress == null) {
                return Optional.empty();
            }
            ObjectInputStream targetRequest = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            Request request = (Request) targetRequest.readObject();
            logger.info("Received new request from " + clientAddress);
            logger.info("Request: " + request);
            request.setClientAddress(clientAddress);
            return Optional.of(request);
        } catch (IOException e) {
            System.out.println("Some error with request");
            return Optional.empty();
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to find class of object");
            return Optional.empty();
        }
    }

    public void send(Response response, SocketAddress clientAddress) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            logger.info("Request was sent to address " + clientAddress);
            logger.info("Request: " + response);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            this.datagramChannel.send(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()), clientAddress);
        } catch (IOException e) {
            logger.info("Error when sending response to client");
        }
    }

}
