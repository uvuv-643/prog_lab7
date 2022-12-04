package Input.FileManager;

import Entities.Person;
import Input.Adapters.TimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FileManager {

    final String path = System.getenv("path");
    Gson gson;

    public FileManager() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ZonedDateTime.class, new TimeAdapter());
        gson = builder.create();
    }

    /**
     * filling the collection by environment variable
     */
    public ArrayList<Person> readFromFile() throws IOException, NullPointerException {
        Scanner scanner = new Scanner(new File(path));
        StringBuilder fileData = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            fileData.append(line);
        }
        Person[] persons = gson.fromJson(fileData.toString(), Person[].class);
        if (persons == null) {
            persons = new Person[]{};
        }
        ArrayList<Person> collection = new ArrayList<>(Arrays.asList(persons));
        scanner.close();
        return collection;
    }

    public void writeInFile(ArrayList<Person> collection) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(path));
        String data = gson.toJson(collection.toArray(Person[]::new));
        out.write(data);
        out.close();
    }

}
