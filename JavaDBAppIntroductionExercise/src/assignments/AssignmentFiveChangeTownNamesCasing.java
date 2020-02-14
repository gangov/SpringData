/*
Change Town Names Casing
Write a program that changes all town names to uppercase for a given country. Print the number of towns that were
changed in the format provided in examples. On the next line print the names that were changed, separated by coma and a
space.
Example

    Input	        Output
    Bulgaria	    3 town names were affected.
                    [SOFIA, PLOVDIV, BURGAS]
    Italy	        No town names were affected.
 */

package assignments;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AssignmentFiveChangeTownNamesCasing implements Runnable {

    private final Connection connection;

    public AssignmentFiveChangeTownNamesCasing(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name of a country where we should uppercase the names: ");
        String country = scanner.nextLine();

        try {
            int result = this.findTownsByCountry(country);
            if (result > 0) {
                System.out.println(String.format("%d town names were affected.", result));
                this.updateTownNames(country);
            } else {
                System.out.println("No town names were affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTownNames(String country) throws SQLException {
        String query = String.format("UPDATE towns SET name = UPPER(name) WHERE country LIKE '%s';", country);

        PreparedStatement convertToUpperCase = connection.prepareStatement(query);
        convertToUpperCase.executeUpdate();

        PreparedStatement showUpdatedNames =
                connection.prepareStatement(String.format("SELECT * FROM towns WHERE country LIKE '%s';", country));

        ResultSet result = showUpdatedNames.executeQuery();

        ArrayList<String> townNames = new ArrayList<>();
        while (result.next()) {
            townNames.add(result.getString("name"));
        }

        System.out.println(townNames);
    }

    private int findTownsByCountry(String country) throws SQLException {
        String query = String.format("SELECT COUNT(name) FROM towns WHERE country LIKE '%s';", country);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        return resultSet.getInt("COUNT(name)");
    }
}
