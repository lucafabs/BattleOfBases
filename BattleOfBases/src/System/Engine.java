package System;
import Structure.*;
import Unit.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Engine implements IAttack, ITimeSystem {

    //the maximum level a village may be
    private int maxVillageLevel;

    public List<Village> villages;

    //default buildings a village will receive when created initially
    private List<Building> defaultBuildings = new ArrayList<>();

    //default inhabitants a village will receive when created initially
    private List<Inhabitant> defaultInhabitants = new ArrayList<>();

    //default amount of each resource a village will start with
    private static final int defaultResources = 100;

    //how many other villages to spawn initially (this is mostly for single player purposes, 1 village/player in future)
    private static final int initialVillages = 10;

    public static Building[] buildingTypes = new Building[] {new ArcherTower(), new Cannon(), new Farm(), new GoldMine(), new IronMine(), new LumberMill()};
    public static Inhabitant[] inhabitantTypes = new Inhabitant[] {new Archer(), new Catapult(), new Knight(), new Soldier(), new Collector(), new Worker()};

    private static Engine singleInstance = null;

    /**
     * Engine is in charge of making decisions when it comes to villages & time-related tasks
     * This includes adding/upgrading buildings and inhabitants, and performing attacks as well as their loot payouts
     */
    private Engine() {
        villages = new ArrayList<>();
        generateVillages();
    }

    /**
     * Singleton for the engine
     * @return the singleton
     */
    public static synchronized Engine getInstance() {
        if (singleInstance == null)
            singleInstance = new Engine();

        return singleInstance;
    }

    //region [Village Generation]
    /**
     * Initializes villages
     */
    public void generateVillages() {
        System.out.println("Generating villages");
        for(int i = 0; i < initialVillages; i++) {
            //give village the buildings & inhabitants they spawn with by default
            defaultBuildings = new ArrayList<>(Arrays.asList(new Cannon(), new ArcherTower(), new Farm(3)));
            defaultInhabitants = new ArrayList<>(Arrays.asList(new Archer(), new Archer(), new Archer(), new Archer(), new Archer(), new Knight()));

            Village newVillage = new Village(this, defaultBuildings, defaultInhabitants);
            villages.add(newVillage);
            //give village the default amount of resources
            newVillage.recalculateFood();
            newVillage.addResource(Resource.WOOD, defaultResources);
            newVillage.addResource(Resource.IRON, defaultResources);
            newVillage.addResource(Resource.GOLD, defaultResources);
        }
    }

    /**
     * generate a new village that is relative in strength to v
     * @param v the village asking to generate a new one
     */
    public Village generateNewVillage(Village v) {
        //the attack and defense the new village should aim to have
        int approxAttack = calculateAttack(v);
        int approxDef = calculateDefense(v);

        Village newVillage = new Village(this);
        int newVillageAttack = 0;
        int newVillageDefense = 0;

        newVillage.addResource(Resource.WOOD, defaultResources);
        newVillage.addResource(Resource.IRON, defaultResources);
        newVillage.addResource(Resource.GOLD, defaultResources);

        //add random defenses until an appropriate defensive strength is reached
        while(newVillageDefense < approxDef)
        {
            //range from 0-1, which would build a cannon or archer tower
            Building newBuilding = buildingTypes[(int)(Math.random()*2)];
            doConstruction(newVillage, newBuilding);
            newVillageDefense = calculateDefense(newVillage);
        }

        //add random inhabitants until an appropriate attack strength is reached
        while(newVillageAttack < approxAttack)
        {
            //range from 0-3, which would spawn Archer, Catapult, Knight or Soldier
            Inhabitant newInhabitant = inhabitantTypes[(int)(Math.random()*4)];
            addInhabitant(newVillage, newInhabitant);
            newVillageAttack = calculateAttack(newVillage);
        }
        return newVillage;
    }
    //endregion
    //region [Buildings]

    /**
     * Attempt to build, based on constraints in checkIfUpgradeAllowed
     * @param v the village being built in
     * @param building the building being built
     */
    public void tryBuild(Village v, Building building) {
        if(checkIfUpgradeAllowed(v, building)) {
            doConstruction(v, building);
        }
    }

    /**
     * Go through with the construction of a building
     * @param v the village being built in
     * @param building the building being built
     */
    private void doConstruction(Village v, Building building) {
        System.out.println("Constructing " + building.name);
        Building newBuilding = building.clone();

        if(newBuilding != null) {
            newBuilding.build(v);
            //process construction time here
            upgradeTimeout(newBuilding.upgradeTime, newBuilding);
        }
        else {
            System.out.println("failed to build " + building.name);
        }
    }

    //endregion
    //region [Inhabitants]

    /**
     * attempt to add inhabitant to village depending on constraint in checkIfTrainAllowed
     * @param v the village being added to
     * @param inhabitant the inhabitant being added
     */
    public void tryAddInhabitant(Village v, Inhabitant inhabitant) {
        if(checkIfTrainAllowed(v, inhabitant))
            addInhabitant(v, inhabitant);
        else
            System.out.println("Could not add inhabitant");

    }

    /**
     * Finish adding the inhabitant to the village
     * @param v the village being added to
     * @param inhabitant the inhabitant being added
     */
    private void addInhabitant(Village v, Inhabitant inhabitant) {
        System.out.println("adding inhabitant " + inhabitant.name);
        inhabitant.addUnit(v);
    }
    //endregion
    //region [Battles]
    public Village randomAttack(){
        //check if attack is possible - are there villages that can be attacked? is the attacker allowed?
        System.out.println("Searching for villages that can be attacked...");
        for(Village v : villages) {
            if(!v.shieldActive) {
                //this village can be attacked
                return v;
            }
        }
        return null;
    }

    public void processBattle(Village attacker, Village defender) {
        //measure attack of attacker & defense of defender
        System.out.println("Processing battle.");
        if (calculateAttack(attacker) > calculateDefense(defender)) {
            //you win!
            System.out.println("You won:");
            int[] payout = performLootPayout(attacker, defender);

            System.out.println(
                    payout[0] + " Wood\n"
                            +payout[1] + " Iron\n"
                            +payout[2] + " Gold");
        }
        else {
            System.out.println("You lose.");
        }
        shieldTimeout(defender.shieldDuration, defender);
    }

    public int calculateAttack(Village v) {
        //System.out.println("Calculating attack of village");
        int attack = 0;
        for(Inhabitant inhabitant: v.inhabitants) {
            if(inhabitant instanceof  ArmyMember) attack += ((ArmyMember) inhabitant).getAttack();
        }
        return attack;
    }

    public int calculateDefense(Village v) {
        //System.out.println("Calculating defense of village");
        int defense = 0;
        for(Building building: v.buildings) {
            if(building instanceof DefenseBuilding) defense += ((DefenseBuilding) building).getDefense();
        }
        return defense;
    }

    public int[] performLootPayout(Village attacker, Village defender){
        //measure loot to reward based on target's amount of resources
        System.out.println("Calculating loot payout");

        //calculation
        int[] payout = new int[]{(int)(0.10*defender.checkResourceAmount(Resource.WOOD)), (int)(0.10*defender.checkResourceAmount(Resource.IRON)), (int)(0.10*defender.checkResourceAmount(Resource.GOLD))};

        //remove resources from target
        defender.spendResource(Resource.WOOD, payout[0]);
        defender.spendResource(Resource.IRON, payout[1]);
        defender.spendResource(Resource.GOLD, payout[2]);

        //add resources to attacker
        attacker.addResource(Resource.WOOD, payout[0]);
        attacker.addResource(Resource.IRON, payout[1]);
        attacker.addResource(Resource.GOLD, payout[2]);

        return payout;
    }
    //endregion
    //region [Upgrading]
    public<T extends Upgradeable> void tryUpgrade(Village v, T upgradeable) {

        if(!checkIfUpgradeAllowed(v, upgradeable))
        {
            System.out.println("Upgrade not allowed");
            return;
        }
        upgradeable.upgrade();
        //apply construction time
    }

    /**
     * Check if an upgrade can be performed
     * @param village the village being upgraded in
     * @param upgradeable the building or troop being upgraded
     * @return whether or not the upgrade can be performed
     * @param <T> Generic for objects that can be upgraded
     */
    public<T extends Upgradeable> boolean checkIfUpgradeAllowed(Village village, T upgradeable){
        if(village.checkResourceAmount(upgradeable.resourceNeeded) < upgradeable.costToMake) return false;
        if(upgradeable.level >= upgradeable.maxLevel) return false;
        if(upgradeable.underConstruction) return false;

        return true;
    }

    /**
     * Every time an upgrade occurs, set the object to under construction for the given amount of time
     * @param timeInSeconds the amount of time to wait
     * @param upgradeable the object being upgraded
     * @param <T> Generic for objects that can be upgraded
     */
    public<T extends Upgradeable> void upgradeTimeout(float timeInSeconds, T upgradeable) {
        upgradeable.underConstruction = true;

        new Thread(() -> {
            try {
                Thread.sleep((long)(timeInSeconds * 1000));
                upgradeable.underConstruction = false;
                System.out.println("Done upgrade on " + upgradeable.name + ".");
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
        }).start();
    }
    //endregion

    /**
     * Check if a troop can be trained (added to the army)
     * @param village the village being added to
     * @param inhabitant the inhabitant being adde
     * @return whether or not training is allowed
     */
    public boolean checkIfTrainAllowed(Village village, Inhabitant inhabitant) {
        return (village.hasFoodRoom(inhabitant.foodRequired));
    }
    /**
     * Determine the rank of the given village, compared to other villages
     * @param v the given village
     * @return the rank of the village
     */
    public int determineRank(Village v) {
        System.out.println("Determining rank of village");
        return 0;
    }
    /**
     * give village v a shield for a given amount of time
     * @param timeInSeconds the amount of time to provide the shield for
     * @param v the village being shielded
     */
    public void shieldTimeout(float timeInSeconds, Village v) {
        v.shieldActive = true;

        new Thread(() -> {
            try {
                Thread.sleep((long)(timeInSeconds * 1000));
                v.shieldActive = false;
                System.out.println("Shield expired.");
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
        }).start();
    }
}
