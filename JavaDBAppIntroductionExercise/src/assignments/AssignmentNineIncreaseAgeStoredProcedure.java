/*
Increase Age Stored Procedure
Create a stored procedure usp_get_older (directly in the database using MySQL Workbench or any other similar tool) that
receives a minion_id and increases the minion’s years by 1. Write a program that uses that stored procedure to increase
the age of a minion, whose id will be given as an input from the console. After that print the name and the age of that
minion.
 */

package assignments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AssignmentNineIncreaseAgeStoredProcedure implements Runnable {

    private final Connection connection;

    public AssignmentNineIncreaseAgeStoredProcedure(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the id of a minion, whos age will be increased by 1 year: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            this.increaseAge(id);
            this.printMinion(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void printMinion(int id) throws SQLException {
        String query = String.format("SELECT name, age FROM minions WHERE id = %d", id);
        PreparedStatement stmt = connection.prepareStatement(query);

        ResultSet result = stmt.executeQuery();

        result.next();

        System.out.println(String.format("%s %d",
                result.getString("name"),
                result.getInt("age")));
    }

    private void increaseAge(int id) throws SQLException {
        String safety = "DROP PROCEDURE IF EXISTS usp_get_older;";
        PreparedStatement safetyStatement = connection.prepareStatement(safety);
        safetyStatement.executeUpdate();

        String query = String.format("CREATE PROCEDURE usp_get_older(IN minion_id INT) BEGIN UPDATE minions SET age = age + 1 WHERE id = %d; END;", id);
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.executeUpdate();

        String call = String.format("CALL usp_get_older(%d);", id);
        PreparedStatement callStmt = connection.prepareStatement(call);
        callStmt.executeUpdate();
    }
}
