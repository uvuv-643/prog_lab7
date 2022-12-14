package Server;

import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Input.FileManager.FileManager;
import Services.PersonService;
import Services.Request;
import Services.Response;
import Services.SQL.SQLManager;
import Services.SQL.SQLQuery;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private static final int FIRST_THREAD_POOL_COUNT = 5;
    private static final int GLOBAL_THREAD_LIMIT = 20;

    public static void main(String[] args) throws IOException {

        Receiver receiver = new Receiver();
        Invoker invoker = new Invoker(receiver);
        Server server = new Server();

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(FIRST_THREAD_POOL_COUNT);

        while (true) {
            try {
                if (System.in.available() > 0) {
                    String serverInteractiveCommand;
                    Scanner in = new Scanner(System.in);
                    try {
                        serverInteractiveCommand = in.nextLine();
                    } catch (NullPointerException e) {
                        return;
                    }
                    if (serverInteractiveCommand.equals("exit")) {
                        receiver.exit();
                    } else {
                        System.out.println("Cannot execute this command. Server able to execute only <exit>");
                    }
                }
            } catch (NoSuchElementException exception) {
                receiver.exit();
            }

            if (Thread.activeCount() < GLOBAL_THREAD_LIMIT) {
                new Thread(() -> {
                    Optional<Request> request = server.receive();
                    request.ifPresent(value -> executor.submit(() -> {
                        Response response = invoker.execute(request.get());
                        server.send(response, request.get().getClientAddress());
                    }));
                }).start();
            }

        }
    }
}
