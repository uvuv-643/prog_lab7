package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Services.Request;
import Services.Response;

import java.util.Optional;

public class Add implements Command {

    private final Receiver receiver;

    public Add(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request, Invoker invoker) {
        if (request.getArgs().length == 0) {
            return receiver.add(request.getPerson());
        } else {
            return new Response(false, "Command <add> is used without arguments");
        }
    }

    @Override
    public String getHelp() {
        return "Type <add {element}> to add new element in collection";
    }

}