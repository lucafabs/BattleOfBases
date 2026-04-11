package ModelViewController;

import ChallengeDecision.BattleResult;
import Structure.Building;
import Structure.Resource;
import System.Engine;
import System.Village;
import Unit.Inhabitant;
import Unit.Upgradeable;

public class Controller {

    private Village village;

    public Controller(Village village) {
        this.village = village;
    }

    public String handleCommand(String input) {

        if (input == null || input.isEmpty()) {
            return "Empty command";
        }

        input = input.toLowerCase().replaceAll("\\s+", "");
        String[] parts = input.split(":");
        String action = parts[0];

        switch(action) {
            case "attackexplore":
                return attackExplore();

            case "build":
                return build(parts);

            case "generate":
                return generate();

            case "stats":
                return stats();

            case "train":
                return train(parts);

            case "upgrade":
                return upgrade(parts);

            case "help":
                return help();

            default:
                return "Not a valid command.";
        }
    }

    //region [Attacking]

    /**
     * this performs the necessary I/O for AttackExplore
     */
    private String attackExplore() {
        Village defender = Engine.getInstance().randomAttack(village);
        if (defender == null) {
            return "Could not find suitable village";
        }
        BattleResult result = Engine.getInstance().processBattle(village, defender);

        return result.message +
               "\nGold: " + result.loot.get(0) +
               "\nIron: " + result.loot.get(1) +
               "\nWood: " + result.loot.get(2);
    }
    //endregion
    //region [Build]

    /**
     * Performs functionality for attempting to build a building
     */
    private String build(String[] parts) {
        if (parts.length < 2) {
            return "Usage: build:<building>";
        }

        String type = parts[1];

        for(Building building: Engine.buildingTypes) {
            if(type.equalsIgnoreCase(building.name))
            {
                boolean success = Engine.getInstance().tryBuild(village, building);

                return success ? "Building started: " + building.name : "Cannot build " + building.name;
            }
        }

        return "Invalid building type.";
    }
    //endregion
    //region [Generate]

    /**
     * calls method for engine to generate a village, then provides attack prompt for player to attack that village
     */
    private String generate() {
        Village newVillage = Engine.getInstance().generateNewVillage(village);

        return "New enemy generated.\n" + "Defense: " + newVillage.calculateDefense();
    }
    //endregion
    //region [Stats]

    /**
     * Print stats for this player's village
     */
    private String stats() {
        return "===== Village Stats =====\n" +
                "Wood: " + village.checkResourceAmount(Resource.WOOD) + "\n" +
                "Iron: " + village.checkResourceAmount(Resource.IRON) + "\n" +
                "Gold: " + village.checkResourceAmount(Resource.GOLD) + "\n" +
                "Food: " + village.foodConsumed + "/" + village.totalFood + "\n" +
                "Attack: " + village.calculateAttack() + "\n" +
                "Defense: " + village.calculateDefense();
    }
    //endregion
    //region [Train]

    /**
     * I/O for training a new inhabitant
     */
    private String train(String[] parts) {
        if(parts.length < 2) {
            return "Usage: train:<unit>";
        }

        String type = parts[1];

        for (Inhabitant inhabitant : Engine.inhabitantTypes) {
            if (type.equalsIgnoreCase(inhabitant.name)) {
                boolean success = Engine.getInstance().tryAddInhabitant(village, inhabitant);
                return success ? "Training " + inhabitant.name : "Not enough capacity.";
            }
        }
        return "Invalid unit type";
    }
    //endregion
    //region [Upgrade]

    /**
     * I/O for attempting to upgrade a unit or building
     */
    private String upgrade(String[] parts) {

        if (parts.length < 2) {
            return getUpgradeableList();
        }

        try {
            int index = Integer.parseInt(parts[1]);

            if (index < 0 || index >= village.upgradeables.size()) {
                return "Invalid index.";
            }

            Upgradeable upgradeable = village.upgradeables.get(index);
            boolean success = Engine.getInstance().tryUpgrade(village, upgradeable);

            return success
                    ? "Upgrading " + upgradeable.name
                    : "Cannot upgrade " + upgradeable.name;

        } catch (NumberFormatException e) {
            return "Invalid index format.";
        }
    }

    /**
     * Build output for upgradeables in village
     * @return the list of potential upgradeables
     */
    private String getUpgradeableList() {
        StringBuilder sb = new StringBuilder("Upgradeable List:\n");

        for (int i = 0; i < village.upgradeables.size(); i++) {
            Upgradeable upgradeable = village.upgradeables.get(i);
            sb.append("[")
              .append(i)
              .append("]")
              .append(upgradeable.name)
              .append(" (Level ")
              .append(upgradeable.level)
              .append(")\n");
        }

        return sb.toString();
    }
    //endregion
    //region [Help]

    private String help() {
        return "Commands:\n" +
               "attackexplore\n" +
               "build:<type>\n" +
               "generate\n" +
               "stats\n" +
               "train:<type>\n" +
               "upgrade OR upgrade:<index>\n";
    }
    //endregion
}
