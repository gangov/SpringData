/*
Print All Minion Names
Write a program that prints all minion names from the minions table in order first record, last record, first + 1,
last – 1, first + 2, last – 2… first + n, last – n.

1	3	5	7	9	10	8	6	4	2

 */

package assignments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AssignmentSevenPrintAllMinionNames implements Runnable {
    private Connection connection;

    public AssignmentSevenPrintAllMinionNames(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {

        try {
            this.printNumbersInOrder();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void printNumbersInOrder() throws SQLException {
        String query = String.format("SELECT name FROM minions");
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet result = stmt.executeQuery();

        ArrayList<String> allNames = new ArrayList<>();

        while (result.next()) {
            allNames.add(result.getString("name"));
        }

        int first = 0;
        int last = allNames.size() - 1;

        for (int i = 0; i < allNames.size() / 2; i++) {

            System.out.println(allNames.get(first));
            System.out.println(allNames.get(last));
            first++;
            last--;
        }
    }
}
