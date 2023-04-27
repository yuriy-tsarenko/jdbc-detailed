package com.goit.examples.connection.datasource;

import com.goit.examples.connection.datasource.mysql.Datasource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class MainBatch {
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";
    private static Datasource datasource;
    private static PreparedStatement preparedStatement;

    //128
    public static void main(String[] args) throws SQLException {
        String userChoice = "Y";
        datasource = Datasource.of(MYSQL_URL, USERNAME, PASSWORD);
        preparedStatement = datasource
                .preparedStatement("UPDATE employee_db.contact SET contact_first_name=?, contact_last_name=? WHERE id=?");
        while (Objects.equals(userChoice.toUpperCase(), "Y")) {
            try {
                Scanner reader = new Scanner(System.in);
                System.out.println("enter first name:");
                String firstName = reader.nextLine();
                System.out.println("enter last name:");
                String lastName = reader.nextLine();
                System.out.println("enter id:");
                int id = Integer.parseInt(reader.nextLine());
                batchUpdate(firstName, lastName, id);
                System.out.println("enter 'Y' to continue or any key to exit");
                userChoice = reader.nextLine();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        preparedStatement.executeBatch();
        preparedStatement.getConnection().close();
    }

    private static void batchUpdate(String firstName, String lastName, int id) throws SQLException {
        final int firstNameIndex = 1;
        preparedStatement.setObject(firstNameIndex, firstName);
        final int lastNameIndex = 2;
        preparedStatement.setObject(lastNameIndex, lastName);
        final int idIndex = 3;
        preparedStatement.setObject(idIndex, id);
        preparedStatement.addBatch();
    }
}

