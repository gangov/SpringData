/*
Now it’s time for you to write a similar program. Follow the steps to write a java application that retrieves
information about the users, their games and duration. We are going to use the “diablo” database from the previous
course.

Example:
    Input	    Output
    nakov	    User: nakov
                Svetlin Nakov has played 6 games
    destroyer	No such user exists
 */

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class WritingOwnDataQuery {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        boolean hasResult = false;

        // Getting connection info for the db
        System.out.println("Please enter username for db:");
        String username = scanner.nextLine();
        System.out.println("Please enter password for db:");
        String password = scanner.nextLine();

        // Saving the username and pass into properties
        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);


        // Establishing connection with the given properties
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/diablo", properties);

        // Building our prepared statement
        PreparedStatement statement = connection
                .prepareStatement("SELECT user_name FROM users WHERE user_name LIKE ?;");

        System.out.println("Enter username to search");
        String nameQuota = scanner.nextLine();
        statement.setString(1, nameQuota);


        // Executing the statement
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            hasResult = true;
            System.out.printf("User: %s%n", resultSet.getString("user_name"));

            PreparedStatement getId = connection.prepareStatement("SELECT id FROM users WHERE user_name LIKE ?;");
            getId.setString(1, nameQuota);
            ResultSet finalId = getId.executeQuery();

            while (finalId.next()) {
                int idOfUser = finalId.getInt("id");
                PreparedStatement countOfGames = connection
                        .prepareStatement("SELECT CONCAT(first_name, ' ', last_name) AS 'fullname', COUNT(ug.user_id) AS 'games' FROM users JOIN users_games ug on users.id = ug.user_id WHERE user_id = ? GROUP BY user_id;");
                countOfGames.setInt(1, idOfUser);

                ResultSet gamesTotal = countOfGames.executeQuery();

                while (gamesTotal.next()) {
                    System.out.printf("%s has played %d games",
                            gamesTotal.getString("fullname"),
                            gamesTotal.getInt("games"));
                }
            }
        }

        if (!hasResult) {
            System.out.println("No such user exists");
        }
    }
}
