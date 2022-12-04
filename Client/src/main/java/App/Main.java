package App;

import CommandPattern.Receiver;
import CommandPattern.Invoker;

public class Main {

    public static void main(String[] args){
        Client client = new Client();
        Invoker invoker = new Invoker(new Receiver(client));
        Terminal terminal = new Terminal(invoker, client);
        terminal.startKeyboard();
    }

}
