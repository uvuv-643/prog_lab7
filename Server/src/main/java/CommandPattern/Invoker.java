package CommandPattern;

import Commands.*;
import Entities.Person;
import Services.Request;
import Services.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class Invoker {

    private final HashMap<String, Command> commandMap = new HashMap<>();

    public void register(String commandName, Command command) {
        commandMap.put(commandName, command);
    }

    public Invoker(Receiver receiver) {
        this.register(String.valueOf(CommandName.ADD), new Add(receiver));
        this.register(String.valueOf(CommandName.ADD_IF_MIN), new AddIfMin(receiver));
        this.register(String.valueOf(CommandName.CLEAR), new Clear(receiver));
        this.register(String.valueOf(CommandName.EXECUTE_SCRIPT), new ExecuteScript(receiver));
        this.register(String.valueOf(CommandName.EXIT), new Exit(receiver));
        this.register(String.valueOf(CommandName.FILTER_GREATER_THAN_NATIONALITY), new FilterGreaterThanNationality(receiver));
        this.register(String.valueOf(CommandName.HELP), new Help());
        this.register(String.valueOf(CommandName.INFO), new Info(receiver));
        this.register(String.valueOf(CommandName.PRINT_DESCENDING), new PrintDescending(receiver));
        this.register(String.valueOf(CommandName.PRINT_FIELD_DESCENDING_ORDER), new PrintFieldDescendingOrder(receiver));
        this.register(String.valueOf(CommandName.REMOVE_AT), new RemoveAt(receiver));
        this.register(String.valueOf(CommandName.REMOVE_BY_ID), new RemoveById(receiver));
        this.register(String.valueOf(CommandName.REORDER), new Reorder(receiver));
        this.register(String.valueOf(CommandName.SAVE), new Save(receiver));
        this.register(String.valueOf(CommandName.SHOW), new Show(receiver));
        this.register(String.valueOf(CommandName.UPDATE), new Update(receiver));
    }

    public Optional<Response> execute(Request request) {
        Command command = commandMap.get(request.getCommandName());
        if (command == null) {
            return Optional.of(new Response(false, "There is no such command"));
        }
        return command.execute(request, this);
    }

    public HashMap<String, Command> getCommandMap() {
        return this.commandMap;
    }

}
