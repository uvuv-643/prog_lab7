package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;
import Services.Response;

import java.util.ArrayList;
import java.util.Optional;

public class Save implements Command {

    private final Receiver receiver;

    public Save(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Response> execute(Request request, Invoker invoker) {
        if (request.getArgs().length == 0) {
            return receiver.save();
        } else {
            return Optional.of(new Response(false, "Command <save> is unavailable"));
        }
    }

    @Override
    public String getHelp() {
        return "Type <save> to save collection in file (env path)";
    }

}
