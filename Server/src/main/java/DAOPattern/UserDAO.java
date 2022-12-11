package DAOPattern;

import Services.LoginCredentials;

public interface UserDAO {

    boolean create(LoginCredentials loginCredentials);

    boolean check(LoginCredentials loginCredentials);

}
