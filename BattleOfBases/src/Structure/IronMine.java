package Structure;

public class IronMine extends Mine{
    public IronMine(){
        name = "ironmine";
        setValues(5, Resource.WOOD, 6, 8);
        setMine(5, Resource.IRON, 1);
    }

    public Building clone() {
        return new IronMine();
    }
}
