package App;

import CommandPattern.Invoker;
import Entities.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Terminal {

    Scanner scanner;
    private final Invoker invoker;
    private final ArrayList<Person> collection;

    public Terminal(Invoker invoker, ArrayList<Person> collection) {
        this.invoker = invoker;
        this.collection = collection;
    }

    public void startFile(String filename) throws FileNotFoundException {
        String pathToFile = new File(filename).getAbsolutePath();
        File file = new File(pathToFile);
        this.scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String commandLine = scanner.nextLine();
            try {
                boolean result = lineHandler(commandLine);
                if (result) {
                    System.out.println("Command <" + commandLine + "> completed successfully");
                } else {
                    System.out.println("Command <" + commandLine + "> was not completed");
                }
            } catch (NullPointerException e) {
                System.out.println("There is an incorrect command in file. The execution is interrupted");
            }
        }
        System.out.println("File was executed");
    }

    /**
     * User input
     */
    public void startKeyboard() {
        this.scanner = new Scanner(System.in);
        System.out.println("Hint: type help to get list of the commands");
        while (true) {
            System.out.println("Enter command: ");
            String commandline = scanner.nextLine();
            try {
                boolean result = lineHandler(commandline);
                if (result) {
                    System.out.println("Command completed successfully");
                } else {
                    System.out.println("Command was not completed");
                }
            } catch (NullPointerException e) {
                System.out.println("There is no such command");
            }
        }
    }

    protected boolean lineHandler(String line) throws NullPointerException {
        String[] commandLine = line.replaceAll("\s{2,}", " ").trim().split("\s");
        String command = commandLine[0].trim();
        return invoker.execute(command, collection, Arrays.copyOfRange(commandLine, 1, commandLine.length));
    }

}
