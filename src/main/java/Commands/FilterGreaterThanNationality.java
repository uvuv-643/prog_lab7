package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;

import java.util.ArrayList;

public class FilterGreaterThanNationality implements Command {

    private final Receiver receiver;

    public FilterGreaterThanNationality(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute(Invoker invoker, ArrayList<Person> collection, String[] args) {
        if (args.length == 1) {
            return receiver.filterGreaterThanNationality(collection, args[0]);
        } else {
            System.out.println("Command <filter_greater_than_nationality> must have only 1 argument, found " + args.length);
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "Type <filter_greater_than_nationality nationality> to display collection elements where nationality is greater then parameter passed to the command";
    }

}
