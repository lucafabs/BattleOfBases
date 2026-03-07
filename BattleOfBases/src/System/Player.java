package System;
import Structure.Building;
import Structure.Resource;
import Unit.Inhabitant;
import Unit.Upgradeable;

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
        loop: while (true) {
            printCommands();
            giveInputPrompt();

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
                case "stats":
                    stats();
                    break;
                case "train":
                    train();
                    break;
                case "upgrade":
                    upgrade();
                    break;
                case "quit":
                    break loop;
                default:
                    System.out.println("Not a valid command.");
            }
        }

        System.out.println("Exiting game...");
    }

    //region [Attacking]
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

        giveInputPrompt();

        if (input.equals("yes")) {
            System.out.println("Performing attack.");
            Engine.getInstance().processBattle(village, defender);
        } else {
            System.out.println("Not processing attack.");
        }
    }
    //endregion
    //region [Build]
    private void build() {
        System.out.println("What kind of building would you like to build?");
        printBuildings();
        giveInputPrompt();

        for(Building building: Engine.buildingTypes) {
            if(input.equals(building.name))
            {
                Engine.getInstance().tryBuild(village, building);
            }
        }
    }
    //endregion
    //region [Generate]
    private void generate() {
        Village newVillage = Engine.getInstance().generateNewVillage(village);
        System.out.println("\nAttacking new village");
        //attempt to attack the newly generated village
        attackTargetPrompt(newVillage);
    }
    //endregion
    //region [Resources]
    private void stats() {
        printStats();
        System.out.println("Type anything to go back");
        giveInputPrompt();
    }
    //endregion
    //region [Train]
    private void train() {
        System.out.println("What kind of inhabitant would you like to train?");
        printInhabitants();
        giveInputPrompt();

        for(Inhabitant inhabitant: Engine.inhabitantTypes) {
            if(input.equals(inhabitant.name))
            {
                Engine.getInstance().tryAddInhabitant(village, inhabitant);
            }
        }
    }
    //endregion
    //region [Upgrade]
    private void upgrade() {

        System.out.println(
          "==========================================="
        + "\nWhat would you like to upgrade?"
        + "\n===========================================");
        printUpgradeables();
        System.out.println("Please provide the index of what you would like to upgrade");
        giveInputPrompt();

        int index = Integer.parseInt(input);

        if(index < 0 || index > village.upgradeables.size()-1) {
            System.out.println("Invalid index, returning...");
            return;
        }

        Engine.getInstance().tryUpgrade(village, village.upgradeables.get(index));
    }
    //endregion
    //region [Print Statements]
    private void printBuildings() {
        System.out.println(
                  "==========================================="
                + "\nArcher Tower: 5 Wood"
                + "\nCannon: 10 Iron"
                + "\nFarm: 5 Wood"
                + "\nGold Mine: 7 Iron"
                + "\nIron Mine: 6 Wood"
                + "\nLumber Mill: 5 Wood"
                + "\nQuit: Go back."
                + "\n===========================================");
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
                + "\nStats: Get your village's current stats (resources, attack, defense)."
                + "\nTrain: Produce inhabitants, if you have the required resources and capacity."
                + "\nUpgrade: Upgrade a building, given it isn't at the maximum level already."
                + "\nQuit: Exit the game."
                + "\n=======================================================================================================");
    }

    private void printInhabitants() {
        System.out.println(
                  "==========================================="
                + "\nArcher: 1 Wood, 2 Food"
                + "\nCatapult: 5 Gold, 4 Food"
                + "\nCollector: N/A"
                + "\nKnight: 5 Iron, 4 Food"
                + "\nSoldier: 1 Wood, 2 Food"
                + "\nWorker: N/A"
                + "\nQuit: Go back."
                + "\n===========================================");
    }

    private void printStats() {
        System.out.println(
          "Current resources:"
        + "\n==========================================="
        + "\nWood: " + village.checkResourceAmount(Resource.WOOD)
        + "\nIron: " + village.checkResourceAmount(Resource.IRON)
        + "\nGold: " + village.checkResourceAmount(Resource.GOLD)
        + "\nTotal Food: " + village.totalFood
        + "\nFood Consumed: " + village.foodConsumed
        + "\nAttack: " + Engine.getInstance().calculateAttack(village)
        + "\nDefense: " + Engine.getInstance().calculateDefense(village)
        + "\n===========================================");
    }

    private void printUpgradeables() {
        int index = 0;
        for(Upgradeable upgradeable : village.upgradeables) {
            System.out.println("[" + index + "] " + upgradeable.name + ": " + upgradeable.costToMake + " " + upgradeable.resourceNeeded);
            index++;
        }
    }
    //endregion
    //region [Input]
    private void giveInputPrompt() {
        System.out.println("Please enter a command:");
        input = scanner.nextLine();
        input = formatString(input);
    }
    private String formatString(String s) {
        s = s.toLowerCase();
        s = s.replaceAll("\\s", "");
        return s;
    }
    //endregion
}
