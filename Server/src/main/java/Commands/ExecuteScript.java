package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Services.Request;
import Services.Response;

import java.util.Optional;

public class ExecuteScript implements Command {

    private final Receiver receiver;

    public ExecuteScript(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request, Invoker invoker) {
        return new Response(false, "Command <execute_script> is unavailable");
    }

    static public String getHelp() {
        return "Type <execute_script filename> to run commands from file";
    }

}
