package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;

import java.util.ArrayList;
import java.util.Optional;

public class Show implements Command {

    private final Receiver receiver;

    public Show(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Request> execute(Invoker invoker, String[] args) {
        if (args.length == 0) {
            return receiver.show();
        } else {
            System.out.println("Command <show> is used without arguments");
            return Optional.empty();
        }
    }

    @Override
    public String getHelp() {
        return "Type <show> to get the list of elements in collection";
    }

}
