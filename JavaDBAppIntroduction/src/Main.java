/*
Part 1: Accessing Database via Simple Java Application – Demo
You are given a simple java program that opens a connection to a local database server and retrieves the following data
– first name and last name from the “soft_uni” database. The data is filtered by salary criteria, which is given by the
user at the input. The exercise shows briefly the usage of the java.sql package and the MySQL Connector/J driver.
Example

    Input	    Output
    70000.00	"Ken Sanchez  James Hamilton Brian Welcker"
    80000.00	"Ken Sanchez  James Hamilton "
 */

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter username for db:");
        String username = scanner.nextLine();
        System.out.println("Please enter password for db:");
        String password = scanner.nextLine();

        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/soft_uni", properties);

        PreparedStatement statement = connection.prepareStatement("SELECT first_name, last_name FROM " +
                "employees WHERE salary > ?");

        System.out.println("Please enter salary by which we will filter:");
        double salaryQuota = Double.parseDouble(scanner.nextLine());

        statement.setDouble(1, salaryQuota);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %s%n", resultSet.getString("first_name"),
                    resultSet.getString("last_name"));
        }

        resultSet.close();
    }
}


