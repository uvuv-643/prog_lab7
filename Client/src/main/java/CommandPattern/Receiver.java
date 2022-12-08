package CommandPattern;

import App.Client;
import Entities.Person;
import Exceptions.ExecuteScriptException;
import Input.InputManager;
import Services.Request;
import App.Terminal;

import java.io.FileNotFoundException;
import java.util.Optional;

public class Receiver {

    private final InputManager inputManager = new InputManager();
    private final Client client;

    public Receiver(Client client) {
        this.client = client;
    }

    public Optional<Request> info() {
        return Optional.of(new Request("info"));
    }

    public Optional<Request> help() {
        return Optional.of(new Request("help"));
    }

    public Optional<Request> show() {
        return Optional.of(new Request("show"));
    }

    public Optional<Request> add() {
        Person person = this.inputManager.inputPerson();
        return Optional.of(new Request("add", person));
    }

    public Optional<Request> update(String idRaw) {
        Person person = this.inputManager.inputPerson();
        return Optional.of(new Request("update", new String[]{idRaw}, person));
    }

    public Optional<Request> removeById(String idRaw) {
        return Optional.of(new Request("remove_by_id", new String[]{idRaw}));
    }

    public Optional<Request> clear() {
        return Optional.of(new Request("clear"));
    }

    public Optional<Request> save() {
        return Optional.empty();
    }

    public Optional<Request> executeScript(Invoker invoker, String filename) {
        try {
            Terminal terminal = new Terminal(invoker, client);
            terminal.startFile(filename);
            return Optional.empty();
        } catch (FileNotFoundException exception) {
            System.out.println("File was not found or you have no access. Cannot execute script");
        } catch (ExecuteScriptException exception) {
            System.out.println(exception.getMessage());
        }
        return Optional.empty();
    }

    public void exit() {
        System.exit(0);
    }

    public Optional<Request> removeAt(String indexRaw) {
        return Optional.of(new Request("remove_at", new String[]{indexRaw}));
    }

    public Optional<Request> addIfMin() {
        Person person = this.inputManager.inputPerson();
        return Optional.of(new Request("add_if_min", person));
    }

    public Optional<Request> reorder() {
        return Optional.of(new Request("reorder"));
    }

    public Optional<Request> filterGreaterThanNationality(String nationalityRaw) {
        return Optional.of(new Request("filter_greater_than_nationality", new String[]{nationalityRaw}));
    }

    public Optional<Request> printDescending() {
        return Optional.of(new Request("print_descending"));
    }

    public Optional<Request> printFieldDescendingOrder() {
        return Optional.of(new Request("print_field_descending_order"));
    }

}
