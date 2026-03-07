package Unit;
import Structure.*;

public class Collector extends Inhabitant{
    private int productionMax;

    private Mine mine;

    public Collector() {
        name = null;
    }

    public void assignMine(Mine m) {
        //collector is assigned to mine
        System.out.println("Assigning collector to mine");
        mine = m;
    }
}
