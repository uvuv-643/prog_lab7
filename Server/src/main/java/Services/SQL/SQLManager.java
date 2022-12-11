package Services.SQL;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;

public class SQLManager {

    public static boolean executeQuery(SQLQuery sqlQuery, Object ...args) {
        try {
            String content = Files.readString(sqlQuery.getPath(), Charset.defaultCharset());
            if (sqlQuery.equals(SQLQuery.PERSON_CREATE)) {
                System.out.println(args.length);
            }
            return true;
        } catch (IOException exception) {
            return false;
        }
    }

}
