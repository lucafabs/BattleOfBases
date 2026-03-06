package Structure;

public class IronMine extends Mine{
    public IronMine(){
        name = "Iron Mine";
        setValues(5, Resource.WOOD, 6);
        setMine(5, Resource.IRON, 1);
    }

    public Building clone() {
        return new IronMine();
    }
}
