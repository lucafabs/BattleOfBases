package Structure;

public class GoldMine extends Mine{
    public GoldMine(){
        name = "goldmine";
        setValues(5, Resource.IRON, 7, 8);
        setMine(5, Resource.GOLD, 1);
    }

    public Building clone() {
        return new GoldMine();
    }
}
