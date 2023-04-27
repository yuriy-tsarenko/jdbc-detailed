package com.goit.examples.transaction;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        Properties properties = loadProperties();
        Datasource datasource = Datasource.of(properties);

        try {
            PreparedStatement preparedStatement = datasource.preparedStatement("INSERT INTO contact VALUES (?,?,'Carine ','54, rue Royale',NULL,'Nantes',NULL,'44000','France','40.32.2555')");
            int firstNameColumnIndex = 2;
            String firstName = "NEW_CONTACT5";
            preparedStatement.setString(firstNameColumnIndex, firstName);
            final int id = 22112;
            final int idColumnIndex = 1;
            preparedStatement.setInt(idColumnIndex, id);

            preparedStatement.executeUpdate();
//            preparedStatement.setString(2, "NEW_CONTACT4");
//            preparedStatement.executeUpdate();
            datasource.commit();
            datasource.close();
        } catch (Exception e) {
            datasource.rollback();
            e.printStackTrace();
        }
    }

    private static Properties loadProperties() throws IOException {
        InputStream stream = Main.class.getClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        properties.load(stream);
        return properties;
    }
}
