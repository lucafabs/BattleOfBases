package System;

import Structure.*;
import Unit.*;
import ChallengeDecision.*;
import FactoryPattern.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Engine implements ITimeSystem, IAttack {

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

    public static Building[] buildingTypes = new Building[]{new ArcherTower(), new Cannon(), new Farm(), new GoldMine(), new IronMine(), new LumberMill()};
    public static Inhabitant[] inhabitantTypes = new Inhabitant[]{new Archer(), new Catapult(), new Knight(), new Soldier(), new Collector(), new Worker()};

    private static Engine singleInstance = null;

    //factories
    BuildingFactory buildingFactory = new BuildingFactory();
    InhabitantFactory inhabitantFactory = new InhabitantFactory();

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
     *
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
        for (int i = 0; i < initialVillages; i++) {

            Village v = new Village(this);

            v.addResource(Resource.WOOD, defaultResources);
            v.addResource(Resource.IRON, defaultResources);
            v.addResource(Resource.GOLD, defaultResources);

            villages.add(v);
        }
    }

    /**
     * generate a new village that is relative in strength to v
     *
     * @param base the village asking to generate a new one
     */
    public synchronized Village generateNewVillage(Village base) {
        //the attack and defense the new village should aim to have
        int targetAttack = base.calculateAttack();
        int targetDefense = base.calculateDefense();

        Village v = new Village(this);

        v.addResource(Resource.WOOD, defaultResources);
        v.addResource(Resource.IRON, defaultResources);
        v.addResource(Resource.GOLD, defaultResources);

        int def = 0;
        int atk = 0;

        while (def < targetDefense) {
            Building b = buildingTypes[(int) (Math.random() * 2)];
            doConstruction(v, b);
            def = v.calculateDefense();
        }

        while (atk < targetAttack) {
            Inhabitant i = inhabitantTypes[(int) (Math.random() * 4)];
            addInhabitant(v, i);
            atk = v.calculateAttack();
        }

        villages.add(v);
        return v;
    }
    //endregion
    //region [Buildings]

    /**
     * Attempt to build, based on constraints in checkIfUpgradeAllowed
     *
     * @param v        the village being built in
     * @param building the building being built
     */
    public synchronized boolean tryBuild(Village v, Building building) {
        if (!checkIfUpgradeAllowed(v, building)) {
            return false;
        }

        doConstruction(v, building);
        return true;
    }

    /**
     * Go through with the construction of a building
     *
     * @param v        the village being built in
     * @param building the building being built
     */
    private void doConstruction(Village v, Building building) {
        Building copy = building.clone();
        if (copy != null) {
            buildingFactory.buildBuilding(copy, v);
        }
    }

    //endregion
    //region [Inhabitants]

    /**
     * attempt to add inhabitant to village depending on constraint in checkIfTrainAllowed
     *
     * @param v          the village being added to
     * @param inhabitant the inhabitant being added
     */
    public synchronized boolean tryAddInhabitant(Village v, Inhabitant inhabitant) {
        if (!checkIfTrainAllowed(v, inhabitant)) {
            return false;
        }

        addInhabitant(v, inhabitant);
        return true;
    }

    /**
     * Finish adding the inhabitant to the village
     *
     * @param v          the village being added to
     * @param inhabitant the inhabitant being added
     */
    private void addInhabitant(Village v, Inhabitant inhabitant) {
        inhabitantFactory.createInhabitant(inhabitant, v);
    }

    //endregion
    //region [Battles]
    public Village randomAttack(Village attacker) {
        //check if attack is possible - are there villages that can be attacked? is the attacker allowed?
        for (Village v : villages) {
            if (v != attacker && !v.shieldActive) {
                //this village can be attacked
                return v;
            }
        }
        return null;
    }

    public synchronized BattleResult processBattle(Village attacker, Village defender) {

        ChallengeResult result =
                Arbitrer.challengeDecide(
                        attacker.getChallengeEntitySet(),
                        defender.getChallengeEntitySet()
                );

        if (result.getChallengeWon()) {

            List<Double> loot = performLootPayout(attacker, defender, result.getLoot());

            shieldTimeout(defender.shieldDuration, defender);

            return new BattleResult(true, loot, "Victory!");

        } else {

            shieldTimeout(defender.shieldDuration, defender);

            return new BattleResult(false, Arrays.asList(0.0, 0.0, 0.0), "Defeat!");
        }
    }

    public List<Double> performLootPayout(
            Village attacker,
            Village defender,
            List<ChallengeResource<Double, Double>> loot) {

        List<Double> payout = new ArrayList<>();

        payout.add(loot.get(0).getProperty()); // GOLD
        payout.add(loot.get(1).getProperty()); // IRON
        payout.add(loot.get(2).getProperty()); // WOOD

        defender.spendResource(Resource.GOLD, payout.get(0));
        defender.spendResource(Resource.IRON, payout.get(1));
        defender.spendResource(Resource.WOOD, payout.get(2));

        attacker.addResource(Resource.GOLD, payout.get(0));
        attacker.addResource(Resource.IRON, payout.get(1));
        attacker.addResource(Resource.WOOD, payout.get(2));

        return payout;
    }

    //endregion
    //region [Upgrading]
    public synchronized <T extends Upgradeable> boolean tryUpgrade(Village v, T u) {

        if (!checkIfUpgradeAllowed(v, u)) {
            return false;
        }

        u.upgrade();
        return true;
    }

    /**
     * Check if an upgrade can be performed
     *
     * @param village     the village being upgraded in
     * @param upgradeable the building or troop being upgraded
     * @param <T>         Generic for objects that can be upgraded
     * @return whether or not the upgrade can be performed
     */
    public <T extends Upgradeable> boolean checkIfUpgradeAllowed(Village village, T upgradeable) {

        if (village.checkResourceAmount(upgradeable.resourceNeeded) < upgradeable.costToMake) return false;
        if (upgradeable.level >= upgradeable.maxLevel) return false;
        if (upgradeable.underConstruction) return false;

        return true;
    }

    /**
     * Every time an upgrade occurs, set the object to under construction for the given amount of time
     *
     * @param timeInSeconds the amount of time to wait
     * @param upgradeable   the object being upgraded
     * @param <T>           Generic for objects that can be upgraded
     */
    public <T extends Upgradeable> void upgradeTimeout(float timeInSeconds, T upgradeable) {

        upgradeable.underConstruction = true;

        new Thread(() -> {
            try {
                Thread.sleep((long) (timeInSeconds * 1000));
                upgradeable.underConstruction = false;
            } catch (InterruptedException ignored) {
            }
        }).start();
    }
    //endregion

    /**
     * Check if a troop can be trained (added to the army)
     *
     * @param village    the village being added to
     * @param inhabitant the inhabitant being adde
     * @return whether or not training is allowed
     */
    public boolean checkIfTrainAllowed(Village village, Inhabitant inhabitant) {
        return (village.hasFoodRoom(inhabitant.foodRequired));
    }

    /**
     * Determine the rank of the given village, compared to other villages
     *
     * @param v the given village
     * @return the rank of the village
     */
    public int determineRank(Village v) {
        System.out.println("Determining rank of village");
        return 0;
    }

    /**
     * give village v a shield for a given amount of time
     *
     * @param timeInSeconds the amount of time to provide the shield for
     * @param v             the village being shielded
     */
    public void shieldTimeout(float timeInSeconds, Village v) {
        v.shieldActive = true;

        new Thread(() -> {
            try {
                Thread.sleep((long) (timeInSeconds * 1000));
                v.shieldActive = false;
            } catch (InterruptedException e) {
            }
        }).start();
    }
}
