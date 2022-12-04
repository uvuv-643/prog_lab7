package Commands;

import CommandPattern.Command;
import CommandPattern.CommandName;
import CommandPattern.Invoker;
import Services.Request;
import Services.Response;

import java.util.Optional;

public class Help implements Command {

    public Help() {

    }

    @Override
    public Optional<Response> execute(Request request, Invoker invoker) {
        if (request.getArgs().length == 0) {
            StringBuilder helpResult = new StringBuilder();
            for (CommandName commandName : CommandName.values()) {
                helpResult.append(commandName).append(" - ");
                helpResult.append(invoker.getCommandMap().get(commandName.toString()).getHelp()).append("\n");
            }
            return Optional.of(new Response(true, helpResult.toString()));
        } else {
            return Optional.of(new Response(false, "Command add is used without arguments" + request.getArgs().length));
        }
    }

    @Override
    public String getHelp() {
        return "You already know what this command does";
    }


}
