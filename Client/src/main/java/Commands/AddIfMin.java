package Commands;

import CommandPattern.*;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;

import java.util.ArrayList;
import java.util.Optional;

/**
 * ConcernCommand
 * @author uvuv-643
 * @version 1.0
 */
public class AddIfMin implements Command {

    /** исполнитель команды */
    private final Receiver receiver;

    /**
     * Конструктор команды
     * @param receiver - исполнитель команды
     */
    public AddIfMin(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Команда "add_if_min {person}".
     * @param invoker - объект, вызвавший команду
     * @param args - аргументы команды (непроверенные)
     * @return Request - запрос, который может быть отправлен на сервер
     */
    @Override
    public Optional<Request> execute(Invoker invoker, String[] args) {
        if (args.length == 0) {
            return receiver.addIfMin();
        } else {
            System.out.println("Command <add_if_min> is used without arguments");
            return Optional.empty();
        }
    }

}
