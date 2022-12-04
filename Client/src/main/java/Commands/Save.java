package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;

import java.util.ArrayList;
import java.util.Optional;

public class Save implements Command {

    private final Receiver receiver;

    public Save(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Request> execute(Invoker invoker, String[] args) {
        if (args.length == 0) {
            return receiver.save();
        } else {
            System.out.println("Command <save> is used without arguments");
            return Optional.empty();
        }
    }

    @Override
    public String getHelp() {
        return "Type <save> to save collection in file (env path)";
    }

}
