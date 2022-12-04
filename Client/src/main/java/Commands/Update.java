package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Entities.Person;
import Services.Request;

import java.util.ArrayList;
import java.util.Optional;

public class Update implements Command {

    private final Receiver receiver;

    public Update(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Optional<Request> execute(Invoker invoker, String[] args) {
        if (args.length == 1) {
            return receiver.update(args[0]);
        } else {
            System.out.println("Command <update> must have only 1 argument, found " + args.length);
            return Optional.empty();
        }
    }

    @Override
    public String getHelp() {
        return "Type <update id> to start updating collection element with the following id";
    }

}
