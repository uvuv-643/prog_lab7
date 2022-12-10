package Commands;

import CommandPattern.*;
import Input.InputManager;
import Services.Request;

import java.util.Optional;

/**
 * ConcernCommand
 * @author uvuv-643
 * @version 1.0
 */
public class Add implements Command {

    /** исполнитель команды */
    private final Receiver receiver;

    /**
     * Конструктор команды
     * @param receiver - исполнитель команды
     */
    public Add(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Команда "add {person}".
     * @param invoker - объект, вызвавший команду
     * @param args - аргументы команды (непроверенные)
     * @return Request - запрос, который может быть отправлен на сервер
     */
    @Override
    public Optional<Request> execute(Invoker invoker, String[] args) {
        if (args.length == 0) {
            return receiver.add();
        } else {
            System.out.println("Command <add> is used without arguments");
            return Optional.empty();
        }
    }

}