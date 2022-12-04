package Commands;

import CommandPattern.Command;
import CommandPattern.CommandName;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;

import java.util.ArrayList;
import java.util.Optional;

public class Help implements Command {

    private final Receiver receiver;

    public Help(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Request> execute(Invoker invoker, String[] args) {
        if (args.length == 0) {
            return receiver.help();
        } else {
            System.out.println("Command <help> is used without arguments");
            return Optional.empty();
        }
    }

    @Override
    public String getHelp() {
        return "You already know what this command does";
    }


}
