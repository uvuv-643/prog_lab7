package Commands;

import CommandPattern.Command;
import CommandPattern.CommandName;
import CommandPattern.Invoker;
import Entities.Person;

import java.util.ArrayList;

public class Help implements Command {

    public Help() {

    }

    @Override
    public boolean execute(Invoker invoker, ArrayList<Person> collection, String[] args) {
        if (args.length == 0) {
            for (CommandName commandName : CommandName.values()) {
                System.out.print(commandName + " - ");
                System.out.println(invoker.getCommandMap().get(commandName.toString()).getHelp());
            }
            return true;
        } else {
            System.out.println("Command add is used without arguments");
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "You already know what this command does";
    }


}
