package Structure;

public class ArcherTower extends DefenseBuilding {
    public ArcherTower(){
        name = "Archer Tower";
        setValues(15, Resource.WOOD, 5);
        setAttack(5, 5);
    }
    public Building clone() {
        return new ArcherTower();
    }
}
