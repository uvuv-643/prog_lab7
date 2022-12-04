package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;
import Services.Response;

import java.util.ArrayList;
import java.util.Optional;

public class Update implements Command {

    private final Receiver receiver;

    public Update(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Response> execute(Request request, Invoker invoker) {
        if (request.getArgs().length == 1) {
            return receiver.update(request.getPerson(), request.getArgs()[0]);
        } else {
            return Optional.of(new Response(false, "Command <update> must have only 1 argument, found " + request.getArgs().length));
        }
    }

    @Override
    public String getHelp() {
        return "Type <update id> to start updating collection element with the following id";
    }

}
