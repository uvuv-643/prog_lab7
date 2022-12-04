package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;
import Services.Response;

import java.util.ArrayList;
import java.util.Optional;

public class Info implements Command {

    private final Receiver receiver;

    public Info(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Response> execute(Request request, Invoker invoker) {
        if (request.getArgs().length == 0) {
            return receiver.info();
        } else {
            return Optional.of(new Response(false, "Command <info> is used without arguments"));
        }
    }

    @Override
    public String getHelp() {
        return "Type <info> to get all the information about current state of collection";
    }


}
