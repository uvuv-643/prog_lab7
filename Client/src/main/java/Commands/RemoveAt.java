package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;

import java.util.ArrayList;
import java.util.Optional;

public class RemoveAt implements Command {

    private final Receiver receiver;

    public RemoveAt(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Request> execute(Invoker invoker, String[] args) {
        if (args.length == 1) {
            return receiver.removeAt(args[0]);
        } else {
            System.out.println("Command <removed_at> must have only 1 argument, found " + args.length);
            return Optional.empty();
        }
    }

    @Override
    public String getHelp() {
        return "Type <remove_at index> to delete element from collection in the following position";
    }

}
