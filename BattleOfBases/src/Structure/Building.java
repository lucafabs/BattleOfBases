package Structure;
import Unit.*;
import System.*;
abstract public class Building extends Upgradeable {
    protected int areaSize;

    protected Village village;

    /**
     * Initialize values
     * @param hp hitpoints of the building
     * @param resource the type of resource required to upgrade the building
     * @param cost the amount of the resource required
     * @param area the size of the building
     */
    public void setValues(int hp, Resource resource, int cost, int area){
        // By Default, every starts at 0 and can be upgraded.
        level = 0;

        hitPoints = hp;
        resourceNeeded = resource;
        costToMake = cost;
        areaSize = area;
    }

    /**
     * Upgrade the building to the next level, in future iterations this will change the cost of the building
     * & some stats depending on the building
     */
    public void upgrade(){
        //attempt to upgrade based on available workers and amount of resources available
        System.out.println("Upgrading " + name + ".");
        Engine.getInstance().upgradeTimeout(upgradeTime, this);
        level++;
        hitPoints += 5;
    }

    /**
     * repairs the given building
     * @param type the type of resource
     * @param builder the worker that will perform the repair
     * @param cost the amount of the resource required
     * @param time the amount of time for the repair
     */
    public void repair(Resource type, Worker builder, int cost, float time){
        //attempt to repair based on available workers and resources available
        System.out.println("attempting to repair building");
    }

    /**
     * Builds this building in the given village, spending the required resources
     * @param owner the village the building belongs to
     */
    public void build(Village owner) {
        //attempt to build this building based on available workers and resources available
        village = owner;
        owner.upgradeables.add(this);

        owner.spendResource(resourceNeeded, costToMake);
        owner.buildings.add(this);
        System.out.println("Building was successfully built.");

        if(this instanceof Farm) village.totalFood += ((Farm) this).foodProduced;
    }

    /**
     * Makes a copy of the building, useful when adding default buildings in Engine
     * @return the copy of the building
     */
    public abstract Building clone();
}
