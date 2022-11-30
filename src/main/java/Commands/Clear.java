package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;

import java.util.ArrayList;

public class Clear implements Command {

    private final Receiver receiver;

    public Clear(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute(Invoker invoker, ArrayList<Person> collection, String[] argument) {
        if (argument.length == 0) {
            return receiver.clear(collection);
        } else {
            System.out.println("Command <clear> is used without arguments");
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "Type <clear> to clear the current collection";
    }


}
