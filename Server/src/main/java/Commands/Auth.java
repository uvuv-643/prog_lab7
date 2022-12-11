package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Services.LoginCredentials;
import Services.Request;
import Services.Response;

public class Auth implements Command {

    private final Receiver receiver;

    public Auth(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request, Invoker invoker) {
        if (request.getArgs().length == 2) {
            return receiver.auth(request.getArgs()[0], request.getArgs()[1]);
        } else {
            return new Response(false, "Command <auth> is used with 2 arguments");
        }
    }

    static public String getHelp() {
        return "Command to check if user credentials is valid";
    }

}