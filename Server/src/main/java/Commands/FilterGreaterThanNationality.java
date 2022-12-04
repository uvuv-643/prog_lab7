package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;
import Services.Response;

import java.util.ArrayList;
import java.util.Optional;

public class FilterGreaterThanNationality implements Command {

    private final Receiver receiver;

    public FilterGreaterThanNationality(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Response> execute(Request request, Invoker invoker) {
        if (request.getArgs().length == 1) {
            return receiver.filterGreaterThanNationality(request.getArgs()[0]);
        } else {
            return Optional.of(new Response(false, "Command <filter_greater_than_nationality> must have only 1 argument, found " + request.getArgs().length));
        }
    }

    @Override
    public String getHelp() {
        return "Type <filter_greater_than_nationality nationality> to display collection elements where nationality is greater then parameter passed to the command";
    }

}
