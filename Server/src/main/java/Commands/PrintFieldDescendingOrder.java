package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Services.Request;
import Services.Response;

import java.util.Optional;

public class PrintFieldDescendingOrder implements Command {

    private final Receiver receiver;

    public PrintFieldDescendingOrder(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request, Invoker invoker) {
        if (request.getArgs().length == 0) {
            return receiver.printFieldDescendingOrder();
        } else {
            return new Response(false, "Command <print_field_descending_order> is used without arguments");
        }
    }

    @Override
    public String getHelp() {
        return "Type <print_field_descending_order> to print all the values of person location sorted by height in descending order";
    }

}
