package Unit;
import Structure.*;
import System.*;

abstract public class Inhabitant extends Upgradeable {
    public int foodRequired;
    private Village village;

    public void setCost(int cost, int food, Resource resource){
        level = 0;
        costToMake = cost;
        foodRequired = food;
        resourceNeeded = resource;
    }

    public void upgrade(){
        //attempt to upgrade based on available workers and amount of resources available
        System.out.println("Upgrading " + name + ".");
        Engine.getInstance().upgradeTimeout(upgradeTime, this);
        level++;
    }

    // Seeing if a unit can be built.
    public void createUnit(Village v){
        v.foodConsumed += foodRequired;
    }

    public void deleteUnit() {
        //this unit is removed from current assignments and safely deleted
        System.out.println("Deleting Unit");
    }
}
