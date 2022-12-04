package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;
import Services.Response;

import java.util.ArrayList;
import java.util.Optional;

public class PrintDescending implements Command {

    private final Receiver receiver;

    public PrintDescending(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Response> execute(Request request, Invoker invoker) {
        if (request.getArgs().length == 0) {
            return receiver.printDescending();
        } else {
            return Optional.of(new Response(false, "Command <print_descending> is used without arguments"));
        }
    }

    @Override
    public String getHelp() {
        return "Type <print_descending> to show all elements in reversed order";
    }

}
