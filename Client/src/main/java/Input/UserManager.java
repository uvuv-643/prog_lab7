package Input;

import App.Client;
import CommandPattern.Invoker;
import Exceptions.ExecuteCommandException;
import Services.LoginCredentials;
import Services.Request;
import Services.Response;

import java.io.Console;
import java.util.Optional;
import java.util.Scanner;

public class UserManager {

    private final Client client;
    private final Invoker invoker;
    private final Scanner scanner;
    private final static int LOGIN_ATTEMPTS = 3;


    public UserManager(Client client, Invoker invoker) {
        this.client = client;
        this.invoker = invoker;
        this.scanner = new Scanner(System.in);
    }

    private Optional<Response> loginUser(LoginCredentials loginCredentials) {
        try {
            Optional<Request> lineHandlerResult = invoker.execute("auth", new String[]{loginCredentials.getLogin(), loginCredentials.getPassword()});
            if (lineHandlerResult.isPresent()) {
                lineHandlerResult.get().setLoginCredentials(loginCredentials);
                client.send(lineHandlerResult.get());
                return client.receive();
            } else {
                return Optional.empty();
            }
        } catch (ExecuteCommandException exception) {
            return Optional.empty();
        }
    }

    private Optional<Response> registerUser(LoginCredentials loginCredentials) {
        try {
            Optional<Request> lineHandlerResult = invoker.execute("register", new String[]{loginCredentials.getLogin(), loginCredentials.getPassword()});
            if (lineHandlerResult.isPresent()) {
                lineHandlerResult.get().setLoginCredentials(loginCredentials);
                client.send(lineHandlerResult.get());
                return client.receive();
            } else {
                return Optional.empty();
            }
        } catch (ExecuteCommandException exception) {
            return Optional.empty();
        }
    }

    private Optional<LoginCredentials> startAuth(int attemptLeft) {
        Console console = System.console();
        if (console == null) {
            System.out.println("To login you must work through console");
            return Optional.empty();
        }
        System.out.println("Input login: ");
        String login = scanner.nextLine().trim();
        String password = new String(console.readPassword("Input your password: "));
        LoginCredentials loginCredentials = new LoginCredentials(login, password);
        Optional<Response> loginResponse = loginUser(loginCredentials);
        if (loginResponse.isPresent() && loginResponse.get().isSuccess()) {
            System.out.println("You are successfully logged in");
            return Optional.of(loginCredentials);
        } else {
            attemptLeft--;
            if (attemptLeft > 0) {
                System.out.println("There is no user with this login / password. Attempts left: " + (attemptLeft));
                return startAuth(attemptLeft);
            }
            return Optional.empty();
        }
    }

    private Optional<LoginCredentials> startRegistering() {
        Console console = System.console();
        if (console == null) {
            System.out.println("To register you must work through console");
            return Optional.empty();
        }
        System.out.println("Input login: ");
        String login = scanner.nextLine().trim();
        String password = new String(System.console().readPassword("Input your password: "));
        LoginCredentials loginCredentials = new LoginCredentials(login, password);
        Optional<Response> registerResponse = registerUser(loginCredentials);
        if (registerResponse.isPresent() && registerResponse.get().isSuccess()) {
            System.out.println("You are successfully registered in");
            return Optional.of(loginCredentials);
        } else {
            System.out.println("User with this login already exists");
            return startRegistering();
        }
    }

    public LoginCredentials loginOrRegister() {
        System.out.println("Do you have an account? [y/n]");
        String loginStatus = scanner.nextLine().trim();
        if (loginStatus.equals("y")) {
            Optional<LoginCredentials> loginCredentials = this.startAuth(LOGIN_ATTEMPTS);
            return loginCredentials.orElse(null);
        } else {
            System.out.println("Do you want to create an account? [y/n]");
            String registerStatus = scanner.nextLine().trim();
            if (registerStatus.equals("y")) {
                Optional<LoginCredentials> loginCredentials = this.startRegistering();
                return loginCredentials.orElse(null);
            }
        }
        return null;
    }



}
