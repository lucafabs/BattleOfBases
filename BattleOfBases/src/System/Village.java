package System;
import ChallengeDecision.ChallengeAttack;
import ChallengeDecision.ChallengeDefense;
import ChallengeDecision.ChallengeEntitySet;
import ChallengeDecision.ChallengeResource;
import Structure.*;
import Unit.*;

import java.util.ArrayList;
import java.util.List;

public class Village {
    private double totalWood;
    private double totalIron;
    private double totalGold;
    private int villageLevel;
    private int mapSize;
    private int amountOfWins;
    private int rank;

    public float shieldDuration = 10f;
    public boolean shieldActive;
    public int totalFood;
    public int foodConsumed;

    public Engine engine;
    public VillageHall villageHall;
    public List<Building> buildings = new ArrayList<>();
    public List<Inhabitant> inhabitants = new ArrayList<>();
    public List<Upgradeable> upgradeables = new ArrayList<>();

    private ChallengeEntitySet<Double, Double> challengeEntitySet;
    private List<ChallengeAttack<Double, Double>> challengeAttacks;
    private List<ChallengeDefense<Double, Double>> challengeDefenses;
    private List<ChallengeResource<Double, Double>> challengeResources;

    public Village(Engine engine) {
        this.engine = engine;
        this.villageHall = new VillageHall(this);
        upgradeables.add(villageHall);
    }

    public Village(Engine engine, List<Building> buildings, List<Inhabitant> inhabitants) {
        this.engine = engine;
        this.buildings = buildings;
        this.inhabitants = inhabitants;

        this.villageHall = new VillageHall(this);
        upgradeables.add(villageHall);
        upgradeables.addAll(buildings);
        upgradeables.addAll(inhabitants);
    }

    public void upgradeVillage(Village v) {
        //check with engine if upgrade is possible based on the village's materials and workers available
        System.out.println("attempt to upgrade village");
        if(engine.checkIfUpgradeAllowed(v, villageHall)) {
            //upgrade village
            System.out.println("upgrading village...");
            villageLevel++;

        }
        else {
            //don't upgrade village
            System.out.println("not upgrading village...");
        }
    }

    public double checkResourceAmount(Resource type){
        if(type.equals(Resource.WOOD)){
            return totalWood;
        } else if (type.equals(Resource.IRON)){
            return totalIron;
        } else if (type.equals(Resource.GOLD)){
            return totalGold;
        } else {
            System.out.println("Incorrect resource inputted.");
            return 0;
        }
    }
    public void spendResource(Resource type, double amount){
        if(type.equals(Resource.WOOD)){
            totalWood -= amount;
        } else if (type.equals(Resource.IRON)){
            totalIron -= amount;
        } else if (type.equals(Resource.GOLD)){
            totalGold -= amount;
        } else {
            System.out.println("Incorrect resource inputted.");
        }
    }

    public void addResource(Resource type, double amount) {
        if (type.equals(Resource.WOOD)) {
            totalWood += amount;
            if (totalWood < 0) totalWood = 0;
        } else if (type.equals(Resource.IRON)) {
            totalIron += amount;
            if (totalIron < 0) totalIron = 0;
        } else if (type.equals(Resource.GOLD)) {
            totalGold += amount;
            if (totalGold < 0) totalGold = 0;
        } else {
            System.out.println("Incorrect resource inputted.");
        }
    }

    /**
     * used to recalculate the amount of food used and consumed by the village
     * this is useful when villages are constructed and farms/inhabitants may not have been initialized traditionally
     */
    public void recalculateFood() {
        totalFood = 0;
        foodConsumed = 0;

        for(Building building : buildings) {
            if(building instanceof Farm)
                totalFood += ((Farm) building).foodProduced;
        }
        for(Inhabitant inhabitant : inhabitants) {
            foodConsumed += inhabitant.foodRequired;
        }
    }

    public boolean hasFoodRoom(int additionalFood) {
        return (totalFood - (foodConsumed + additionalFood)) >= 0;
    }

    public int calculateAttack() {
        //System.out.println("Calculating attack of village");
        int attack = 0;
        for(Inhabitant inhabitant: inhabitants) {
            if(inhabitant instanceof  ArmyMember) attack += (int) ((ArmyMember) inhabitant).getAttack();
        }
        return attack;
    }

    public int calculateDefense() {
        //System.out.println("Calculating defense of village");
        int defense = 0;
        for(Building building: buildings) {
            if(building instanceof DefenseBuilding) defense += (int) ((DefenseBuilding) building).getDefense();
        }
        return defense;
    }

    public ChallengeEntitySet getChallengeEntitySet() {
        challengeResources = new ArrayList<>();
        challengeAttacks = new ArrayList<>();
        challengeDefenses = new ArrayList<>();

        //resources don't have hit-points
        challengeResources.add(new ChallengeResource<>(totalGold, 0.0d));
        challengeResources.add(new ChallengeResource<>(totalIron, 0.0d));
        challengeResources.add(new ChallengeResource<>(totalWood, 0.0d));

        //add defenses and attackers to their respective lists
        for (Upgradeable upgradeable : upgradeables) {
            if(upgradeable instanceof DefenseBuilding) {
                challengeDefenses.add(new ChallengeDefense<>(((DefenseBuilding) upgradeable).getDefense(), upgradeable.hitPoints));
            }
            else if(upgradeable instanceof ArmyMember) {
                challengeAttacks.add(new ChallengeAttack<>(((ArmyMember)upgradeable).getAttack(), upgradeable.hitPoints));
            }
        }

        challengeEntitySet = new ChallengeEntitySet<>(challengeAttacks, challengeDefenses, challengeResources);
        return challengeEntitySet;
    }
}
