package CommandPattern;

import Entities.Person;

import java.util.ArrayList;

public interface Command {

    boolean execute(Invoker invoker, ArrayList<Person> collection, String[] args);
    String getHelp();

}
