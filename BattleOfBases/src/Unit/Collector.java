package Unit;
import Structure.*;

public class Collector extends Inhabitant{
    private int productionMax;

    private Mine mine;

    public void assignMine(Mine mine) {
        //collector is assigned to mine
        System.out.println("Assigning collector to mine");
    }
}
