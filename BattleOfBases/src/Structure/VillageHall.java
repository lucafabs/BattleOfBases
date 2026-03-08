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
        name = "villagehall";
        setValues(100, Resource.GOLD, 50, 16);
        totalArmy = new ArrayList<>();
        this.village = village;
    }
    public Inhabitant createUnit(Inhabitant unitType) {
        if(village.checkResourceAmount(unitType.resourceNeeded) > unitType.costToMake && totalFood > unitType.foodRequired)
        {
            System.out.println("Creating unit of type: " + unitType);
            return unitType;
        }

        System.out.println("Cannot create " + unitType);
        return null;
    }
    public ArmyMember createArmyUnit(ArmyMember unitType) {
        //attempt to instantiate a new unit based on cost

        if(village.checkResourceAmount(unitType.resourceNeeded) > unitType.costToMake && totalFood > unitType.foodRequired)
        {
            System.out.println("Creating unit of type: " + unitType);
            return unitType;
        }

        System.out.println("Cannot create " + unitType);
        return null;
    }

    //This building should never be cloned
    public Building clone() {
        return null;
    }
}
