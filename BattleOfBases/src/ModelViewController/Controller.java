package ModelViewController;

import Structure.Building;
import System.Engine;
import System.Village;
import Unit.Inhabitant;

public class Controller {

    private Village village;
    private View view;
    String input;

    public Controller(Village village) {
        this.village = village;
        this.view = new View();
    }

    public void start() {
        boolean running = true;

        //currently actions are controlled through text input, the GUI will handle this in the future
        while (running) {
            view.printCommands();
            input = view.giveInputPrompt();

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
                    running = false;
                default:
                    view.showMessage("Not a valid command.");
            }
        }

        view.showMessage("Exiting game...");
    }

    //region [Attacking]

    /**
     * this performs the necessary I/O for AttackExplore
     */
    private void attackExplore() {
        view.showMessage("Generating a potential target...");
        Village defender = Engine.getInstance().randomAttack();
        if (defender == null) {
            view.showMessage("Could not find suitable village");
        }
        else {
            attackTargetPrompt(defender);
        }
    }

    /**
     * I/O for performing the attack on the randomly generated village from AttackExplore
     * @param defender the village defending from this player
     */
    private void attackTargetPrompt(Village defender) {
        view.showMessage("Village found with defense strength of: " + defender.calculateDefense()
                + "\nYour Village has an attacking strength of: " + village.calculateAttack()
                + "\nWould you like to attack them?"
                + "\nYes / No");

        input = view.giveInputPrompt();

        if (input.equals("yes")) {
            view.showMessage("Performing attack.");
            Engine.getInstance().processBattle(village, defender);
        } else {
            view.showMessage("Not processing attack.");
        }
    }
    //endregion
    //region [Build]

    /**
     * Performs functionality for attempting to build a building
     */
    private void build() {
        view.showMessage("What kind of building would you like to build?");
        view.printBuildings();
        input = view.giveInputPrompt();

        for(Building building: Engine.buildingTypes) {
            if(input.equals(building.name))
            {
                Engine.getInstance().tryBuild(village, building);
            }
        }
    }
    //endregion
    //region [Generate]

    /**
     * calls method for engine to generate a village, then provides attack prompt for player to attack that village
     */
    private void generate() {
        Village newVillage = Engine.getInstance().generateNewVillage(village);
        view.showMessage("\nAttacking new village");
        //attempt to attack the newly generated village
        attackTargetPrompt(newVillage);
    }
    //endregion
    //region [Stats]

    /**
     * Print stats for this player's village
     */
    private void stats() {
        view.printStats(village);
        view.showMessage("Type anything to go back");
        input = view.giveInputPrompt();
    }
    //endregion
    //region [Train]

    /**
     * I/O for training a new inhabitant
     */
    private void train() {
        view.showMessage("What kind of inhabitant would you like to train?");
        view.printInhabitants();
        input = view.giveInputPrompt();

        for(Inhabitant inhabitant: Engine.inhabitantTypes) {
            if(input.equals(inhabitant.name))
            {
                Engine.getInstance().tryAddInhabitant(village, inhabitant);
            }
        }
    }
    //endregion
    //region [Upgrade]

    /**
     * I/O for attempting to upgrade a unit or building
     */
    private void upgrade() {

        view.showMessage(
                "==========================================="
                        + "\nWhat would you like to upgrade?"
                        + "\n===========================================");
        view.printUpgradeables(village);
        view.showMessage("Please provide the index of what you would like to upgrade");
        input = view.giveInputPrompt();

        int index = Integer.parseInt(input);

        if(index < 0 || index > village.upgradeables.size()-1) {
            view.showMessage("Invalid index, returning...");
            return;
        }

        Engine.getInstance().tryUpgrade(village, village.upgradeables.get(index));
    }
    //endregion
}
