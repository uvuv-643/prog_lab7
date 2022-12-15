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
import java.util.List;
import java.util.Scanner;

/**
 * Класс, предназначенный для управления сохранением информации в файл
 * @author uvuv-643
 * @version 1.0
 */
public class FileManager {

    /** Путь к файлу, хранящему коллекцию - переменная среды окружения path */
    final String path = System.getenv("path");

    /** объект для работы с форматом JSON */
    Gson gson;

    /**
     * Конструктор класса управления работы с файлами
     */
    public FileManager() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ZonedDateTime.class, new TimeAdapter());
        gson = builder.create();
    }

    /**
     * Прочитать информацию с файла в коллекцию
     * @return List - коллекция, прочитанная из файла
     * @throws IOException - выбрасывается в случае ошибки работы с форматом JSON
     * @throws NullPointerException - выбрасывается в случае проблем с обработкой JSON
     */
    @Deprecated
    public List<Person> readFromFile() throws IOException {
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
        List<Person> collection = new ArrayList<>(Arrays.asList(persons));
        scanner.close();
        return collection;
    }

    /**
     * Записать в файл коллекцию
     * @param collection - коллекция, которую необходимо записать
     * @throws IOException - выбрасывается в случае ошибки работы с форматом JSON
     */
    @Deprecated
    public void writeInFile(List<Person> collection) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(path));
        String data = gson.toJson(collection.toArray(Person[]::new));
        out.write(data);
        out.close();
    }

}
