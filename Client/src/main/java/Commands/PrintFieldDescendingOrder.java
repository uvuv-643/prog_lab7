package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;

import java.util.ArrayList;
import java.util.Optional;

public class PrintFieldDescendingOrder implements Command {

    private final Receiver receiver;

    public PrintFieldDescendingOrder(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Request> execute(Invoker invoker, String[] args) {
        if (args.length == 0) {
            return receiver.printFieldDescendingOrder();
        } else {
            System.out.println("Command <print_field_descending_order> is used without arguments");
            return Optional.empty();
        }
    }

    @Override
    public String getHelp() {
        return "Type <print_field_descending_order> to print all the values of person location sorted by height in descending order";
    }

}
