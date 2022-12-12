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

        try {
            while (true) {
                if (System.in.available() > 0) {
                    String serverInteractiveCommand;
                    Scanner in = new Scanner(System.in);
                    try {
                        serverInteractiveCommand = in.nextLine();
                    } catch (NullPointerException e) {
                        return;
                    }
                    if (serverInteractiveCommand.equals("save")) {
                        Response response = receiver.save();
                        System.out.println(response.getMessage());
                    } else if (serverInteractiveCommand.equals("exit")) {
                        receiver.exit();
                    } else {
                        System.out.println("Cannot execute this command. Server able to execute only <save> and <exit>");
                    }
                }

                Optional<Request> request = server.receive();
                if (request.isPresent()) {
                    Response response = invoker.execute(request.get());
                    server.send(response, request.get().getClientAddress());
                }
            }
        } catch (NoSuchElementException ignored) { }
    }

}
