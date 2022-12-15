package CommandPattern;

import Commands.*;
import Services.Request;
import Services.Response;

import java.util.HashMap;

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
        this.register("add", new Add(receiver));
        this.register("add_if_min", new AddIfMin(receiver));
        this.register("clear", new Clear(receiver));
        // this.register(String.valueOf(CommandName.EXECUTE_SCRIPT), new ExecuteScript(receiver));
        // this.register(String.valueOf(CommandName.EXIT), new Exit(receiver));
        this.register("filter_greater_than_nationality", new FilterGreaterThanNationality(receiver));
        this.register("help", new Help());
        this.register("info", new Info(receiver));
        this.register("print_descending", new PrintDescending(receiver));
        this.register("print_field_descending_order", new PrintFieldDescendingOrder(receiver));
        this.register("remove_at", new RemoveAt(receiver));
        this.register("remove_by_id", new RemoveById(receiver));
        this.register("reorder", new Reorder(receiver));
        // this.register(String.valueOf(CommandName.SAVE), new Save(receiver));
        this.register("show", new Show(receiver));
        this.register("update", new Update(receiver));
        this.register("alive", new Alive(receiver));
        this.register("check_id", new CheckId(receiver));
        this.register("auth", new Auth(receiver));
        this.register("register", new Register(receiver));
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
