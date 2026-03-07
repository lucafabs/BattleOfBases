package System;
import Structure.*;
import Unit.*;

import java.util.ArrayList;
import java.util.List;

public class Village {
    private int totalWood;
    private int totalIron;
    private int totalGold;
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

    public int checkResourceAmount(Resource type){
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
    public void spendResource(Resource type, int amount){
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

    public void addResource(Resource type, int amount) {
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
}
