package Structure;

public class ArcherTower extends DefenseBuilding {
    public ArcherTower(){
        name = "archertower";
        setValues(15, Resource.WOOD, 5, 4);
        setAttack(5, 5);
    }
    public Building clone() {
        return new ArcherTower();
    }
}
