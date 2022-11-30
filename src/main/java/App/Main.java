package App;

import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Input.FileManager.FileManager;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        String path = System.getenv("path");

        ArrayList<Person> collection = new ArrayList<>();
        Receiver receiver = new Receiver();
        Invoker invoker = new Invoker(receiver);
        try {
            FileManager fileManager = new FileManager();
            collection = fileManager.readFromFile();
        } catch (IOException exception) {
            System.out.println("There is no file with collection or you cannot read collection from file");
            System.exit(0);
        } catch (JsonParseException exception) {
            System.out.println("Cannot parse JSON file");
            System.exit(0);
        }
        AddCommands.addCommands(invoker, receiver);
        Terminal terminal = new Terminal(invoker, collection);
        terminal.startKeyboard();

    }

}
