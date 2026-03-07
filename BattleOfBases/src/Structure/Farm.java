package Structure;

public class Farm extends Building{
    int foodProduced;

    public Farm(){
        name = "farm";
        setValues(5, Resource.WOOD, 5, 8);
    }
    public void setFarmLevel(){
        //attempt to upgrade farm based on materials available
        System.out.println("Setting farm's food production based on level.");
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
