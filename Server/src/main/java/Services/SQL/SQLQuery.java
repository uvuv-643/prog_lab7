package Services.SQL;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum SQLQuery {

    PERSON_CHECK_BY_ID("person/check_by_id.sql"),
    PERSON_CLEAR("person/clear.sql"),
    PERSON_CREATE("person/create.sql"),
    PERSON_READ_ALL("person/read_all.sql"),
    PERSON_REMOVE_BY_ID("person/remove_by_id.sql"),
    PERSON_REORDER("person/reorder.sql"),
    PERSON_UPDATE("person/update.sql"),
    LOCATION_CREATE("location/create.sql"),
    COORDINATE_CREATE("coordinate/create.sql");

    private Path path;
    private final static String PATH_TO_SCRIPTS = "./Server/src/main/java/Services/SQL/query";

    SQLQuery(String path) {
        this.path = Paths.get(PATH_TO_SCRIPTS, path).toAbsolutePath();
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
