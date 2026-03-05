import Structure.Resource;
import System.*;
import Unit.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine(new Village[3]);
        engine.generateVillages();
        Village village = new Village(engine, null, null);

        village.addResource(Resource.WOOD, 10);
        village.villageHall.totalFood += 10;

        // When the GUI is implemented, everything will be made through commands there.
        // For now, we prove it works by adding a unit to the totalArmy.
        Archer archer = new Archer();

        village.villageHall.totalArmy.add(village.villageHall.createArmyUnit(archer));


//        village.upgradeVillage(null, 0, 0f);
//
//        village.villageHall.launchAttack(engine.villages[0]);
//
//        village.villageHall.guardVillage(100f);




    }
}