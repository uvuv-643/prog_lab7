package Services;

import DAOPattern.UserDAO;

public class UserService implements UserDAO {

    @Override
    public boolean create(LoginCredentials loginCredentials) {
        return false;
    }

    @Override
    public boolean check(LoginCredentials loginCredentials) {
        return false;
    }

}
