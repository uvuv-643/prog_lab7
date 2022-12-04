package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;
import Services.Response;

import java.util.ArrayList;
import java.util.Optional;

public class RemoveById implements Command {

    private final Receiver receiver;

    public RemoveById(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Response> execute(Request request, Invoker invoker) {
        if (request.getArgs().length == 1) {
            return receiver.removeById(request.getArgs()[0]);
        } else {
            return Optional.of(new Response(false, "Command <remove_by_id> must have only 1 argument, found " + request.getArgs().length));
        }
    }

    @Override
    public String getHelp() {
        return "Type <remove_by_id id> to delete element from collection with the following ID";
    }

}
