package Commands;

import CommandPattern.*;
import Services.Request;

import java.util.Optional;

public class Add implements Command {

    private final Receiver receiver;

    public Add(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Request> execute(Invoker invoker, String[] args) {
        if (args.length == 0) {
            return receiver.add();
        } else {
            System.out.println("Command <add> is used without arguments");
            return Optional.empty();
        }
    }

    @Override
    public String getHelp() {
        return "Type <add {element}> to add new element in collection";
    }

}