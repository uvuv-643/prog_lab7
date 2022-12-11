package Services;

import java.io.Serial;
import java.io.Serializable;

public class LoginCredentials implements Serializable {

    @Serial
    private static final long serialVersionUID = 666777666L;

    private String login;
    private String password;

    public LoginCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
