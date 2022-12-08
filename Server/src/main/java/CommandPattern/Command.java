package CommandPattern;

import Entities.Person;
import Services.Request;
import Services.Response;

import java.util.ArrayList;
import java.util.Optional;

public interface Command {

    Optional<Response> execute(Request request, Invoker invoker);
    String getHelp();

}
