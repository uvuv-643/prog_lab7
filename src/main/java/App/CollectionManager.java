package App;

import Entities.Person;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class CollectionManager {
    ArrayList<Person> persons = new ArrayList<>();
    /**
     * The Init time.
     */
    ZonedDateTime initTime = ZonedDateTime.now();
}
