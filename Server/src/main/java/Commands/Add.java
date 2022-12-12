package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Services.LoginCredentials;
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
        Optional<LoginCredentials> loginCredentials = request.getLoginCredentials();
        if (loginCredentials.isPresent()) {
            Request authCheckRequest = new Request("auth", new String[]{ loginCredentials.get().getLogin(), loginCredentials.get().getPassword() });
            Response response = invoker.execute(authCheckRequest);
            if (response.isSuccess()) {
                if (request.getArgs().length == 0) {
                    return receiver.add(request.getPerson(), response.getUserId());
                } else {
                    return new Response(false, "Command <add> is used without arguments");
                }
            }
        }
        return new Response(false, "Failed to login");
    }

    static public String getHelp() {
        return "Type <add {element}> to add new element in collection";
    }

}