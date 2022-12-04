package App;

import CommandPattern.Invoker;
import Services.Request;
import Services.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Terminal {

    Scanner scanner;
    private final Invoker invoker;
    private final Client client;

    public Terminal(Invoker invoker, Client client) {
        this.invoker = invoker;
        this.client = client;
    }

    public void startFile(String filename) throws FileNotFoundException {
        String pathToFile = new File(filename).getAbsolutePath();
        File file = new File(pathToFile);
        this.scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String commandLine = scanner.nextLine();
            try {
                lineHandler(commandLine);
            } catch (NullPointerException e) {
                System.out.println("There is an incorrect command in file. The execution is interrupted");
            }
        }
        System.out.println("File was executed");
    }

    public void startKeyboard() {
        this.scanner = new Scanner(System.in);
        System.out.println("Hint: type help to get list of the commands");
        while (true) {
            System.out.println("Enter command: ");
            String commandLine = scanner.nextLine();
            try {
                lineHandler(commandLine);
            } catch (NullPointerException e) {
                System.out.println("There is no such command");
            }
        }
    }

    protected void lineHandler(String line) throws NullPointerException {
        String[] commandLine = line.replaceAll("\s{2,}", " ").trim().split("\s");
        String command = commandLine[0].trim();
        Optional<Request> lineHandlerResult = invoker.execute(command, Arrays.copyOfRange(commandLine, 1, commandLine.length));
        System.out.println("Executing <" + command + ">...");
        if (lineHandlerResult.isPresent()) {
            Request request = lineHandlerResult.get();
            client.send(request);
            Optional<Response> receiveResult = client.receive();
            if (receiveResult.isPresent()) {
                Response response = receiveResult.get();
                String responseMessage = response.getMessage();
                System.out.println(Objects.requireNonNullElse(responseMessage, "Deserialization error"));
                if (response.isSuccess()) {
                    System.out.println("Command was executed successfully");
                } else {
                    System.out.println("Command wasn't executed");
                }
            } else {
                System.out.println("Lost connection with server.");
                System.exit(-1);
            }
        } else {
            System.out.println("Command cannot be executed");
        }
    }

}
