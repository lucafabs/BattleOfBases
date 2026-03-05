package System;
import Structure.*;
import Unit.*;

public class Village {
    private String playerName;
    private String playerId;
    private int totalWood;
    private int totalGold;
    private int totalIron;
    private int villageLevel;
    private int mapSize;
    private int amountOfWins;
    private int rank;

    public Engine engine;
    public VillageHall villageHall;
    private Building[] buildings;
    private Inhabitant[] inhabitants;

    public Village(Engine engine, VillageHall villageHall) {
        this.engine = engine;

        this.villageHall = new VillageHall(this);
    }

    public Village(Engine engine, Building[] buildings, Inhabitant[] inhabitants) {
        this.engine = engine;
        this.buildings = buildings;
        this.inhabitants = inhabitants;

        this.villageHall = new VillageHall(this);

    }

    public void upgradeVillage(Resource materials, int cost, float time) {
        //check with engine if upgrade is possible based on the village's materials and workers available
        System.out.println("attempt to upgrade village");
        if(engine.checkIfUpgradeAllowed(cost, materials, villageLevel, this)) {
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
        } else if (type.equals(Resource.IRON)) {
            totalIron += amount;
        } else if (type.equals(Resource.GOLD)) {
            totalGold += amount;
        } else {
            System.out.println("Incorrect resource inputted.");
        }
    }
}
