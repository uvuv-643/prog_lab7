package Services;

import DAOPattern.PersonDAO;
import Entities.Color;
import Entities.Country;
import Entities.Person;
import Services.SQL.SQLManager;

import java.util.List;
import java.util.Optional;

public class PersonService implements PersonDAO {

    public PersonService() {
        SQLManager.initPerson();
    }

    @Override
    public List<Person> readAll() {
        return SQLManager.executeQueryReadAll();
    }

    @Override
    public Optional<Long> create(Person person, long userId) {
        Optional<Long> coordinateId = SQLManager.executeQueryCoordinateCreate(person.getCoordinates());
        Optional<Long> locationId = SQLManager.executeQueryLocationCreate(person.getLocation());
        Optional<Long> personId = Optional.empty();
        if (coordinateId.isPresent()) {
            Long actualLocationId = locationId.orElse(null);
            personId = SQLManager.executeQueryPersonCreate(person.getName(), coordinateId.get(), person.getHeight(),  person.getWeight(), person.getEyeColor(), person.getNationality(), actualLocationId, userId);
        } else {
            System.out.println("Coordinates is null ???");
        }
        return personId;
    }

    @Override
    public boolean updateById(long id, Person person, long userId) {
        Optional<Long> coordinateId = SQLManager.executeQueryCoordinateCreate(person.getCoordinates());
        Optional<Long> locationId = SQLManager.executeQueryLocationCreate(person.getLocation());
        if (coordinateId.isPresent()) {
            Long actualLocationId = locationId.orElse(null);
            return SQLManager.executeQueryPersonUpdate(person.getName(), coordinateId.get(), person.getHeight(),  person.getWeight(), person.getEyeColor(), person.getNationality(), actualLocationId, id);
        } else {
            System.out.println("Coordinates is null ???");
        }
        return false;
    }

    @Override
    public boolean clear(long userId) {
        return SQLManager.executeQueryPersonClear(userId);
    }

    @Override
    public boolean checkById(long personId, long userId) {
        return SQLManager.executeQueryPersonCheckById(personId, userId);
    }

    @Override
    public boolean removeById(long personId) {
        return SQLManager.executeQueryPersonRemoveById(personId);
    }

    @Override
    public boolean reorder(long userId) {
        return SQLManager.executeQueryPersonReorder(userId);
    }

}
