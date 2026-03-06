package Structure;

public class GoldMine extends Mine{
    public GoldMine(){
        name = "Gold Mine";
        setValues(5, Resource.IRON, 7);
        setMine(5, Resource.GOLD, 1);
    }

    public Building clone() {
        return new GoldMine();
    }
}
