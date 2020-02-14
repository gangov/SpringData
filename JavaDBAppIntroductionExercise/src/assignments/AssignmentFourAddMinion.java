/*
Add Minion
Write a program that reads information about a minion and its villain and adds it to the database. In case the town of
the minion is not in the database, insert it as well. In case the villain is not present in the database, add him too
with default evilness factor of “evil”. Finally, set the new minion to be servant of the villain. Print appropriate
messages after each operation – see the examples.

Input	                            Output
Minion: Robert 14 Berlin            Successfully added Robert to be minion of Gru.
Villain: Gru

Minion: Cathleen 20 Liverpool       Town Liverpool was added to the database.
Villain: Gru"	                    Successfully added Cathleen to be minion of Gru.

Minion: Mars 23 Sofia               Villain Poppy was added to the database.
Villain: Poppy                      Successfully added Mars to be minion of Poppy

Minion: Carry 20 Eindhoven          Town Eindhoven was added to the database.
Villain: Jimmy                      Villain Jimmy was added to the database.
                                    Successfully added Carry to be minion of Jimmy

 */

package assignments;

import java.sql.*;
import java.util.Scanner;

public class AssignmentFourAddMinion implements Runnable {
    Scanner scanner = new Scanner(System.in);
    private final Connection connection;

    public AssignmentFourAddMinion(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        System.out.printf("Enter minion and villain information in the following format:%n" +
                "Minion: {name} {age} {town}%n" +
                "Villain: {name}%n");
        try {
            String[] minionInfo = scanner.nextLine().split("\\s+");
            String villainName = scanner.nextLine().split("\\s+")[1];

            String nameMinion = minionInfo[1];
            int ageMinion = Integer.parseInt(minionInfo[2]);
            String town = minionInfo[3];

            try {
                if (!this.checkEntryInDB(town, "towns")) {
                    this.addTown(town);
                }

                if (!this.checkEntryInDB(villainName, "villains")) {
                    this.addVillain(villainName);
                }

                int town_id = this.findID(town, "towns");

                if (!this.checkEntryInDB(nameMinion, "minions")) {
                    this.addMinionIntoMinions(nameMinion, ageMinion, town_id);
                    int villain_id = this.findID(villainName, "villains");
                    int minion_id = this.findID(nameMinion, "minions");

                    this.addMinionToVillain(minion_id, villain_id);
                    System.out.println(String.format("Successfully added %s to be minion of %s", nameMinion, villainName));
                } else {
                    System.out.println("Minion already exist. It won't be added to our db. Sry :/");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Please, enter a valid input information.");
        }
    }

    private void addMinionToVillain(int minion_id, int villain_id) throws SQLException {
        String query = String.format("INSERT INTO minions_villains(minion_id, villain_id) VALUE (%d, %d);",
                minion_id, villain_id);

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.executeUpdate();
    }

    private int findID(String searchCriteria, String tableToLook) throws SQLException {
        String query = String.format("SELECT id FROM %s WHERE name LIKE '%s'", tableToLook, searchCriteria);

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);

        ResultSet result = preparedStatement.executeQuery();

        result.next();

        return result.getInt("id");
    }

    private void addMinionIntoMinions(String nameMinion, int ageMinion, int town_id) throws SQLException {
        String query = String.format("INSERT INTO minions(name, age, town_id) VALUE ('%s', %d, %d);",
                nameMinion,
                ageMinion,
                town_id);

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.executeUpdate();
    }

    private void addVillain(String villainName) throws SQLException {
        PreparedStatement preparedStatement =
                this.connection.prepareStatement("INSERT INTO villains(name, evilness_factor) VALUE (?, 'evil');");
        preparedStatement.setString(1, villainName);
        preparedStatement.executeUpdate();
        System.out.println(String.format("Villain %s was added to the database.", villainName));
    }

    private void addTown(String town) throws SQLException {
        String query = String.format("INSERT INTO towns(name, country) VALUE('%s', NULL);", town);
        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.executeUpdate();
        System.out.println(String.format("Town %s was added to the database.", town));
    }

    private boolean checkEntryInDB(String searchCriteria, String tableToLook) throws SQLException {
        String query = String.format("SELECT * FROM %s WHERE name LIKE ?;", tableToLook);

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.setString(1, searchCriteria);

        ResultSet result = preparedStatement.executeQuery();

        return result.first();
    }
}
