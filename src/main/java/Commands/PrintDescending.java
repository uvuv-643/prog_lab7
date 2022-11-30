package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;

import java.util.ArrayList;

public class PrintDescending implements Command {

    private final Receiver receiver;

    public PrintDescending(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute(Invoker invoker, ArrayList<Person> collection, String[] args) {
        if (args.length == 0) {
            return receiver.printDescending(collection);
        } else {
            System.out.println("Command <print_descending> is used without arguments");
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "Type <print_descending> to show all elements in reversed order";
    }

}
