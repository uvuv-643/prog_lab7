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
public class PrintFieldDescendingOrder implements Command {

    /** исполнитель команды */
    private final Receiver receiver;

    /**
     * Конструктор команды
     * @param receiver - исполнитель команды
     */
    public PrintFieldDescendingOrder(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Команда "print_field_descending_order".
     * @param invoker - объект, вызвавший команду
     * @param args - аргументы команды (непроверенные)
     * @return Request - запрос, который может быть отправлен на сервер
     */
    @Override
    public Optional<Request> execute(Invoker invoker, String[] args) {
        if (args.length == 0) {
            return receiver.printFieldDescendingOrder();
        } else {
            System.out.println("Command <print_field_descending_order> is used without arguments");
            return Optional.empty();
        }
    }

}
