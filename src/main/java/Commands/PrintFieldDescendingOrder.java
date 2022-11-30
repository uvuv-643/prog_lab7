package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;

import java.util.ArrayList;

public class PrintFieldDescendingOrder implements Command {

    private final Receiver receiver;

    public PrintFieldDescendingOrder(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute(Invoker invoker, ArrayList<Person> collection, String[] args) {
        if (args.length == 0) {
            return receiver.printFieldDescendingLocation(collection);
        } else {
            System.out.println("Command <print_field_descending_order> is used without arguments");
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "Type <print_field_descending_order> to print all the values of person location sorted by height in descending order";
    }

}
