package DAOPattern;

import Entities.Country;
import Entities.Person;
import Services.LoginCredentials;

import java.util.List;
import java.util.Optional;

public interface PersonDAO {

    List<Person> readAll();

    Optional<Long> create(Person person, long userId);

    boolean updateById(long id, Person person, long userId);

    boolean clear(long userId);

    boolean checkById(long personId, long userId);

    boolean removeById(long personId);

    boolean reorder(long userId);

}
