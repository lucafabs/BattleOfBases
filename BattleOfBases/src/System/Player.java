package System;
import java.util.Scanner;
public class Player {
    int playerID;
    Village village;

    private Scanner scanner;
    private String input;
    public Player(Village village) {
        this.village = village;

        scanner = new Scanner(System.in);
        //currently actions are controlled through text input, the GUI will handle this in the future
        printCommands();

        String response;

        loop: while (true) {
            System.out.println("Please enter a command:");
            input = scanner.nextLine();
            //response = village.ReceiveCommand(input);

            switch(input) {
                case "AttackExplore":
                    AttackExplore();
                    break;
                case "Build":
                    break;
                case "Generate":
                    break;
                case "Help":
                    printCommands();
                    break;
                case "Upgrade":
                    break;
                case "Train":
                    break;
                case "Quit":
                    break loop;
                default:
                    System.out.println("Not a valid command.");
            }

            //System.out.println(response);

        }

        System.out.println("Exiting game...");
    }

    private void AttackExplore() {
        System.out.println("Generating a potential target...");
        Village defender = Engine.getInstance().randomAttack();
        if (defender == null) {
            System.out.println("Could not find suitable village");
        }else {
            System.out.println("Village found with defense strength of: " + Engine.getInstance().calculateDefense(defender)
                    + "\nYour Village has an attacking strength of: " + Engine.getInstance().calculateAttack(village)
                    + "\nWould you like to attack them?"
                    + "\nYes / No");
            input = scanner.nextLine();

            if (input.isEmpty()) {
                System.out.println("Invalid input.");
            }

            if (input.equals("Yes")) {
                System.out.println("Performing attack.");
                Engine.getInstance().processBattle(village, defender);
            } else {
                System.out.println("Not processing attack.");
            }
        }
    }

    private void printCommands() {
        System.out.println("Welcome to the Battle of Bases prototype! Utilize the following commands to perform actions!"
                + "\nAttackExplore: Find an enemy base to attack."
                + "\nBuild: Construct a new building."
                + "\nGenerate: Create a new village of relative strength to your own."
                + "\nHelp: Print this list of commands."
                + "\nUpgrade: Upgrade a building, given it isn't at the maximum level already."
                + "\nTrain: Produce inhabitants, if you have the required resources and capacity."
                + "\nQuit: Exit the game."
                + "\n=======================================================================================================");
    }

}
