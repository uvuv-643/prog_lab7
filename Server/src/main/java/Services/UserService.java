package Services;

import DAOPattern.UserDAO;
import Services.SQL.SQLManager;

import java.util.Optional;

public class UserService implements UserDAO {

    private static final HashEncrypter hashEncrypter = new HashEncrypter();

    public UserService() {
        SQLManager.initUsers();
    }

    @Override
    public Optional<Long> create(LoginCredentials loginCredentials) {
        return SQLManager.executeQueryUserCreate(loginCredentials.getLogin(), hashEncrypter.encryptString(loginCredentials.getPassword()));
    }

    @Override
    public Optional<Long> check(LoginCredentials loginCredentials) {
        return SQLManager.executeQueryUserCheck(loginCredentials.getLogin(), hashEncrypter.encryptString(loginCredentials.getPassword()));
    }

}
