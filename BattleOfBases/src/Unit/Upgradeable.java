package Unit;
import Structure.Resource;

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
