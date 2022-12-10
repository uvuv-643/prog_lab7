package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Services.Request;

import java.util.Optional;

public class ExecuteScript implements Command {

    /** исполнитель команды */
    private final Receiver receiver;

    /**
     * Конструктор команды
     * @param receiver - исполнитель команды
     */
    public ExecuteScript(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Команда "execute_script script".
     * @param invoker - объект, вызвавший команду
     * @param args - аргументы команды (непроверенные)
     * @return Request - запрос, который может быть отправлен на сервер
     */
    @Override
    public Optional<Request> execute(Invoker invoker, String[] args) {
        if (args.length == 1) {
            return receiver.executeScript(invoker, args[0]);
        } else {
            System.out.println("Command <execute_script> must have only 1 argument, found " + args.length);
            return Optional.empty();
        }
    }

}
