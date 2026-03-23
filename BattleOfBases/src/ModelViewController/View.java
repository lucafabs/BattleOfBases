package ModelViewController;

import Structure.Resource;
import System.Village;
import Unit.Upgradeable;

import java.util.Scanner;

//this will handle printing menus, displaying stats, and getting user input
public class View {
    private Scanner scanner = new Scanner(System.in);
    private String input;

    /**
     * this method provides the player with a prompt to give a command, receive input through Scanner
     * and format the input so that commands are case-insensitive, and ignores spaces
     */
    public String giveInputPrompt() {
        System.out.println("Please enter a command:");
        input = scanner.nextLine();
        return formatString(input);
    }

    /**
     * This method does the formatting for user input, removing spaces and converting the string to lowercase
     * @param s the string being formatted
     * @return the reformatted string
     */
    public String formatString(String s) {
        s = s.toLowerCase();
        s = s.replaceAll("\\s", "");
        return s;
    }

    //region [Print Statements]

    /**
     * This is text printed for types of commands the player can use
     */
    public void printCommands() {
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

    /**
     * This is text printed for types of buildings the player can build
     */
    public void printBuildings() {
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

    /**
     * This is text printed for types of inhabitants the player can train
     */
    public void printInhabitants() {
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

    /**
     * This method prints all upgradeables in the village (buildings and inhabitants)
     */
    public void printUpgradeables(Village village) {
        int index = 0;
        for(Upgradeable upgradeable : village.upgradeables) {
            System.out.println("[" + index + "] " + upgradeable.name + ": " + upgradeable.costToMake + " " + upgradeable.resourceNeeded);
            index++;
        }
    }

    /**
     * This is text printed for the player's stats
     */
    public void printStats(Village village) {
        System.out.println(
                "Current resources:"
                        + "\n==========================================="
                        + "\nWood: " + village.checkResourceAmount(Resource.WOOD)
                        + "\nIron: " + village.checkResourceAmount(Resource.IRON)
                        + "\nGold: " + village.checkResourceAmount(Resource.GOLD)
                        + "\nTotal Food: " + village.totalFood
                        + "\nFood Consumed: " + village.foodConsumed
                        + "\nAttack: " + village.calculateAttack()
                        + "\nDefense: " + village.calculateDefense()
                        + "\n===========================================");
    }
    //endregion

    /**
     * Prints the given message
     * @param message message to be printed
     */
    public void showMessage(String message) {
        System.out.println(message);
    }
}
