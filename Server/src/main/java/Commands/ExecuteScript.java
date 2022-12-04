package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;
import Services.Response;

import java.util.ArrayList;
import java.util.Optional;

public class ExecuteScript implements Command {

    private final Receiver receiver;

    public ExecuteScript(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Response> execute(Request request, Invoker invoker) {
        return Optional.of(new Response(false, "Command <execute_script> is unavailable"));
    }

    @Override
    public String getHelp() {
        return "Type <execute_script filename> to run commands from file";
    }

}
