package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Services.LoginCredentials;
import Services.Request;
import Services.Response;

import java.util.Optional;

public class FilterGreaterThanNationality implements Command {

    private final Receiver receiver;

    public FilterGreaterThanNationality(Receiver receiver) {
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
                    return receiver.filterGreaterThanNationality(request.getArgs()[0]);
                } else {
                    return new Response(false, "Command <filter_greater_than_nationality> must have only 1 argument, found " + request.getArgs().length);
                }
            }
        }
        return new Response(false, "Failed to login");
    }

    static public String getHelp() {
        return "Type <filter_greater_than_nationality nationality> to display collection elements where nationality is greater then parameter passed to the command";
    }

}
