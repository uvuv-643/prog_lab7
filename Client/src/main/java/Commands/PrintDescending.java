package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;

import java.util.ArrayList;
import java.util.Optional;

public class PrintDescending implements Command {

    private final Receiver receiver;

    public PrintDescending(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Request> execute(Invoker invoker, String[] args) {
        if (args.length == 0) {
            return receiver.printDescending();
        } else {
            System.out.println("Command <print_descending> is used without arguments");
            return Optional.empty();
        }
    }

    @Override
    public String getHelp() {
        return "Type <print_descending> to show all elements in reversed order";
    }

}
