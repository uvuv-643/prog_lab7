package Services.SQL;

import Entities.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Exceptions.ValidationException;
import Input.Validation.CustomValidators.EyeColorValidator;
import Input.Validation.CustomValidators.NationalityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SQLManager {

    private static final Logger logger = LogManager.getLogger("info");
    private final static Connection connection = getConnection();
    private static final String DB_URL = "jdbc:postgresql://localhost:5433/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "ch4p72";

    protected static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgresQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return null;
        }
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException exception) {
            logger.info(exception.getMessage() + " Cannot create connection");
        }
        return connection;
    }

    public static List<Person> executeQueryReadAll() {
        logger.info("Attempt to execute <execute_query_read_all>");
        SQLQuery currentQuery = SQLQuery.PERSON_READ_ALL;
        if (connection != null) {
            try {
                String content = Files.readString(currentQuery.getPath(), Charset.defaultCharset());
                PreparedStatement preparedStatement = connection.prepareStatement(content);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Person> personList = new ArrayList<>();
                while (resultSet.next()) {
                    EyeColorValidator eyeColorValidator = new EyeColorValidator();
                    NationalityValidator nationalityValidator = new NationalityValidator();
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    Coordinates coordinates = new Coordinates(resultSet.getFloat("coordinate_x"), resultSet.getFloat("coordinate_y"));
                    ZonedDateTime creationDate = ZonedDateTime.of(resultSet.getTimestamp("created_at").toLocalDateTime(), ZoneId.systemDefault());
                    int height = resultSet.getInt("height");
                    float weight = resultSet.getFloat("weight");
                    Optional<Color> eyeColor = eyeColorValidator.validate(resultSet.getString("color")).getValidatedData();
                    Optional<Country> nationality = nationalityValidator.validate(resultSet.getString("country")).getValidatedData();
                    Optional<Location> location = Optional.empty();
                    if (resultSet.getBoolean("is_location_present")) {
                        location = Optional.of(new Location(resultSet.getDouble("location_x"), resultSet.getInt("location_y"), resultSet.getString("location_title")));
                    }
                    Color actualColor = eyeColor.orElse(null);
                    Country actualCountry = nationality.orElse(null);
                    Location actualLocation = location.orElse(null);
                    long userId = resultSet.getLong("user_id");
                    String userLogin = resultSet.getString("user_login");
                    Person person = Person.personCreator(id, name, coordinates, creationDate, height, weight, actualColor, actualCountry, actualLocation, userId, userLogin);
                    personList.add(person);
                }
                logger.info("Operation was executed successfully");
                return personList;
            } catch (IOException exception) {
                logger.info("Cannot read data from .sql file. " + exception);
            } catch (SQLException exception) {
                logger.info("SQL error. " + exception);
            } catch (ValidationException exception) {
                logger.info("Error with validation. " + exception);
            }
        } else {
            logger.info("Cannot make connection with database.");
        }
        return new ArrayList<>();
    }

    public static Optional<Long> executeQueryPersonCreate(String name, Long coordinatesId, int height, float weight, Color color, Country country, Long locationId, Long userId) {
        logger.info("Attempt to execute <execute_query_person_create>");
        SQLQuery currentQuery = SQLQuery.PERSON_CREATE;
        if (connection != null) {
            try {
                String content = Files.readString(currentQuery.getPath(), Charset.defaultCharset());
                PreparedStatement preparedStatement = connection.prepareStatement(content);
                preparedStatement.setString(1, name);
                preparedStatement.setLong(2, coordinatesId);
                preparedStatement.setInt(3, height);
                preparedStatement.setFloat(4, weight);
                if (color != null) {
                    preparedStatement.setString(5, color.name());
                } else {
                    preparedStatement.setNull(5, Types.VARCHAR);
                }
                if (country != null) {
                    preparedStatement.setString(6, country.name());
                } else {
                    preparedStatement.setNull(6, Types.VARCHAR);
                }
                if (locationId != null) {
                    preparedStatement.setLong(7, locationId);
                } else {
                    preparedStatement.setNull(7, Types.INTEGER);
                }
                preparedStatement.setLong(8, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    logger.info("Operation was executed successfully");
                    return Optional.of(resultSet.getLong("id"));
                } else {
                    logger.info("Cannot execute operation.");
                }
            } catch (IOException exception) {
                logger.info("Cannot read data from .sql file.");
            } catch (SQLException exception) {
                logger.info("SQL error. " + exception);
            }
        } else {
            logger.info("Cannot make connection with database.");
        }
        return Optional.empty();
    }

    public static boolean executeQueryPersonUpdate(String name, Long coordinatesId, int height, float weight, Color color, Country country, Long locationId, Long id) {
        logger.info("Attempt to execute <execute_query_person_update>");
        SQLQuery currentQuery = SQLQuery.PERSON_UPDATE;
        if (connection != null) {
            try {
                String content = Files.readString(currentQuery.getPath(), Charset.defaultCharset());
                PreparedStatement preparedStatement = connection.prepareStatement(content);
                preparedStatement.setString(1, name);
                preparedStatement.setLong(2, coordinatesId);
                preparedStatement.setInt(3, height);
                preparedStatement.setFloat(4, weight);
                if (color != null) {
                    preparedStatement.setString(5, color.name());
                } else {
                    preparedStatement.setNull(5, Types.VARCHAR);
                }
                if (country != null) {
                    preparedStatement.setString(6, country.name());
                } else {
                    preparedStatement.setNull(6, Types.VARCHAR);
                }
                if (locationId != null) {
                    preparedStatement.setLong(7, locationId);
                } else {
                    preparedStatement.setNull(7, Types.INTEGER);
                }
                preparedStatement.setLong(8, id);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    logger.info("Operation was executed successfully");
                    return true;
                } else {
                    logger.info("Cannot execute operation.");
                }
            } catch (IOException exception) {
                logger.info("Cannot read data from .sql file.");
            } catch (SQLException exception) {
                logger.info("SQL error. " + exception);
            }
        } else {
            logger.info("Cannot make connection with database.");
        }
        return false;
    }

    public static Optional<Long> executeQueryCoordinateCreate(Coordinates coordinates) {
        logger.info("Attempt to execute <execute_query_coordinate_create>");
        SQLQuery currentQuery = SQLQuery.COORDINATE_CREATE;
        if (connection != null) {
            try {
                String content = Files.readString(currentQuery.getPath(), Charset.defaultCharset());
                PreparedStatement preparedStatement = connection.prepareStatement(content);
                preparedStatement.setFloat(1, coordinates.getX());
                preparedStatement.setFloat(2, coordinates.getY());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    logger.info("Operation was executed successfully");
                    return Optional.of(resultSet.getLong("id"));
                } else {
                    logger.info("Cannot execute operation.");
                }
            } catch (IOException exception) {
                logger.info("Cannot read data from .sql file.");
            } catch (SQLException exception) {
                logger.info("SQL error. " + exception);
            }
        } else {
            logger.info("Cannot make connection with database.");
        }
        return Optional.empty();
    }

    public static Optional<Long> executeQueryLocationCreate(Location location) {
        if (location == null) return Optional.empty();
        logger.info("Attempt to execute <execute_query_location_create>");
        SQLQuery currentQuery = SQLQuery.LOCATION_CREATE;
        if (connection != null) {
            try {
                String content = Files.readString(currentQuery.getPath(), Charset.defaultCharset());
                PreparedStatement preparedStatement = connection.prepareStatement(content);
                preparedStatement.setDouble(1, location.getX());
                preparedStatement.setInt(2, location.getY());
                preparedStatement.setString(3, location.getName());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    logger.info("Operation was executed successfully");
                    return Optional.of(resultSet.getLong("id"));
                } else {
                    logger.info("Cannot execute operation.");
                }
            } catch (IOException exception) {
                logger.info("Cannot read data from .sql file.");
            } catch (SQLException exception) {
                logger.info("SQL error. " + exception);
            }
        } else {
            logger.info("Cannot make connection with database.");
        }
        return Optional.empty();
    }

    public static boolean executeQueryPersonCheckById(long personId, long userId) {
        logger.info("Attempt to execute <execute_query_person_check_by_id>");
        SQLQuery currentQuery = SQLQuery.PERSON_CHECK_BY_ID;
        if (connection != null) {
            try {
                String content = Files.readString(currentQuery.getPath(), Charset.defaultCharset());
                PreparedStatement preparedStatement = connection.prepareStatement(content);
                preparedStatement.setLong(1, personId);
                preparedStatement.setLong(2, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    logger.info("Operation was executed successfully");
                    return true;
                } else {
                    logger.info("Cannot execute operation.");
                }
            } catch (IOException exception) {
                logger.info("Cannot read data from .sql file.");
            } catch (SQLException exception) {
                logger.info("SQL error. " + exception);
            }
        } else {
            logger.info("Cannot make connection with database.");
        }
        return false;
    }

    public static boolean executeQueryPersonReorder(long userId) {
        logger.info("Attempt to execute <execute_query_person_reorder>");
        SQLQuery currentQuery = SQLQuery.PERSON_REORDER;
        if (connection != null) {
            try {
                String content = Files.readString(currentQuery.getPath(), Charset.defaultCharset());
                PreparedStatement preparedStatement = connection.prepareStatement(content);
                preparedStatement.setLong(1, userId);
                preparedStatement.setLong(2, userId);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    logger.info("Operation was executed successfully");
                    return true;
                } else {
                    logger.info("Cannot execute operation.");
                }
            } catch (IOException exception) {
                logger.info("Cannot read data from .sql file.");
            } catch (SQLException exception) {
                logger.info("SQL error. " + exception);
            }
        } else {
            logger.info("Cannot make connection with database.");
        }
        return false;
    }

    public static boolean executeQueryPersonClear(long userId) {
        logger.info("Attempt to execute <execute_query_person_clear>");
        SQLQuery currentQuery = SQLQuery.PERSON_CLEAR;
        if (connection != null) {
            try {
                String content = Files.readString(currentQuery.getPath(), Charset.defaultCharset());
                PreparedStatement preparedStatement = connection.prepareStatement(content);
                preparedStatement.setLong(1, userId);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    logger.info("Operation was executed successfully");
                    return true;
                } else {
                    logger.info("Cannot execute operation.");
                }
            } catch (IOException exception) {
                logger.info("Cannot read data from .sql file.");
            } catch (SQLException exception) {
                logger.info("SQL error. " + exception);
            }
        } else {
            logger.info("Cannot make connection with database.");
        }
        return false;
    }

    public static boolean executeQueryPersonRemoveById(long personId) {
        logger.info("Attempt to execute <execute_query_person_remove_by_id>");
        SQLQuery currentQuery = SQLQuery.PERSON_REMOVE_BY_ID;
        if (connection != null) {
            try {
                String content = Files.readString(currentQuery.getPath(), Charset.defaultCharset());
                PreparedStatement preparedStatement = connection.prepareStatement(content);
                preparedStatement.setLong(1, personId);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    logger.info("Operation was executed successfully");
                    return true;
                } else {
                    logger.info("Cannot execute operation.");
                }
            } catch (IOException exception) {
                logger.info("Cannot read data from .sql file.");
            } catch (SQLException exception) {
                logger.info("SQL error. " + exception);
            }
        } else {
            logger.info("Cannot make connection with database.");
        }
        return false;
    }












    public static Optional<Long> executeQueryUserCreate(String login, String password) {
        logger.info("Attempt to execute <execute_query_user_create>");
        SQLQuery currentQuery = SQLQuery.USER_CREATE;
        if (connection != null) {
            try {
                String content = Files.readString(currentQuery.getPath(), Charset.defaultCharset());
                PreparedStatement preparedStatement = connection.prepareStatement(content);
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    logger.info("Operation was executed successfully");
                    return Optional.of(resultSet.getLong("id"));
                } else {
                    logger.info("Cannot execute operation.");
                    return Optional.empty();
                }
            } catch (IOException exception) {
                logger.info("Cannot read data from .sql file.");
            } catch (SQLException exception) {
                logger.info("SQL error. " + exception);
            }
        } else {
            logger.info("Cannot make connection with database.");
        }
        return Optional.empty();
    }

    public static Optional<Long> executeQueryUserCheck(String login, String password) {
        logger.info("Attempt to execute <execute_query_user_check>");
        SQLQuery currentQuery = SQLQuery.USER_CHECK;
        if (connection != null) {
            try {
                String content = Files.readString(currentQuery.getPath(), Charset.defaultCharset());
                PreparedStatement preparedStatement = connection.prepareStatement(content);
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    logger.info("Operation was executed successfully");
                    return Optional.of(resultSet.getLong("id"));
                } else {
                    logger.info("Cannot execute operation.");
                    return Optional.empty();
                }
            } catch (IOException exception) {
                logger.info("Cannot read data from .sql file.");
            } catch (SQLException exception) {
                logger.info("SQL error. " + exception);
            }
        } else {
            logger.info("Cannot make connection with database.");
        }
        return Optional.empty();
    }

}
