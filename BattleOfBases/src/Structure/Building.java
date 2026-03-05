package Structure;
import Unit.*;
import System.*;
abstract public class Building {
    protected int level;
    protected int hitPoints;
    protected Resource resourceNeeded;
    protected int costToMake;

    private Village village;

    public void setValues(int hp, Resource resource, int cost){
        // By Default, every starts at 0 and can be upgraded.
        level = 0;

        hitPoints = hp;
        resourceNeeded = resource;
        costToMake = cost;
    }

    public void upgrade(Resource type, Worker builder, int cost, float time){
        //attempt to upgrade based on available workers and amount of resources available
        System.out.println("attempting to upgrade building");
        if(village.engine.checkIfUpgradeAllowed(cost, type, level, village) && builder.isIdle){
            System.out.println("Able to upgrade.");
            level++;
            hitPoints += 5;
        }
    }
    public void repair(Resource type, Worker builder, int cost, float time){
        //attempt to repair based on available workers and resources available
        System.out.println("attempting to repair building");
    }
    public Building build(Resource type, Worker builder, int cost, float time, Building building) {
        //attempt to build this building based on available workers and resources available
        System.out.println("attempting to build building");
        if(village.checkResourceAmount(type) > cost && builder.isIdle){
            System.out.println("Creating building");
            return building;
        }

        System.out.println("Unable to create building");
        return null;
    }
}
