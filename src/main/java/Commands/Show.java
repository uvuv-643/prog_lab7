package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;

import java.util.ArrayList;

public class Show implements Command {

    private final Receiver receiver;

    public Show(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute(Invoker invoker, ArrayList<Person> collection, String[] args) {
        if (args.length == 0) {
            return receiver.show(collection);
        } else {
            System.out.println("Command <show> is used without arguments");
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "Type <show> to get the list of elements in collection";
    }

}
