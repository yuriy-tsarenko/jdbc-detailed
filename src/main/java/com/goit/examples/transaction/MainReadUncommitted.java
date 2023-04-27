package com.goit.examples.transaction;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class MainReadUncommitted {
    public static void main(String[] args) throws IOException, SQLException {
        Properties properties = loadProperties();
        Datasource datasource = Datasource.of(properties);

        try {
            PreparedStatement preparedStatement = datasource.preparedStatement("SELECT * FROM contact WHERE id=?");
            final int id = 22112;
            final int idColumnIndex = 1;
            preparedStatement.setInt(idColumnIndex, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            datasource.commit();
            resultSet.next();
            datasource.close();
        } catch (Exception e) {
            datasource.rollback();
            e.printStackTrace();
        }
    }

    private static Properties loadProperties() throws IOException {
        InputStream stream = MainReadUncommitted.class.getClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        properties.load(stream);
        return properties;
    }
}
