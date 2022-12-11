package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Services.Request;
import Services.Response;

public class Alive implements Command {

    private final Receiver receiver;

    public Alive(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request, Invoker invoker) {
        return new Response(true, "Server is alive");
    }

    static public String getHelp() {
        return "Command to check connection with server";
    }

}