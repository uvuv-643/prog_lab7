package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;

import java.util.ArrayList;
import java.util.Optional;

public class Clear implements Command {

    private final Receiver receiver;

    public Clear(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Request> execute(Invoker invoker, String[] argument) {
        if (argument.length == 0) {
            return receiver.clear();
        } else {
            System.out.println("Command <clear> is used without arguments");
            return Optional.empty();
        }
    }

    @Override
    public String getHelp() {
        return "Type <clear> to clear the current collection";
    }


}
