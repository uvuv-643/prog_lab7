package Services;

import DAOPattern.PersonDAO;
import Entities.Color;
import Entities.Country;
import Entities.Person;
import Services.SQL.SQLManager;

import java.util.List;
import java.util.Optional;

public class PersonService implements PersonDAO {


    @Override
    public List<Person> readAll() {
        return null;
    }

    @Override
    public Optional<Long> create(Person person, long userId) {
        Optional<Long> coordinateId = SQLManager.executeQueryCoordinateCreate(person.getCoordinates());
        Optional<Long> locationId = SQLManager.executeQueryLocationCreate(person.getLocation());
        Optional<Long> personId = Optional.empty();
        if (coordinateId.isPresent()) {
            Long actualLocationId = locationId.orElse(null);
            personId = SQLManager.executeQueryPersonCreate(coordinateId.get(), person.getHeight(),  person.getWeight(), person.getEyeColor(), person.getNationality(), actualLocationId, userId);
        } else {
            System.out.println("Coordinates is null ???");
        }
        return personId;
    }

    @Override
    public boolean updateById(long id, Person person, long userId) {
        return false;
    }

    @Override
    public boolean clear(long userId) {
        return false;
    }

    @Override
    public boolean checkById(long personId, long userId) {
        return false;
    }

    @Override
    public boolean removeById(long personId, long userId) {
        return false;
    }

    @Override
    public boolean reorder(long userId) {
        return false;
    }

}
