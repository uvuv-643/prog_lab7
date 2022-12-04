package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;

import java.util.ArrayList;
import java.util.Optional;

public class Exit implements Command {

    private final Receiver receiver;

    public Exit(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Request> execute(Invoker invoker, String[] argument) {
        if (argument.length == 0) {
            receiver.exit();
        } else {
            System.out.println("Command <exit> is used without arguments");
        }
        return Optional.empty();
    }

    @Override
    public String getHelp() {
        return "Good bye";
    }


}
