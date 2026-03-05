package System;
import Structure.*;
import Unit.*;

public class Engine implements IAttack, ITimeSystem {
    private ITimeSystem timeSystem;
    private int maxVillageLevel;

    public Village[] villages;

    public Engine(Village[] villages) {
        this.villages = villages;
    }

    public void randomAttack(){
        //check if attack is possible - are there villages that can be attacked? is the attacker allowed?

        //select a random village Villages & launch an attack
        System.out.println("Launching attack");
    }

    public Village[] generateVillages(){
        //initialize villages[]
        System.out.println("Generating villages");
        return null;
    }

    public boolean checkIfUpgradeAllowed(int cost, Resource resource, int currLevel, Village village){
        //check resources & workers for if upgrade is allowed
        return village.checkResourceAmount(resource) > cost && currLevel < 3;
    }

    public int determineRank(int wins) {
        //compare rank to other villages[]
        System.out.println("Determining rank of village");
        return 0;
    }

    public void processBattle() {
        //measure attack of attacker & defense of defender
        System.out.println("Processing battle.");
    }

    public void processConstructionTime(float time) {
        //workers are occupied during construction time
        System.out.println("Constructing...");
    }

    public int calculateAttack(ArmyMember[] army){
        //measure army strength based on damage, range and type of given army[]
        System.out.println("Calculating attack strength of army.");
        return 0;
    }
    public int calculateDefense(DefenseBuilding[] defense) {
        //measure base defense based on attackRange and attackDamage of given defense[]
        System.out.println("Calculating defense");
        return 0;
    }
    public Resource[] calculateLootPayout(Village target){
        //measure loot to reward based on target's amount of resources
        System.out.println("Calculating loot payout");
        return null;
    }

    public void timeToWait(float time){
        //wait for time
        System.out.println("Waiting for: " + time);
    }

    // Call to set the values for Units
    public void setUnitValues(Archer archer, Catapult catapult, Knight knight, Soldier soldier, Worker worker, Collector collector){
        // Setting combative units costs & damage
        archer.setCost(2, 2, Resource.WOOD);
        archer.setDamage(1, 3, DamageType.ARROW, 5);

        catapult.setCost(5, 10, Resource.GOLD);
        catapult.setDamage(7, 10, DamageType.CATAPULT, 20);

        knight.setCost(1, 1, Resource.WOOD);
        knight.setDamage(2, 1, DamageType.SWORD, 8);

        soldier.setCost(3, 5, Resource.IRON);
        soldier.setDamage(4, 1, DamageType.SWORD, 16);

        // Setting non-combat units values
        worker.setCost(1, 1, Resource.WOOD);
        collector.setCost(2, 2, Resource.IRON);
    }

    public void setBuildingValues(ArcherTower archerTower, Cannon cannon, Farm farm, GoldMine goldMine, IronMine ironMine, LumberMill lumberMill){
        // Setting defensive buildings values defaults
        archerTower.setValues(15, Resource.WOOD, 5);
        archerTower.setAttack(5, 5);

        cannon.setValues(25, Resource.IRON, 10);
        cannon.setAttack(10, 10);

        // Setting productive buildings defaults
        farm.setValues(5, Resource.WOOD, 5);
        goldMine.setValues(5, Resource.IRON, 7);
        goldMine.setMine(5, Resource.GOLD, 1);
        ironMine.setValues(5, Resource.WOOD, 6);
        ironMine.setMine(5, Resource.IRON, 1);
        lumberMill.setValues(5, Resource.WOOD, 5);
        lumberMill.setMine(5, Resource.WOOD, 1);
    }
}
