package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;

import java.util.ArrayList;

public class Update implements Command {

    private final Receiver receiver;

    public Update(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute(Invoker invoker, ArrayList<Person> collection, String[] args) {
        if (args.length == 1) {
            return receiver.update(collection, args[0]);
        } else {
            System.out.println("Command <update> must have only 1 argument, found " + args.length);
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "Type <update id> to start updating collection element with the following id";
    }

}
