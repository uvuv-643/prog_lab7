package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;
import Services.Response;

import java.util.ArrayList;
import java.util.Optional;

public class Exit implements Command {

    private final Receiver receiver;

    public Exit(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Response> execute(Request request, Invoker invoker) {
        if (request.getArgs().length == 0) {
            receiver.save();
            receiver.exit();
            return Optional.empty();
        } else {
            return Optional.of(new Response(false, "Command <exit> is used without arguments"));
        }
    }

    @Override
    public String getHelp() {
        return "Good bye";
    }


}
