package Unit;
import Structure.Resource;

//this abstract class includes all of the shared variables across upgradeable objects (units & buildings)
public abstract class Upgradeable {
    public int costToMake;
    public Resource resourceNeeded;
    public int level;
    public int maxLevel = 3;
    public String name;
    public boolean underConstruction;
    public float upgradeTime = 10f;
    public abstract void upgrade();
}
