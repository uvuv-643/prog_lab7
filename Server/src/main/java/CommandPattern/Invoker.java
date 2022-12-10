package CommandPattern;

import Commands.*;
import Entities.Person;
import Exceptions.ExecuteCommandException;
import Services.Request;
import Services.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 * Класс, предназначенный для вызова определенной команды
 * @author uvuv-643
 * @version 1.0
 */
public class Invoker {

    /** Сопоставление название_команды => класс_комманды */
    private final HashMap<String, Command> commandMap = new HashMap<>();

    /** Создать сопоставление название_команды => класс_комманды
     * @param commandName - название команды
     * @param command - класс команды
     */
    public void register(String commandName, Command command) {
        commandMap.put(commandName, command);
    }

    /**
     * Создание объекта вызова команд, регистрация всех команд программы
     * @param receiver - исполнитель команд
     * @see Receiver
     */
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

    /**
     * Вызов команды
     * @param request - запрос, поступивший с клиента на исполнение
     * @return Response - ответ клиенту на основании выполнения команды
     * @see Request
     * @see Response
     */
    public Response execute(Request request) {
        Command command = commandMap.get(request.getCommandName());
        if (command == null) {
            return new Response(false, "There is no such command");
        }
        return command.execute(request, this);
    }

    /**
     * getter для Map commandMap
     * @return HashMap
     */
    public HashMap<String, Command> getCommandMap() {
        return this.commandMap;
    }

}
