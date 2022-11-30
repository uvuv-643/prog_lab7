package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;

import java.util.ArrayList;

public class Reorder implements Command {

    private final Receiver receiver;

    public Reorder(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute(Invoker invoker, ArrayList<Person> collection, String[] args) {
        if (args.length == 0) {
            return receiver.reorder(collection);
        } else {
            System.out.println("Command <reorder> is used without arguments");
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "Type <reorder> to reverse the position of elements in collection";
    }

}
