package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;

import java.util.ArrayList;
import java.util.Optional;

public class ExecuteScript implements Command {

    private final Receiver receiver;

    public ExecuteScript(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Request> execute(Invoker invoker, String[] args) {
        if (args.length == 1) {
            return receiver.executeScript(invoker, args[0]);
        } else {
            System.out.println("Command <execute_script> must have only 1 argument, found " + args.length);
            return Optional.empty();
        }
    }

    @Override
    public String getHelp() {
        return "Type <execute_script filename> to run commands from file";
    }

}
