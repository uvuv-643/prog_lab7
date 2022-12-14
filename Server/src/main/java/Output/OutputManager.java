package Output;

import Entities.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для вывода информации клиенту с сервера
 * @author uvuv-643
 * @version 1.0
 */
public class OutputManager {

    /**
     * Обработать коллекцию и представить её в текстовом виде
     * @param collection - исходная коллекция
     * @return String - текстовое представление коллекции
     */
    public String showCollection(List<Person> collection) {
        StringBuilder stringBuilder = new StringBuilder();
        if (collection.isEmpty()) {
            stringBuilder.append("Collection is empty").append("\n");
        } else {
            stringBuilder.append("Elements in collection:").append("\n\n");
            for (Person person : collection) {
                stringBuilder.append(person).append("\n\n");
            }
        }
        return stringBuilder.toString();
    }

}
