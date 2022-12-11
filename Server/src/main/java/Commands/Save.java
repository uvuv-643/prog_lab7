package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Services.Request;
import Services.Response;

import java.util.Optional;

public class Save implements Command {

    private final Receiver receiver;

    public Save(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request, Invoker invoker) {
        if (request.getArgs().length == 0) {
            return receiver.save();
        } else {
            return new Response(false, "Command <save> is unavailable");
        }
    }

    static public String getHelp() {
        return "You cannot save collection to file";
    }

}
