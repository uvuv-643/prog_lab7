package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;

import java.util.ArrayList;

public class Add implements Command {

    private final Receiver receiver;

    public Add(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute(Invoker invoker, ArrayList<Person> collection, String[] args) {
        if (args.length == 0) {
            return receiver.add(collection);
        } else {
            System.out.println("Command <add> is used without arguments");
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "Type <add {element}> to add new element in collection";
    }

}