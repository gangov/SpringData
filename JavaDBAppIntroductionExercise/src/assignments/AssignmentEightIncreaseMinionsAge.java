/*
Increase Minions Age
Read from the console minion IDs, separated by space. Increment the age of those minions by 1 and make their names title
to lower case. Finally, print the names and the ages of all minions that are in the database. See the examples below.
Example

        minions
    Id	name	age
    1	May	    44
    2	Brina	43
    3	Roslyn	50
    4	Virgie	53
    5	Nananne	23
    ...	...	    ...

    Input	Output
    2 1 4	may 45
            brina 44
            Roslyn 50
            virgie 54
            Nananne 23
            ...
 */

package assignments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class AssignmentEightIncreaseMinionsAge implements Runnable {

    private final Connection connection;

    public AssignmentEightIncreaseMinionsAge(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter minions id's in the following format: {id1} {id2} {id3} ...");
        int[] entries = Arrays
                .stream(scanner.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        try {
            for (int id : entries) {

                this.increaseAge(id);
                this.lowercaseName(id);

            }

            this.showAllNamesWithAges();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    private void showAllNamesWithAges() throws SQLException {
        String query = "SELECT name, age FROM minions;";
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet result = stmt.executeQuery();

        while (result.next()) {
            System.out.println(String.format("%s %d",
                    result.getString("name"),
                    result.getInt("age")));
        }
    }

    private void lowercaseName(int id) throws SQLException {
        String query = String.format("UPDATE minions SET name = LOWER(name) WHERE id = %d", id);
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.executeUpdate();
    }

    private void increaseAge(int id) throws SQLException {
        String query = String.format("UPDATE minions SET age = age + 1 WHERE id = %d;", id);
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.executeUpdate();
    }
}
