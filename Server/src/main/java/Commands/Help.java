package Commands;

import CommandPattern.Command;
import CommandPattern.CommandName;
import CommandPattern.Invoker;
import Services.Request;
import Services.Response;

public class Help implements Command {

    public Help() {

    }

    @Override
    public Response execute(Request request, Invoker invoker) {
        if (request.getArgs().length == 0) {
            StringBuilder helpResult = new StringBuilder();
            for (CommandName commandName : CommandName.values()) {
                helpResult.append(String.format("%-35s - ", commandName));
                helpResult.append(invoker.getCommandMap().get(commandName.toString()).getHelp()).append("\n");
            }
            return new Response(true, helpResult.toString());
        } else {
            return new Response(false, "Command add is used without arguments" + request.getArgs().length);
        }
    }

    @Override
    public String getHelp() {
        return "You already know what this command does";
    }

}
