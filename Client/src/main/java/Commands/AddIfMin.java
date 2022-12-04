package Commands;

import CommandPattern.*;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;

import java.util.ArrayList;
import java.util.Optional;

public class AddIfMin implements Command {

    private final Receiver receiver;

    public AddIfMin(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Request> execute(Invoker invoker, String[] argument) {
        if (argument.length == 0) {
            return receiver.addIfMin();
        } else {
            System.out.println("Command <add_if_min> is used without arguments");
            return Optional.empty();
        }
    }

    @Override
    public String getHelp() {
        return "Type <add_if_min {element}> to add element in collection only in case if it's the minimal element in collection. The comparison condition is the human height.";
    }

}
