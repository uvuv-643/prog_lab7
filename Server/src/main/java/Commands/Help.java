package Commands;

import CommandPattern.Command;
import CommandPattern.CommandName;
import CommandPattern.Invoker;
import Services.LoginCredentials;
import Services.Request;
import Services.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class Help implements Command {

    private static final HashMap<CommandName, String> helpTexts = new HashMap<>();

    public Help() {
        helpTexts.put(CommandName.ADD, Add.getHelp());
        helpTexts.put(CommandName.ADD_IF_MIN, AddIfMin.getHelp());
        helpTexts.put(CommandName.CLEAR, Clear.getHelp());
        helpTexts.put(CommandName.EXECUTE_SCRIPT, ExecuteScript.getHelp());
        helpTexts.put(CommandName.EXIT, Exit.getHelp());
        helpTexts.put(CommandName.FILTER_GREATER_THAN_NATIONALITY, FilterGreaterThanNationality.getHelp());
        helpTexts.put(CommandName.HELP, Help.getHelp());
        helpTexts.put(CommandName.INFO, Info.getHelp());
        helpTexts.put(CommandName.PRINT_DESCENDING, PrintDescending.getHelp());
        helpTexts.put(CommandName.PRINT_FIELD_DESCENDING_ORDER, PrintFieldDescendingOrder.getHelp());
        helpTexts.put(CommandName.REMOVE_AT, RemoveAt.getHelp());
        helpTexts.put(CommandName.REMOVE_BY_ID, RemoveById.getHelp());
        helpTexts.put(CommandName.REORDER, Reorder.getHelp());
        helpTexts.put(CommandName.SAVE, Save.getHelp());
        helpTexts.put(CommandName.SHOW, Show.getHelp());
        helpTexts.put(CommandName.UPDATE, Update.getHelp());
    }

    @Override
    public Response execute(Request request, Invoker invoker) {
        Optional<LoginCredentials> loginCredentials = request.getLoginCredentials();
        if (loginCredentials.isPresent()) {
            Request authCheckRequest = new Request("auth", new String[]{ loginCredentials.get().getLogin(), loginCredentials.get().getPassword() });
            Response response = invoker.execute(authCheckRequest);
            if (response.isSuccess()) {
                if (request.getArgs().length == 0) {
                    StringBuilder helpResult = new StringBuilder();
                    for (CommandName commandName : CommandName.values()) {
                        helpResult.append(String.format("%-35s - ", commandName));
                        helpResult.append(helpTexts.get(commandName)).append("\n");
                    }
                    return new Response(true, helpResult.toString());
                } else {
                    return new Response(false, "Command add is used without arguments" + request.getArgs().length);
                }
            }
        }
        return new Response(false, "Failed to login");
    }

    public static String getHelp() {
        return "You already know what this command does";
    }

}
