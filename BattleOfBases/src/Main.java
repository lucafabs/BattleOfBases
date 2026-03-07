import Structure.Resource;
import System.*;
import Unit.*;

public class Main {
    public static void main(String[] args) {

        Village village = Engine.getInstance().villages.get(9);

        village.addResource(Resource.WOOD, 10);
        village.villageHall.totalFood += 10;

        // When the GUI is implemented, everything will be made through commands there.

        //in the future multiple players will be supported
        Player p = new Player(village);
    }
}