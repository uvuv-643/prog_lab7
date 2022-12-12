package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Services.LoginCredentials;
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
        Optional<LoginCredentials> loginCredentials = request.getLoginCredentials();
        if (loginCredentials.isPresent()) {
            Request authCheckRequest = new Request("auth", new String[]{ loginCredentials.get().getLogin(), loginCredentials.get().getPassword() });
            Response response = invoker.execute(authCheckRequest);
            if (response.isSuccess()) {
                if (request.getArgs().length == 1) {
                    return receiver.removeAt(request.getArgs()[0], response.getUserId());
                } else {
                    return new Response(false, "Command <removed_at> must have only 1 argument, found " + request.getArgs().length);
                }
            }
        }
        return new Response(false, "Failed to login");
    }

    static public String getHelp() {
        return "Type <remove_at index> to delete element from collection in the following position";
    }

}
