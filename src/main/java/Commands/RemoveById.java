package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;

import java.util.ArrayList;

public class RemoveById implements Command {

    private final Receiver receiver;

    public RemoveById(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute(Invoker invoker, ArrayList<Person> collection, String[] args) {
        if (args.length == 1) {
            return receiver.removeById(collection, args[0]);
        } else {
            System.out.println("Command <remove_by_id> must have only 1 argument, found " + args.length);
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "Type <remove_by_id id> to delete element from collection with the following ID";
    }

}
