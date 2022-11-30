package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;

import java.util.ArrayList;

public class ExecuteScript implements Command {

    private final Receiver receiver;

    public ExecuteScript(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute(Invoker invoker, ArrayList<Person> collection, String[] args) {
        if (args.length == 1) {
            return receiver.executeScript(invoker, collection, args[0]);
        } else {
            System.out.println("Command <execute_script> must have only 1 argument, found " + args.length);
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "Type <execute_script filename> to run commands from file";
    }

}
