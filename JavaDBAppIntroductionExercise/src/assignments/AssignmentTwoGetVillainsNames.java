/*
Get Villains’ Names
Write a program that prints on the console all villains’ names and their number of minions. Get only the villains who
have more than 15 minions. Order them by number of minions in descending order.
 */

package assignments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssignmentTwoGetVillainsNames implements Runnable {

    private final Connection connection;

    public AssignmentTwoGetVillainsNames(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        String query = "SELECT villains.name, COUNT(minion_id) AS 'army'\n" +
                "FROM villains\n" +
                "         JOIN minions_villains mv ON villains.id = mv.villain_id\n" +
                "GROUP BY villains.name\n" +
                "HAVING army > 15\n" +
                "ORDER BY army DESC;";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                System.out.printf("%s %d%n",
                        result.getString("name"),
                        result.getInt("army"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
