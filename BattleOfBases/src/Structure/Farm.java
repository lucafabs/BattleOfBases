package Structure;

public class Farm extends Building{
    public int foodProduced;

    public Farm(){
        name = "farm";
        setValues(5, Resource.WOOD, 5, 8);
        setFarmLevel();
    }

    public Farm(int level) {
        setValues(5, Resource.WOOD, 5, 8);
        this.level = level;
        name = "farm";
        setFarmLevel();
    }
    public void setFarmLevel(){
        switch (level){
            case 0:
                foodProduced = 5;
                break;
            case 1:
                foodProduced = 10;
                break;
            case 2:
                foodProduced = 20;
                break;
            case 3:
                foodProduced = 30;
                break;
        }
    }

    public Building clone() {
        return new Farm();
    }
}
