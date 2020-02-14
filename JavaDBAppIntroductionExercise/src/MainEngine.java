import assignments.*;

import java.io.Console;
import java.sql.Connection;
import java.util.Scanner;

public class MainEngine implements Runnable {

    Scanner scanner = new Scanner(System.in);
    private Connection connection;

    public MainEngine(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void run() {
        String welcomeInfo = "Select assignment to execute as follows:\n" +
                "Type 2 for Assignment #2 \"Get Villain’s Names\"\n" +
                "Type 3 for Assignment #3 \"Get Minion Names\"\n" +
                "Type 4 for Assignment #4 \"Add Minion\"\n" +
                "Type 5 for Assignment #5 \"Change Town Names Casing\"\n" +
                "Type 6 for Assignment #6 \"Remove Villain\"\n" +
                "Type 7 for Assignment #7 \"Print All Minion Names\"\n" +
                "Type 8 for Assignment #8 \"Increase Minions Age\"\n" +
                "Type 9 for Assignment #9 \"Increase Age Stored Procedure\"\n" +
                "Type q to exit.";
        System.out.println(welcomeInfo);

        String input = scanner.nextLine();

        while (!input.equals("q")) {
            switch (input) {
                case "2":
                    System.out.println("Executing assignment #2 \"Get Villain's Names\"");
                    AssignmentTwoGetVillainsNames twoGetVillainsNames =
                            new AssignmentTwoGetVillainsNames(this.connection);
                    twoGetVillainsNames.run();
                    break;
                case "3":
                    System.out.println("Executing assignment #3 \"Get Minion Names\"");
                    AssignmentThreeGetMinionNames threeGetMinionNames =
                            new AssignmentThreeGetMinionNames(this.connection);
                    threeGetMinionNames.run();
                    break;
                case "4":
                    System.out.println("Executing assignment #4 \"Add Minion\"");
                    AssignmentFourAddMinion fourAddMinion =
                            new AssignmentFourAddMinion(this.connection);
                    fourAddMinion.run();
                    break;
                case "5":
                    System.out.println("Executing assignment #5 \"Change Town Names Casing\"");
                    AssignmentFiveChangeTownNamesCasing fiveChangeTownNamesCasing =
                            new AssignmentFiveChangeTownNamesCasing(this.connection);
                    fiveChangeTownNamesCasing.run();
                    break;
                case "6":
                    System.out.println("Executing assignment #6 \"Remova Villain\"");
                    AssignmentSixRemoveVillain sixRemoveVillain =
                            new AssignmentSixRemoveVillain((this.connection));
                    sixRemoveVillain.run();
                    break;
                case "7":
                    System.out.println("Executing assignment #7 \"Print All Minion Names\"");
                    AssignmentSevenPrintAllMinionNames sevenPrintAllMinionNames =
                            new AssignmentSevenPrintAllMinionNames(this.connection);
                    sevenPrintAllMinionNames.run();
                    break;
                case "8":
                    System.out.println("Executing assignment #8 \"Increase Minions Age");
                    AssignmentEightIncreaseMinionsAge eightIncreaseMinionsAge =
                            new AssignmentEightIncreaseMinionsAge(this.connection);
                    eightIncreaseMinionsAge.run();
                    break;
                case "9":
                    System.out.println("Executing assignment #9 \"Increase Age Stored Procedure\"");
                    AssignmentNineIncreaseAgeStoredProcedure nineIncreaseAgeStoredProcedure =
                            new AssignmentNineIncreaseAgeStoredProcedure(this.connection);
                    nineIncreaseAgeStoredProcedure.run();
                    break;
                default:
                    System.out.println("Numbers from 2 to 9, remember?");
                    break;
            }

            System.out.println();
            System.out.println("*****************************");
            System.out.flush();
            System.out.println(welcomeInfo);
            input = scanner.nextLine();
        }
    }
}
