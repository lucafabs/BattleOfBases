import Structure.Resource;
import System.*;
import Unit.*;

public class Main {
    public static void main(String[] args) {

        Village village = Engine.getInstance().villages.get(9);

        //in the future multiple players will be supported
        Player p = new Player(village);
    }
}