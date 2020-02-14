/*
Get Minion Names
Write a program that prints on the console all minion names and their age for given villain id. For the output, use the
formats given in the examples.
 */

package assignments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AssignmentThreeGetMinionNames implements Runnable {
    Scanner scanner = new Scanner(System.in);
    private final Connection connection;

    public AssignmentThreeGetMinionNames(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        System.out.println("Select villain id: ");
        int villainToLook = Integer.parseInt(scanner.nextLine());


        try {
            this.findVillain(villainToLook);
            this.printMinions(villainToLook);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void printMinions(int villainToLook) throws SQLException {
        String query = "SELECT name, age\n" +
                "FROM minions\n" +
                "         JOIN minions_villains mv ON minions.id = mv.minion_id\n" +
                "WHERE villain_id = ?;\n";

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.setInt(1, villainToLook);

        ResultSet result = preparedStatement.executeQuery();

        int counter = 1;
        while (result.next()) {
            System.out.printf("%d. %s %d%n", counter++,
                    result.getString("name"),
                    result.getInt("age"));
        }
    }

    private void findVillain(int villainToLook) throws SQLException {
        String query = "SELECT name FROM villains WHERE id = ?;";

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.setInt(1, villainToLook);

        ResultSet result = preparedStatement.executeQuery();

        if (result.first()) {
            System.out.printf("Villain: %s%n", result.getString("name"));
        } else {
            System.out.printf("No villain with ID %d exists in the database.", villainToLook);
            System.exit(1);
        }
    }
}
