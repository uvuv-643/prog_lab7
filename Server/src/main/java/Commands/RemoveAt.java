package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Services.Request;
import Services.Response;

import java.util.Optional;

public class RemoveAt implements Command {

    private final Receiver receiver;

    public RemoveAt(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request, Invoker invoker) {
        if (request.getArgs().length == 1) {
            return receiver.removeAt(request.getArgs()[0]);
        } else {
            return new Response(false, "Command <removed_at> must have only 1 argument, found " + request.getArgs().length);
        }
    }

    @Override
    public String getHelp() {
        return "Type <remove_at index> to delete element from collection in the following position";
    }

}
