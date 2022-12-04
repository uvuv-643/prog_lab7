package CommandPattern;

import Services.Request;

import java.util.Optional;

public interface Command {

    Optional<Request> execute(Invoker invoker, String[] args);
    String getHelp();

}
