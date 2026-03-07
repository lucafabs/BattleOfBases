package Structure;
import Unit.*;
import System.*;
abstract public class Building extends Upgradeable {
    protected int areaSize;
    protected int hitPoints;

    protected Village village;
    public void setValues(int hp, Resource resource, int cost, int area){
        // By Default, every starts at 0 and can be upgraded.
        level = 0;

        hitPoints = hp;
        resourceNeeded = resource;
        costToMake = cost;
        areaSize = area;
    }

    public void upgrade(){
        //attempt to upgrade based on available workers and amount of resources available
        System.out.println("Upgrading " + name + ".");
        Engine.getInstance().upgradeTimeout(upgradeTime, this);
        level++;
        hitPoints += 5;
    }
    public void repair(Resource type, Worker builder, int cost, float time){
        //attempt to repair based on available workers and resources available
        System.out.println("attempting to repair building");
    }
    public void build(Village owner) {
        //attempt to build this building based on available workers and resources available
        village = owner;
        owner.upgradeables.add(this);

        owner.spendResource(resourceNeeded, costToMake);
        owner.buildings.add(this);
        System.out.println("Building was successfully built.");

        if(this instanceof Farm) village.totalFood += ((Farm) this).foodProduced;
    }

    public abstract Building clone();
}
