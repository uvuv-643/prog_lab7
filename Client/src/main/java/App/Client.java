package App;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Optional;

import Services.Request;
import Services.Response;

/**
 * Класс, определяющий сущность клиента и осуществляющий отправку запросов и получение ответов на них.
 * @author uvuv-643
 * @version 1.0
 */
public class Client {

    /** Порт сервера, на который отправляются все запросы */
    private static final int PORT = 9000;

    /** Размер буффера для сериализации / десериализации данных */
    private static final int BUF_SIZE = 32768;

    /** Количество милисекунд, в течение который должен поступить ответ сервера */
    private static final int CLIENT_SOCKET_INVALID_TIMEOUT = 5000;

    /** Сокет датаграмм для отправки запросов и получения ответов */
    private DatagramSocket socket;

    /** Адрес сервера, на который отправляются все запросы */
    private InetAddress host;

    /**
     * Инициализация объекта Client по умолчанию.
     * Создание сокета датаграмм, инициализация адреса сервера
     */
    public Client() {
        try {
            host = InetAddress.getLocalHost();
            socket = new DatagramSocket();
            socket.setSoTimeout(CLIENT_SOCKET_INVALID_TIMEOUT);
            System.out.println("Client start working");
        } catch (SocketException e) {
            System.out.println("Connection error");
            System.exit(-1);
        } catch (UnknownHostException e) {
            System.out.println("Connection error: host hot found");
            System.exit(-1);
        }
    }

    /**
     * Получить ответ от сервера на отправленный ранее запрос
     * @return Response - ответ сервера на запрос, может быть null
     * @see Response
     */
    public Optional<Response> receive() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE);
            DatagramPacket packet = new DatagramPacket(buffer.array(), buffer.array().length);
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

    /**
     * Отправление запроса на сервер
     * @param request - запрос, отправляемый на сервер
     */
    public void send(Request request) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(request);
            socket.send(new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.toByteArray().length, host, PORT));
        } catch (IOException e) {
            System.out.println("Sending error. Request cannot be sent");
        }
    }


}
