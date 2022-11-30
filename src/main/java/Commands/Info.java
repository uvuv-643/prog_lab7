package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;

import java.util.ArrayList;

public class Info implements Command {

    private final Receiver receiver;

    public Info(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute(Invoker invoker, ArrayList<Person> collection, String[] args) {
        if (args.length == 0) {
            return receiver.info(collection);
        } else {
            System.out.println("Command <info> is used without arguments");
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "Type <info> to get all the information about current state of collection";
    }


}
