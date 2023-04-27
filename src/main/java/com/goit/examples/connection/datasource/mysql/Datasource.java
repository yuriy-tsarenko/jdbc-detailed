package com.goit.examples.connection.datasource.mysql;

import com.goit.examples.connection.datasource.exception.DatasourceException;
import com.mysql.cj.jdbc.Driver;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@RequiredArgsConstructor(staticName = "of")
public class Datasource {

    private final String mysqlUrl;
    private final String username;
    private final String password;
    private PreparedStatement preparedStatement;

    public PreparedStatement preparedStatement(@Language("SQL") String query) {
        try {
            preparedStatement = openConnection().prepareStatement(query);
            return preparedStatement;
        } catch (Exception e) {
            throw new DatasourceException("Can't create prepared statement", e);
        }
    }

    private Connection openConnection() {
        try {
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(mysqlUrl, username, password);
        } catch (Exception e) {
            throw new DatasourceException("Open connection failed", e);
        }
    }
}
