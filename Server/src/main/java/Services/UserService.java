package Services;

import DAOPattern.UserDAO;

import java.util.Optional;

public class UserService implements UserDAO {

    @Override
    public Optional<Long> create(LoginCredentials loginCredentials) {
        return Optional.empty();
    }

    @Override
    public Optional<Long> check(LoginCredentials loginCredentials) {
        return Optional.empty();
    }

}
