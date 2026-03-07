package System;
import Structure.*;
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

        String response;

        loop: while (true) {
            printCommands();
            System.out.println("Please enter a command:");
            input = scanner.nextLine();
            input = formatString(input);

            switch(input) {
                case "attackexplore":
                    attackExplore();
                    break;
                case "build":
                    build();
                    break;
                case "generate":
                    generate();
                    break;
                case "help":
                    printCommands();
                    break;
                case "upgrade":
                    break;
                case "train":
                    break;
                case "quit":
                    break loop;
                default:
                    System.out.println("Not a valid command.");
            }

            //System.out.println(response);

        }

        System.out.println("Exiting game...");
    }

    private void attackExplore() {
        System.out.println("Generating a potential target...");
        Village defender = Engine.getInstance().randomAttack();
        if (defender == null) {
            System.out.println("Could not find suitable village");
        }
        else {
            attackTargetPrompt(defender);
        }
    }

    private void attackTargetPrompt(Village defender) {
        System.out.println("Village found with defense strength of: " + Engine.getInstance().calculateDefense(defender)
                + "\nYour Village has an attacking strength of: " + Engine.getInstance().calculateAttack(village)
                + "\nWould you like to attack them?"
                + "\nYes / No");

        input = scanner.nextLine();
        input = formatString(input);

        if (input.equals("yes")) {
            System.out.println("Performing attack.");
            Engine.getInstance().processBattle(village, defender);
        } else {
            System.out.println("Not processing attack.");
        }
    }

    private void build() {
        printBuildings();
        input = scanner.nextLine();
        input = formatString(input);

        for(Building building: Engine.buildingTypes) {
            if(input.equals(building.name))
            {
                Engine.getInstance().tryBuild(village, building);
            }
        }
    }

    private void generate() {
        Village newVillage = Engine.getInstance().generateNewVillage(village);
        System.out.println("\nAttacking new village");
        //attempt to attack the newly generated village
        attackTargetPrompt(newVillage);
    }
    private String formatString(String s) {
        s = s.toLowerCase();
        s = s.replaceAll("\\s", "");
        return s;
    }
//region [Print Statements]
    private void printBuildings() {
        System.out.println("What kind of building would you like to build?"
                + "\nArcher Tower: 5 Wood"
                + "\nCannon: 10 Iron"
                + "\nFarm: 5 Wood"
                + "\nGold Mine: 7 Iron"
                + "\nIron Mine: 6 Wood"
                + "\nLumber Mill: 5 Wood"
                + "\nQuit: Go back."
                + "\n===========================================================");
    }

    private void printCommands() {
        System.out.println(
                  "\n======================================================================================================="
                + "\nWelcome to the Battle of Bases prototype! Utilize the following commands to perform actions!"
                + "\n======================================================================================================="
                + "\nAttack Explore: Find an enemy base to attack."
                + "\nBuild: Construct a new building."
                + "\nGenerate: Create a new village of relative strength to your own, for you to attack."
                + "\nHelp: Print this list of commands."
                + "\nUpgrade: Upgrade a building, given it isn't at the maximum level already."
                + "\nTrain: Produce inhabitants, if you have the required resources and capacity."
                + "\nQuit: Exit the game."
                + "\n=======================================================================================================");
    }
//endregion
}
