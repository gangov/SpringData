/*
Remove Villain
Write a program that receives an ID of a villain, deletes him from the database and releases his minions from serving
him. As an output print the name of the villain and the number of minions released. Make sure all operations go as
planned, otherwise do not make any changes to the database. For the output use the format given in the examples.
Example

    Input	Output
    1	    Carl was deleted
            16 minions released

    3	    Arabele was deleted
            14 minions released

    101	    No such villain was found
 */

package assignments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AssignmentSixRemoveVillain implements Runnable {

    private final Connection connection;

    public AssignmentSixRemoveVillain(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ID of a villain, that you want to delete: ");
        int villainID = Integer.parseInt(scanner.nextLine());

        try {
            this.alterTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (this.findVillainByID(villainID)) {
                this.printVillainName(villainID);
                this.printMinionsCount(villainID);
                this.deleteMinionsByVillainId(villainID);
                this.deleteVillainById(villainID);
            } else {
                System.out.println("No such villain was found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void alterTable() throws SQLException {
        String firstQuery = "ALTER TABLE minions_villains DROP FOREIGN KEY fk_minions_villains;";
        PreparedStatement first = connection.prepareStatement(firstQuery);

        String secondQuery = "ALTER TABLE minions_villains ADD FOREIGN KEY (minion_id) REFERENCES minions (id) ON DELETE CASCADE;";
        PreparedStatement second = connection.prepareStatement(secondQuery);

        first.executeUpdate();
        second.executeUpdate();
    }

    private void printMinionsCount(int villainID) throws SQLException {
        String findCount = String.format("SELECT COUNT(*) FROM minions_villains WHERE villain_id = %d;", villainID);
        PreparedStatement getCount = connection.prepareStatement(findCount);
        ResultSet count = getCount.executeQuery();
        count.next();
        System.out.println(String.format("%d minions released", count.getInt("COUNT(*)")));
    }

    private void printVillainName(int villainID) throws SQLException {
        String findName = String.format("SELECT name FROM villains WHERE id = %d", villainID);
        PreparedStatement getName = connection.prepareStatement(findName);
        ResultSet villainName = getName.executeQuery();
        villainName.next();
        System.out.println(String.format("%s was deleted", villainName.getString("name")));
    }

    private void deleteMinionsByVillainId(int villainID) throws SQLException {
        String deleteQuery =
                String.format("DELETE minions, mv FROM minions JOIN minions_villains mv on minions.id = mv.minion_id WHERE mv.villain_id = %d", villainID);
        PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery);
        deleteStmt.executeUpdate();
    }

    private void deleteVillainById(int villainID) throws SQLException {
        String firstDeleteQuery = String.format("DELETE FROM minions_villains WHERE villain_id = %d;", villainID);
        PreparedStatement firstDeleteStmt = connection.prepareStatement(firstDeleteQuery);
        firstDeleteStmt.executeUpdate();

        String secondDeleteQuer = String.format("DELETE villains FROM villains WHERE id = %d;", villainID);
        PreparedStatement secondDeleteStmt = connection.prepareStatement(secondDeleteQuer);
        secondDeleteStmt.executeUpdate();
    }

    private boolean findVillainByID(int villainID) throws SQLException {
        String query = String.format("SELECT * FROM villains WHERE id = %d;", villainID);

        PreparedStatement checkForVillain = connection.prepareStatement(query);
        ResultSet resultSet = checkForVillain.executeQuery();

        return resultSet.next();
    }
}
