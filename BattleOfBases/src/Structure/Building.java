package Structure;
import Unit.*;
import System.*;
abstract public class Building {
    public int level;
    public Resource resourceNeeded;
    public int costToMake;

    protected int hitPoints;

    private Village village;

    public String name;

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
    public void build(Village owner) {
        //attempt to build this building based on available workers and resources available
        System.out.println("Creating building");
        village = owner;

        owner.addResource(resourceNeeded, -costToMake);
    }

    public abstract Building clone();
}
