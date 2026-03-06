package Structure;

public class Cannon extends DefenseBuilding {
    public Cannon(){
        name = "Cannon";
        setValues(25, Resource.IRON, 10, 6);
        setAttack(10, 10);
    }

    public Building clone() {
        return new Cannon();
    }
}
