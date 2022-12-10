package App;

import CommandPattern.Invoker;
import Exceptions.ExecuteCommandException;
import Exceptions.ExecuteScriptException;
import Services.Request;
import Services.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

 /**
 * Класс для осуществления взаимодействия с пользователем через файлы / стандартный ввод
 * @author uvuv-643
 * @version 1.0
 */
public class Terminal {

    /** Объект для ввода информации из стандартного потока / файла */
    Scanner scanner;

    /**
     * Invoker, предназначенный для запуска команд
     * @see Invoker
     */
    private final Invoker invoker;

    /** Клиент, работающий в текущем терминале */
    private final Client client;

     /**
      * Инициализация терминала
      * @param invoker - объект, вызывающий команды
      * @param client
      */
    public Terminal(Invoker invoker, Client client) {
        this.invoker = invoker;
        this.client = client;
    }

     /**
      * Прочитать файл и запустить все команды построчно, находящиеся в нём.
      * @param filename - относительный путь к файлу
      * @throws FileNotFoundException - файл не был найден
      * @throws ExecuteScriptException - бесконечная рекурсия
      */
    public void startFile(String filename) throws FileNotFoundException, ExecuteScriptException {
        String pathToFile = new File(filename).getAbsolutePath();
        if (this.invoker.isExecutedScript(filename)) {
            throw new ExecuteScriptException("Infinite recursion. Stopped executing scripts");
        }
        this.invoker.addExecutedScript(filename);
        File file = new File(pathToFile);
        this.scanner = new Scanner(file);
        try {
            while (scanner.hasNextLine()) {
                String commandLine = scanner.nextLine();
                lineHandler(commandLine);
            }
            System.out.println("File " + filename + " was executed");
        } catch (ExecuteCommandException e) {
            System.out.println("There is an incorrect command in file " + filename + ". The execution is interrupted");
        }

    }

     /**
      * Начать взаимодействие с пользователем посредством стандартного потока ввода
      */
    public void startKeyboard() {
        this.scanner = new Scanner(System.in);
        System.out.println("Hint: type help to get list of the commands");
        while (true) {
            System.out.println("Enter command: ");
            String commandLine = scanner.nextLine();
            try {
                invoker.clearScriptList();
                lineHandler(commandLine);
            } catch (ExecuteCommandException e) {
                System.out.println("There is no such command");
            }
        }
    }

     /**
      * Обработать единичную команду
      * @param line - строка, содержащая команду (возможно некорректную)
      * @throws ExecuteCommandException - команда некорректна
      */
    protected void lineHandler(String line) throws ExecuteCommandException {
        String[] commandLine = line.replaceAll("\s{2,}", " ").trim().split("\s");
        if (commandLine.length == 0) {
            throw new ExecuteCommandException("Given empty command");
        }
        String command = commandLine[0].trim();
        Optional<Request> lineHandlerResult = invoker.execute(command, Arrays.copyOfRange(commandLine, 1, commandLine.length));
        if (lineHandlerResult.isPresent()) {
            System.out.println("Executing <" + command + ">...");
            Request request = lineHandlerResult.get();
            client.send(request);
            Optional<Response> receiveResult = client.receive();
            if (receiveResult.isPresent()) {
                Response response = receiveResult.get();
                String responseMessage = response.getMessage();
                if (responseMessage == null) {
                    System.out.println("Deserialization error");
                } else if (!responseMessage.isEmpty()) {
                    System.out.println(responseMessage);
                }
                if (response.isSuccess()) {
                    System.out.println("Command was executed successfully");
                } else {
                    System.out.println("Command wasn't executed");
                }
            } else {
                System.out.println("Lost connection with server");
                System.exit(-1);
            }
        } else {
            if (!command.equals("execute_script")) {
                System.out.println("Command <" + command + "> cannot be executed");
            }
        }
    }

}
