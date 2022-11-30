package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;

import java.util.ArrayList;

public class AddIfMin implements Command {

    private final Receiver receiver;

    public AddIfMin(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute(Invoker invoker, ArrayList<Person> collection, String[] argument) {
        if (argument.length == 0) {
            return receiver.addIfMin(collection);
        } else {
            System.out.println("Command <add_if_min> is used without arguments");
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "Type <add_if_min {element}> to add element in collection only in case if it's the minimal element in collection. The comparison condition is the human height.";
    }

}
