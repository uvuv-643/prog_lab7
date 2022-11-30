package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;

import java.util.ArrayList;

public class RemoveAt implements Command {

    private final Receiver receiver;

    public RemoveAt(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute(Invoker invoker, ArrayList<Person> collection, String[] args) {
        if (args.length == 1) {
            return receiver.removeAt(collection, args[0]);
        } else {
            System.out.println("Command <removed_at> must have only 1 argument, found " + args.length);
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "Type <remove_at index> to delete element from collection in the following position";
    }

}
