package Structure;

public class LumberMill extends Mine{
    public LumberMill(){
        name = "Lumber Mill";
        setValues(5, Resource.WOOD, 5,8);
        setMine(5, Resource.WOOD, 1);
    }

    public Building clone() {
        return new LumberMill();
    }
}
