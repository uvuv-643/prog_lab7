package CommandPattern;

import Commands.*;
import Exceptions.ExecuteCommandException;
import Services.Request;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

/**
 * Класс, предназначенный для вызова определенной команды
 * @author uvuv-643
 * @version 1.0
 */
public class Invoker {

    /** Сопоставление название_команды => класс_комманды */
    private final HashMap<String, Command> commandMap = new HashMap<>();

    /** Список всех уже извлеченных скриптов в пределах текущей команды. Необходимо для предотвращения появления бесконечной рекурсии */
    private final HashSet<String> executedScripts = new HashSet<>();

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
        this.register(String.valueOf(CommandName.HELP), new Help(receiver));
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
     * @param commandName - название команды
     * @param args - аргументы команды (необработанные)
     * @return Request - сформированный запрос для отправки на сервер, может быть null (если команда введена некорректно)
     * @throws ExecuteCommandException - команада не была зарестрирована в Invoker
     * @see Request
     */
    public Optional<Request> execute(String commandName, String[] args) throws ExecuteCommandException {
        Command command = commandMap.get(commandName);
        if (command == null) {
            throw new ExecuteCommandException("There is no such command");
        }
        return command.execute(this, args);
    }

    /**
     * getter для Set<String> executedScripts
     * @param script - путь к файлу скрипта
     * @return boolean - был ли исполнен данный скрипт в пределах исполнения текущей команды?
     */
    public boolean isExecutedScript(String script) {
        return this.executedScripts.contains(script);
    }

    /**
     * setter для Set<String> executedScripts
     * @param script - исполненный скрипт
     */
    public void addExecutedScript(String script) {
        this.executedScripts.add(script);
    }

    /**
     * Очистить список исполненных скриптов. Рекомендуется применять в случае, если исполнение скриптов в рамках команды было завершено.
     */
    public void clearScriptList() {
        this.executedScripts.clear();
    }

}
