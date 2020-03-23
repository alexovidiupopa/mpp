package Repository;


import Model.BaseEntity;
import Model.Validators.Validator;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseRepository<ID extends Serializable, T extends BaseEntity<ID>> implements SortingRepository<ID, T> {

    Validator<Student> validator;

    private String dbType;
    private String dbHost;
    private String dbPort;
    private String dbName;
    private String dbUser;
    private String dbPassword;
    protected Validator<T> validator;
    public DatabaseRepository(Validator<T> validator, String dbCredentialsFilename) {
        this.validator = validator;
        loadDBConfiguration(dbCredentialsFilename);
    }

    /**
     * Method to load the db configuration details.
     * @param dbCredentialsFilename - file containing the credentials.
     */
    private void loadDBConfiguration(String dbCredentialsFilename) {
        Path path = Paths.get(dbCredentialsFilename);
        List<String> inputData = new ArrayList<>();
        try {
            Files.lines(path).forEach(inputData::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbType = inputData.get(0);
        dbHost = inputData.get(1);
        dbPort = inputData.get(2);
        dbName = inputData.get(3);
        dbUser = inputData.get(4);
        dbPassword = inputData.get(5);
    }

    /**
     * Method that handles loading the driver from the gradle build file.
     */
    private void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Can’t load driver");
        }
    }

    /**
     * Method that handles creating the database connection having previously loaded the credentials.
     * @return a Connection object representing the established connection.
     */
    protected Connection dbConnection() {

        loadDriver();
        DriverManager.setLoginTimeout(60);
        try {
            String url =
                    new StringBuilder()
                            .append("jdbc:")
                            .append(this.dbType)
                            .append("://")
                            .append(this.dbHost)
                            .append(":")
                            .append(this.dbPort)
                            .append("/")
                            .append(dbName)
                            .append("?user=")
                            .append(this.dbUser)
                            .append("&password=")
                            .append(this.dbPassword)
                            .toString();
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.err.println("Cannot connect to the database: " + e.getMessage());
        }
        return null;
    }
}