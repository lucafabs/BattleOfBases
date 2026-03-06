package Structure;
import System.*;
import Unit.*;

import java.util.ArrayList;

public class VillageHall extends Building{
    private ArrayList<Worker> totalWorkers;
    private ArrayList<Collector> totalCollectors;
    public ArrayList<ArmyMember> totalArmy;
    public int totalFood;


    private Village village;

    public VillageHall(Village village) {
        totalArmy = new ArrayList<>();
        this.village = village;
    }

    public void launchAttack(Village target) {
        //attempt randomAttack through engine
        System.out.println("Attempting to launch attack");
    }
    public void guardVillage(float timeProtected) {
        //prevent attacks for given time
        System.out.println("Guarding village for: " + timeProtected);
    }
    public Inhabitant createUnit(Inhabitant unitType) {
        //attempt to instantiate a new unit based on cost

        if(village.checkResourceAmount(unitType.resourceRequired) > unitType.costToMake && totalFood > unitType.foodRequired)
        {
            System.out.println("Creating unit of type: " + unitType);
            return unitType;
        }

        System.out.println("Cannot create " + unitType);
        return null;
    }
    public ArmyMember createArmyUnit(ArmyMember unitType) {
        //attempt to instantiate a new unit based on cost

        if(village.checkResourceAmount(unitType.resourceRequired) > unitType.costToMake && totalFood > unitType.foodRequired)
        {
            System.out.println("Creating unit of type: " + unitType);
            return unitType;
        }

        System.out.println("Cannot create " + unitType);
        return null;
    }
}
