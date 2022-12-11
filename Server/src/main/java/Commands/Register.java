package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Services.Request;
import Services.Response;

public class Register implements Command {

    private final Receiver receiver;

    public Register(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request, Invoker invoker) {
        if (request.getArgs().length == 2) {
            return receiver.register(request.getArgs()[0], request.getArgs()[1]);
        } else {
            return new Response(false, "Command <register> is used with 2 arguments");
        }
    }

    static public String getHelp() {
        return "Command to add new user in database";
    }

}