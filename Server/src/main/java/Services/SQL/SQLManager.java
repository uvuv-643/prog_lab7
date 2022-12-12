package Services.SQL;

import Entities.Color;
import Entities.Coordinates;
import Entities.Country;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.*;
import java.util.Optional;

import Entities.Location;
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

    public static Optional<Long> executeQueryPersonCreate(Long coordinatesId, int height, float weight, Color color, Country country, Long locationId, Long userId) {
        logger.info("Attempt to execute <execute_query_person_create>");
        SQLQuery currentQuery = SQLQuery.PERSON_CREATE;
        if (connection != null) {
            try {
                String content = Files.readString(currentQuery.getPath(), Charset.defaultCharset());
                if (content.chars().filter(x -> x == '?').count() == 7) {
                    PreparedStatement preparedStatement = connection.prepareStatement(content);
                    preparedStatement.setLong(1, coordinatesId);
                    preparedStatement.setInt(2, height);
                    preparedStatement.setFloat(3, weight);
                    if (color != null) {
                        preparedStatement.setString(4, color.name());
                    } else {
                        preparedStatement.setNull(4, Types.VARCHAR);
                    }
                    if (country != null) {
                        preparedStatement.setString(5, country.name());
                    } else {
                        preparedStatement.setNull(5, Types.VARCHAR);
                    }
                    if (locationId != null) {
                        preparedStatement.setLong(6, locationId);
                    } else {
                        preparedStatement.setNull(6, Types.INTEGER);
                    }
                    preparedStatement.setLong(7, userId);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        logger.info("Operation was executed successfully");
                        return Optional.of(resultSet.getLong("id"));
                    } else {
                        logger.info("Cannot execute operation.");
                        return Optional.empty();
                    }
                }
                logger.info("Number of parameters mismatch");
                return Optional.empty();
            } catch (IOException exception) {
                logger.info("Cannot read data from .sql file.");
                return Optional.empty();
            } catch (SQLException exception) {
                logger.info("SQL error. " + exception);
                return Optional.empty();
            }
        }
        logger.info("Cannot make connection with database.");
        return Optional.empty();
    }

    public static Optional<Long> executeQueryCoordinateCreate(Coordinates coordinates) {
        logger.info("Attempt to execute <execute_query_coordinate_create>");
        SQLQuery currentQuery = SQLQuery.COORDINATE_CREATE;
        if (connection != null) {
            try {
                String content = Files.readString(currentQuery.getPath(), Charset.defaultCharset());
                if (content.chars().filter(x -> x == '?').count() == 2) {
                    PreparedStatement preparedStatement = connection.prepareStatement(content);
                    preparedStatement.setFloat(1, coordinates.getX());
                    preparedStatement.setFloat(2, coordinates.getY());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        logger.info("Operation was executed successfully");
                        return Optional.of(resultSet.getLong("id"));
                    } else {
                        logger.info("Cannot execute operation.");
                        return Optional.empty();
                    }
                }
                logger.info("Number of parameters mismatch");
                return Optional.empty();
            } catch (IOException exception) {
                logger.info("Cannot read data from .sql file.");
                return Optional.empty();
            } catch (SQLException exception) {
                logger.info("SQL error. " + exception);
                return Optional.empty();
            }
        }
        logger.info("Cannot make connection with database.");
        return Optional.empty();
    }

    public static Optional<Long> executeQueryLocationCreate(Location location) {
        if (location == null) return Optional.empty();
        logger.info("Attempt to execute <execute_query_location_create>");
        SQLQuery currentQuery = SQLQuery.LOCATION_CREATE;
        if (connection != null) {
            try {
                String content = Files.readString(currentQuery.getPath(), Charset.defaultCharset());
                if (content.chars().filter(x -> x == '?').count() == 3) {
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
                        return Optional.empty();
                    }
                }
                logger.info("Number of parameters mismatch");
                return Optional.empty();
            } catch (IOException exception) {
                logger.info("Cannot read data from .sql file.");
                return Optional.empty();
            } catch (SQLException exception) {
                logger.info("SQL error. " + exception);
                return Optional.empty();
            }
        }
        logger.info("Cannot make connection with database.");
        return Optional.empty();
    }

}
