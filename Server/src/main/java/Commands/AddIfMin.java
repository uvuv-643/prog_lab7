package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Services.Request;
import Services.Response;

import java.util.Optional;

public class AddIfMin implements Command {

    private final Receiver receiver;

    public AddIfMin(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request, Invoker invoker) {
        if (request.getArgs().length == 0) {
            return receiver.addIfMin(request.getPerson());
        } else {
            return new Response(false, "Command <add_if_min> is used without arguments");
        }
    }

    @Override
    public String getHelp() {
        return "Type <add_if_min {element}> to add element in collection only in case if it's the minimal element in collection. The comparison condition is the human height.";
    }

}
