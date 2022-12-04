package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;
import Services.Response;

import java.util.ArrayList;
import java.util.Optional;

public class Show implements Command {

    private final Receiver receiver;

    public Show(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Response> execute(Request request, Invoker invoker) {
        if (request.getArgs().length == 0) {
            return receiver.show();
        } else {
            return Optional.of(new Response(false, "Command <show> is used without arguments"));
        }
    }

    @Override
    public String getHelp() {
        return "Type <show> to get the list of elements in collection";
    }

}
