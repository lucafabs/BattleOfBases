package Unit;
import Structure.*;
import System.*;

abstract public class Inhabitant extends Upgradeable {
    public int foodRequired;
    private Village village;

    /**
     * Initialize cost variables
     * @param cost the amount of the resource required
     * @param food the amount of food consumed by this inhabitant
     * @param resource the type of resource required to train this inhabitant
     */
    public void setCost(int cost, int food, Resource resource){
        level = 0;
        costToMake = cost;
        foodRequired = food;
        resourceNeeded = resource;
    }

    /**
     * attempt to upgrade based on available workers and amount of resources available
     */
    public void upgrade(){
        System.out.println("Upgrading " + name + ".");
        Engine.getInstance().upgradeTimeout(upgradeTime, this);
        level++;
    }

    /**
     * this unit is removed from current assignments and safely deleted
     */
    public void deleteUnit() {
        System.out.println("Deleting Unit");
    }
}
