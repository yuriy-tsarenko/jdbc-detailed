package com.goit.examples.connection.datasource;

import com.goit.examples.connection.datasource.mysql.Datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";
    private static Datasource datasource;

    //128
    public static void main(String[] args) {
        String userChoice = "Y";
        while (Objects.equals(userChoice.toUpperCase(), "Y")) {
            try {
                Scanner reader = new Scanner(System.in);
                datasource = Datasource.of(MYSQL_URL, USERNAME, PASSWORD);
                System.out.println("enter first name:");
                String firstName = reader.nextLine();
                System.out.println("enter last name:");
                String lastName = reader.nextLine();
                System.out.println("enter id:");
                int id = Integer.parseInt(reader.nextLine());
                updateContact(firstName, lastName, id);
                System.out.println("enter 'Y' to continue or any key to exit");
                userChoice = reader.nextLine();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    private static void updateContact(String firstName, String lastName, int id) throws SQLException {
        try (PreparedStatement preparedStatement = datasource
                .preparedStatement("UPDATE employee_db.contact SET contact_first_name=?, contact_last_name=? WHERE id=?");
             Connection connection = preparedStatement.getConnection()) {
            final int firstNameIndex = 1;
            preparedStatement.setObject(firstNameIndex, firstName);
            final int lastNameIndex = 2;
            preparedStatement.setString(lastNameIndex, lastName);
            final int idIndex = 3;
            preparedStatement.setInt(idIndex, id);
            preparedStatement.executeUpdate();
        }
    }
}
