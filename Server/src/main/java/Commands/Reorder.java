package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Services.Request;
import Services.Response;

import java.util.Optional;

public class Reorder implements Command {

    private final Receiver receiver;

    public Reorder(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request, Invoker invoker) {
        if (request.getArgs().length == 0) {
            return receiver.reorder();
        } else {
            return new Response(false, "Command <reorder> is used without arguments" + request.getArgs().length);
        }
    }

    @Override
    public String getHelp() {
        return "Type <reorder> to reverse the position of elements in collection";
    }

}
