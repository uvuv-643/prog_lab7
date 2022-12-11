package Services;

import Entities.Person;

import java.io.Serial;
import java.io.Serializable;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.Optional;

/**
 * Общий класс для всех запросов, отправляемых клиентом на сервер.
 * Поддерживает сериализацию
 * @author uvuv-643
 * @version 1.0
 */
public class Request implements Serializable {

    @Serial
    private static final long serialVersionUID = 666777666L;

    /** Название команды к исполнению */
    private String commandName;

    /** Аргументы команды к исполнению */
    private String[] args;

    private LoginCredentials loginCredentials;

    /** Поле, которое может быть null, передаётся в случае, если необходимо добавить / обновить элемент */
    private Person person;

    /** Адрес клиента, совершающего запрос */
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

    public Request(String commandName, Person person, LoginCredentials loginCredentials) {
        this(commandName, person);
        this.loginCredentials = loginCredentials;
    }

    public Request(String commandName, LoginCredentials loginCredentials) {
        this(commandName);
        this.loginCredentials = loginCredentials;
    }

    public Request(String commandName, String[] args, LoginCredentials loginCredentials) {
        this(commandName, args);
        this.loginCredentials = loginCredentials;
    }

    public Request(String commandName, String[] args, Person person, LoginCredentials loginCredentials) {
        this(commandName, args, person);
        this.loginCredentials = loginCredentials;
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

    public Optional<LoginCredentials> getLoginCredentials() {
        return Optional.of(loginCredentials);
    }

    public void setLoginCredentials(LoginCredentials loginCredentials) {
        this.loginCredentials = loginCredentials;
    }
}