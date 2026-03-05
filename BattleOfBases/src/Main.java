import Structure.Resource;
import System.*;
import Unit.*;

public class Main {
    public static void main(String[] args) {

        Village village = Engine.getInstance().villages[9];

        village.addResource(Resource.WOOD, 10);
        village.villageHall.totalFood += 10;

        // When the GUI is implemented, everything will be made through commands there.
        // For now, we prove it works by adding a unit to the totalArmy.
        Archer archer = new Archer();

        village.villageHall.totalArmy.add(village.villageHall.createArmyUnit(archer));

        //in the future multiple players will be supported
        Player p = new Player(village);
    }
}