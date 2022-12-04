package Server;

import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Input.FileManager.FileManager;
import Server.Server;
import Services.Request;
import Services.Response;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        ArrayList<Person> collection = new ArrayList<>();
        try {
            FileManager fileManager = new FileManager();
            collection = fileManager.readFromFile();
        } catch (IOException exception) {
            System.out.println("There is no file with collection or you cannot read collection from file");
            System.exit(0);
        } catch (JsonParseException exception) {
            System.out.println("Cannot parse JSON file");
            System.exit(0);
        } catch (NullPointerException exception) {
            System.out.println("You have to export env path of initial collection before starting working");
            System.exit(0);
        }
        Receiver receiver = new Receiver(collection);
        Invoker invoker = new Invoker(receiver);
        Server server = new Server();

        while (true) {
            if (System.in.available() > 0) {
                String serverInteractiveCommand;
                Scanner in = new Scanner(System.in);
                try {
                    serverInteractiveCommand = in.nextLine();
                } catch (NullPointerException e) {
                    return;
                }
                if (serverInteractiveCommand.equals("save") || serverInteractiveCommand.equals("exit")) {
                    Request request = new Request(serverInteractiveCommand, new String[]{});
                    Optional<Response> responseRaw = invoker.execute(request);
                    responseRaw.ifPresentOrElse((response) -> {
                        System.out.println(response.getMessage());
                    }, () -> {
                        System.out.println("Cannot execute command");
                    });
                } else {
                    System.out.println("Cannot execute this command. Server able to execute only <save> and <exit>");
                }
            }

            Optional<Request> request = server.receive();
            if (request.isPresent()) {
                Optional<Response> response = invoker.execute(request.get());
                response.ifPresent(responseValue -> server.send(responseValue, request.get().getClientAddress()));
            }
        }
    }

}
