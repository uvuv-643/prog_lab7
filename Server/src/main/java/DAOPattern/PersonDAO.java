package DAOPattern;

import Entities.Country;
import Entities.Person;
import Services.LoginCredentials;

import java.util.List;

public interface PersonDAO {

    List<Person> readAll();

    boolean create(Person person, long userId);

    boolean updateById(long id, Person person, long userId);

    boolean clear(long userId);

    boolean checkById(long personId, long userId);

    boolean removeById(long personId, long userId);

    boolean reorder(long userId);

}
