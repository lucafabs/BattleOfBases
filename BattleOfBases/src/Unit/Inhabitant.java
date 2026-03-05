package Unit;
import Structure.*;
import System.*;

abstract public class Inhabitant {
    public int foodRequired;
    public Resource resourceRequired;
    public int costToMake;

    private Village village;

    public void setCost(int cost, int food, Resource resource){
        costToMake = cost;
        foodRequired = food;
        resourceRequired = resource;
    }

    // Seeing if a unit can be built.
    public boolean createUnit(int food, Resource resource, int cost){
        return food >= foodRequired && resource.equals(resourceRequired) && cost >= costToMake;
    }

    public void deleteUnit() {
        //this unit is removed from current assignments and safely deleted
        System.out.println("Deleting Unit");
    }
}
