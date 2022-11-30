package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;

import java.util.ArrayList;

public class Save implements Command {

    private final Receiver receiver;

    public Save(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute(Invoker invoker, ArrayList<Person> collection, String[] args) {
        if (args.length == 0) {
            return receiver.save(collection);
        } else {
            System.out.println("Command <save> is used without arguments");
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "Type <save> to save collection in file (env path)";
    }

}
