package Services;

import DAOPattern.PersonDAO;
import Entities.Country;
import Entities.Person;

import java.util.List;
import java.util.Optional;

public class PersonService implements PersonDAO {


    @Override
    public List<Person> readAll() {
        return null;
    }

    @Override
    public boolean create(Person person, long userId) {
        return false;
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
