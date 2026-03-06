package System;
import Structure.*;
import Unit.*;

import java.util.ArrayList;
import java.util.List;

public class Engine implements IAttack, ITimeSystem {
    private ITimeSystem timeSystem;
    private int maxVillageLevel;

    public Village[] villages;

    //default buildings a village will receive when created initially
    private List<Building> defaultBuildings = new ArrayList<>();

    //default inhabitants a village will receive when created initially
    private Inhabitant[] defaultInhabitants;

    private static final int defaultResources = 100;

    public static Building[] buildingTypes = new Building[] {new ArcherTower(), new Cannon(), new Farm(), new GoldMine(), new IronMine(), new LumberMill()};


    private static Engine singleInstance = null;

    private Engine() {
        villages = new Village[10];
        generateVillages();
    }
    public static synchronized Engine getInstance()
    {
        if (singleInstance == null)
            singleInstance = new Engine();

        return singleInstance;
    }

    public Village randomAttack(){
        //check if attack is possible - are there villages that can be attacked? is the attacker allowed?
        System.out.println("Searching for villages that can be attacked...");
        for(Village v : villages) {
            if(v.shieldDuration <= 0) {
                //this village can be attacked
                return v;
            }
        }
        return null;
        //select a random village Villages & launch an attack
    }

    public void generateVillages() {
        //initialize villages[]
        System.out.println("Generating villages");
        for(int i = 0; i < villages.length; i++) {
            //give village the buildings & inhabitants they spawn with by default
            defaultBuildings.add(new Cannon());
            defaultInhabitants = new Inhabitant[] {new Archer(), new Archer(), new Archer(), new Archer(), new Archer()};

            villages[i] = new Village(this, defaultBuildings, defaultInhabitants);

            //give village the default amount of resources
            villages[i].addResource(Resource.WOOD, defaultResources);
            villages[i].addResource(Resource.IRON, defaultResources);
            villages[i].addResource(Resource.GOLD, defaultResources);
        }
    }

    public int determineRank(int wins) {
        //compare rank to other villages[]
        System.out.println("Determining rank of village");
        return 0;
    }

    public void tryBuild(Village v, Building b) {
        if(checkIfUpgradeAllowed(b.costToMake, b.resourceNeeded, b.level, v)) {
            System.out.println("Constructing " + b.name);
            Building newBuilding = b.clone();
            if(newBuilding != null) {
                newBuilding.build(v);
                v.buildings.add(newBuilding);
            }
            else {
                System.out.println("failed to build " + b.name);
            }
        }
    }

    public boolean checkIfUpgradeAllowed(int cost, Resource resource, int currLevel, Village village){
        //check resources & workers for if upgrade is allowed
        return village.checkResourceAmount(resource) > cost && currLevel < 3;
    }

    public void processConstructionTime(float time) {
        //workers are occupied during construction time
        System.out.println("Constructing...");
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
                            +payout[2] + " Gold\n");
        }
        else {
            System.out.println("You lose.");
        }
    }

    public int calculateAttack(Village v) {
        System.out.println("Calculating attack of village");
        int attack = 0;
        for(Inhabitant inhabitant: v.inhabitants) {
            if(inhabitant instanceof  ArmyMember) attack += ((ArmyMember) inhabitant).getAttack();
        }
        return attack;
    }

    public int calculateDefense(Village v) {
        System.out.println("Calculating defense of village");
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
        defender.addResource(Resource.WOOD, -payout[0]);
        defender.addResource(Resource.IRON, -payout[1]);
        defender.addResource(Resource.GOLD, -payout[2]);

        //add resources to attacker
        attacker.addResource(Resource.WOOD, payout[0]);
        attacker.addResource(Resource.IRON, payout[1]);
        attacker.addResource(Resource.GOLD, payout[2]);

        return payout;
    }

    public void timeToWait(float time){
        //wait for time
        System.out.println("Waiting for: " + time);
    }
}
