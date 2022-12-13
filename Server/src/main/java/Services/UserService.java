package Services;

import DAOPattern.UserDAO;
import Services.SQL.SQLManager;

import java.util.Optional;

public class UserService implements UserDAO {

    @Override
    public Optional<Long> create(LoginCredentials loginCredentials) {
        return SQLManager.executeQueryUserCreate(loginCredentials.getLogin(), loginCredentials.getPassword());
    }

    @Override
    public Optional<Long> check(LoginCredentials loginCredentials) {
        return SQLManager.executeQueryUserCheck(loginCredentials.getLogin(), loginCredentials.getPassword());
    }

}
