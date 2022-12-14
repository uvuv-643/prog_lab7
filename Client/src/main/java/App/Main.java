package App;

import CommandPattern.Invoker;
import CommandPattern.Receiver;

import java.util.NoSuchElementException;

public class Main {

    public static void main(String[] args) {
        Client client = new Client();
        Invoker invoker = new Invoker(new Receiver(client));
        Terminal terminal = new Terminal(invoker, client);
        try {
            terminal.startKeyboard();
        } catch (NoSuchElementException ignored) { }
    }

}
