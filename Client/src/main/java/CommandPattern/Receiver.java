package CommandPattern;

import App.Client;
import Entities.Person;
import Exceptions.ExecuteScriptException;
import Input.InputManager;
import Services.LoginCredentials;
import Services.Request;
import App.Terminal;

import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * Класс, представляющий собой сущность исполнителя команд
 * @author uvuv-643
 * @version 1.0
 */
public class Receiver {

    /** Объект, управляющий вводом информации со стандартного потока ввода */
    private final InputManager inputManager = new InputManager();

    /** Клиент, вызывающий определенную команду */
    private final Client client;

    /**
     * Конструктор исполнителя команд
     * @param client - клиент, вызывающй команду
     */
    public Receiver(Client client) {
        this.client = client;
    }

    /**
     * Команда "info"
     * @return Request - запрос, который может быть отправлен на сервер
     */
    public Optional<Request> info() {
        return Optional.of(new Request("info"));
    }

    /**
     * Команда "help"
     * @return Request - запрос, который может быть отправлен на сервер
     */
    public Optional<Request> help() {
        return Optional.of(new Request("help"));
    }

    /**
     * Команда "show"
     * @return Request - запрос, который может быть отправлен на сервер
     */
    public Optional<Request> show() {
        return Optional.of(new Request("show"));
    }

    /**
     * Команда "add {person}". Будет осуществлен ввод информации через inputManager
     * @return Request - запрос, который может быть отправлен на сервер
     * @see InputManager
     */
    public Optional<Request> add() {
        Person person = this.inputManager.inputPerson();
        return Optional.of(new Request("add", person));
    }

    /**
     * Команда "update id {person}". Будет осуществлен ввод информации через inputManager
     * @param idRaw - аргумент id для команды
     * @return Request - запрос, который может быть отправлен на сервер
     * @see InputManager
     */
    public Optional<Request> update(String idRaw) {
        Person person = this.inputManager.inputPerson();
        return Optional.of(new Request("update", new String[]{idRaw}, person));
    }

    /**
     * Команда "remove_by_id id"
     * @param idRaw - аргумент id для команды
     * @return Request - запрос, который может быть отправлен на сервер
     */
    public Optional<Request> removeById(String idRaw) {
        return Optional.of(new Request("remove_by_id", new String[]{idRaw}));
    }

    /**
     * Команда "clear"
     * @return Request - запрос, который может быть отправлен на сервер
     */
    public Optional<Request> clear() {
        return Optional.of(new Request("clear"));
    }

    /**
     * Команда "save"
     * @return Request - запрос, который может быть отправлен на сервер
     */
    public Optional<Request> save() {
        return Optional.empty();
    }

    /**
     * Команда "execute_script script"
     * Осуществляется полная обработка файла script и отправляются все скрипты в данном файле
     * @param invoker - объект, совершивший вызов данной команды
     * @param filename - путь к файлу, необходимый к исполнению
     * @return Request - запрос, который может быть отправлен на сервер
     */
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

    /**
     * Команда "exit"
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Команда "remove_at index"
     * @param indexRaw - аргумент index для команды
     * @return Request - запрос, который может быть отправлен на сервер
     */
    public Optional<Request> removeAt(String indexRaw) {
        return Optional.of(new Request("remove_at", new String[]{indexRaw}));
    }

    /**
     * Команда "add_if_min {person}".
     * Будет осуществлен ввод информации через inputManager
     * @return Request - запрос, который может быть отправлен на сервер
     * @see InputManager
     */
    public Optional<Request> addIfMin() {
        Person person = this.inputManager.inputPerson();
        return Optional.of(new Request("add_if_min", person));
    }

    /**
     * Команда "reorder"
     * @return Request - запрос, который может быть отправлен на сервер
     */
    public Optional<Request> reorder() {
        return Optional.of(new Request("reorder"));
    }

    /**
     * Команда "filter_greater_than_nationality nationality"
     * @param nationalityRaw - аргумент nationality для команды
     * @return Request - запрос, который может быть отправлен на сервер
     */
    public Optional<Request> filterGreaterThanNationality(String nationalityRaw) {
        return Optional.of(new Request("filter_greater_than_nationality", new String[]{nationalityRaw}));
    }

    /**
     * Команда "print_descending"
     * @return Request - запрос, который может быть отправлен на сервер
     */
    public Optional<Request> printDescending() {
        return Optional.of(new Request("print_descending"));
    }

    /**
     * Команда "print_field_descending_order"
     * @return Request - запрос, который может быть отправлен на сервер
     */
    public Optional<Request> printFieldDescendingOrder() {
        return Optional.of(new Request("print_field_descending_order"));
    }

    public Optional<Request> check(String idRaw) {
        return Optional.of(new Request("check_id", new String[]{idRaw}));
    }

    public Optional<Request> auth(String login, String password) {
        return Optional.of(new Request("auth", new String[]{login, password}));
    }

    public Optional<Request> register(String login, String password) {
        return Optional.of(new Request("register", new String[]{login, password}));
    }

}
