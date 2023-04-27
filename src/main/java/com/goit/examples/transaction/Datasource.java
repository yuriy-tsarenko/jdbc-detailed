package com.goit.examples.transaction;

import com.goit.examples.connection.datasource.exception.DatasourceException;
import com.mysql.cj.jdbc.NonRegisteringDriver;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

import static com.goit.examples.transaction.Constants.CONNECTION_AUTOCOMMIT;
import static com.goit.examples.transaction.Constants.CONNECTION_URL;
import static com.goit.examples.transaction.Constants.TRANSACTION_ISOLATION;

@RequiredArgsConstructor(staticName = "of")
public class Datasource {

    private final Properties properties;

    private PreparedStatement preparedStatement;
    private Connection connection;

    public PreparedStatement preparedStatement(@Language("SQL") String query) {
        try {
            connection = openConnection();
            connection.createStatement().execute(getIsolationLevelQuery(properties.getProperty(TRANSACTION_ISOLATION)));
            boolean autocommit = Boolean.parseBoolean(properties.getProperty(CONNECTION_AUTOCOMMIT));
            connection.setAutoCommit(autocommit);
            preparedStatement = connection.prepareStatement(query);
            return preparedStatement;
        } catch (Exception e) {
            throw new DatasourceException("Can't create prepared statement", e);
        }
    }

    private String getIsolationLevelQuery(String name) {
        switch (name) {
            case "READ_UNCOMMITTED":
                return "SET GLOBAL TRANSACTION ISOLATION LEVEL READ UNCOMMITTED";
            case "REPEATABLE_READ":
                return "SET GLOBAL TRANSACTION ISOLATION LEVEL REPEATABLE READ";
            case "READ_COMMITTED":
                return "SET GLOBAL TRANSACTION ISOLATION LEVEL READ COMMITTED";
            case "SERIALIZABLE":
                return "SET GLOBAL TRANSACTION ISOLATION LEVEL SERIALIZABLE";
            default:
                throw new DatasourceException("Unsupported isolation level:" + name);
        }
    }

    public void commit() {
        try {
            if (!connection.isClosed()) {
                connection.commit();
            }
        } catch (Exception e) {
            throw new DatasourceException("Transaction commit failed", e);
        }
    }

    public void close() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            throw new DatasourceException("Connection close failed", e);
        }
    }

    public void rollback() {
        try {
            if (!connection.isClosed()) {
                connection.rollback();
                connection.close();
            }
        } catch (Exception e) {
            throw new DatasourceException("Transaction commit failed", e);
        }
    }

    private Connection openConnection() {
        try {
            NonRegisteringDriver driver = new NonRegisteringDriver();
            String url = properties.getProperty(CONNECTION_URL);
//            String username = properties.getProperty(CONNECTION_USERNAME);
//            String password = properties.getProperty(CONNECTION_PASSWORD);
            DriverManager.registerDriver(driver);
            return DriverManager.getConnection(url, properties);
        } catch (Exception e) {
            throw new DatasourceException("Open connection failed", e);
        }
    }
}
