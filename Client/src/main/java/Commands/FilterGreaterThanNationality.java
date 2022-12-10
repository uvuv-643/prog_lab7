package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Services.Request;

import java.util.Optional;

/**
 * ConcernCommand
 * @author uvuv-643
 * @version 1.0
 */
public class FilterGreaterThanNationality implements Command {

    /** исполнитель команды */
    private final Receiver receiver;

    /**
     * Конструктор команды
     * @param receiver - исполнитель команды
     */
    public FilterGreaterThanNationality(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Команда "filter_greater_than_nationality nationality".
     * @param invoker - объект, вызвавший команду
     * @param args - аргументы команды (непроверенные)
     * @return Request - запрос, который может быть отправлен на сервер
     */
    @Override
    public Optional<Request> execute(Invoker invoker, String[] args) {
        if (args.length == 1) {
            return receiver.filterGreaterThanNationality(args[0]);
        } else {
            System.out.println("Command <filter_greater_than_nationality> must have only 1 argument, found " + args.length);
            return Optional.empty();
        }
    }

}
