package Services.SQL;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum SQLQuery {

    PERSON_CHECK_BY_ID("query/person/check_by_id.sql"),
    PERSON_CLEAR("query/person/clear.sql"),
    PERSON_CREATE("query/person/create.sql"),
    PERSON_READ_ALL("query/person/read_all.sql"),
    PERSON_REMOVE_BY_ID("query/person/remove_by_id.sql"),
    PERSON_REORDER("query/person/reorder.sql"),
    PERSON_UPDATE("query/person/update.sql"),
    USER_CREATE("query/user/create.sql"),
    USER_CHECK("query/user/check.sql"),
    LOCATION_CREATE("query/location/create.sql"),
    COORDINATE_CREATE("query/coordinate/create.sql"),
    PERSON_INIT("init/persons.sql"),
    USERS_INIT("init/users.sql");

    private Path path;
    private final static String PATH_TO_SCRIPTS = "./sql/";

    SQLQuery(String path) {
        this.path = Paths.get(PATH_TO_SCRIPTS, path);
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
