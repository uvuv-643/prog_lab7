package Services;

import Entities.Person;

import java.io.Serial;
import java.io.Serializable;
import java.net.SocketAddress;
import java.util.Arrays;

public class Request implements Serializable {

    @Serial
    private static final long serialVersionUID = 666777666L;

    private String commandName;
    private String[] args;
    private Person person;
    private SocketAddress clientAddress;

    public Request(String commandName, String[] args, Person person) {
        this(commandName, args);
        this.person = person;
    }

    public Request(String commandName, String[] args) {
        this.commandName = commandName;
        this.args = args;
    }

    public Request(String commandName) {
        this.commandName = commandName;
        this.args = new String[]{};
    }

    public Request(String commandName, Person person) {
        this.commandName = commandName;
        this.person = person;
        this.args = new String[]{};
    }

    public String getCommandName() {
        return this.commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public SocketAddress getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(SocketAddress clientAddress) {
        this.clientAddress = clientAddress;
    }

    @Override
    public String toString() {
        return "command: " + commandName + "; args: " + Arrays.toString(args);
    }

}