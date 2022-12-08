package Output;

import Entities.Person;

import java.util.ArrayList;

public class OutputManager {

    public String showCollection(ArrayList<Person> collection) {
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
