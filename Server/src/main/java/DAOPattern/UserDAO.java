package DAOPattern;

import Services.LoginCredentials;

import java.util.Optional;

public interface UserDAO {

    Optional<Long> create(LoginCredentials loginCredentials);

    Optional<Long> check(LoginCredentials loginCredentials);

}
