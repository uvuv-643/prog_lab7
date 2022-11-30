package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;

import java.util.ArrayList;

public class Exit implements Command {

    private final Receiver receiver;

    public Exit(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute(Invoker invoker, ArrayList<Person> collection, String[] argument) {
        if (argument.length == 0) {
            receiver.exit();
        } else {
            System.out.println("Command <exit> is used without arguments");
            return false;
        }
        return true;
    }

    @Override
    public String getHelp() {
        return "Good bye";
    }


}
