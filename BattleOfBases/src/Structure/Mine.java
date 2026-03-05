package Structure;
import Unit.*;

abstract public class Mine extends Building {
    private int resourceAmount;
    private Resource resourceType;
    private int maxCollectors;

    // Maybe should have a var for resourceOutput, where for (amount of time) adds resourceAmount + collectors * 5 or something

    private Collector[] collectors;

    public void setMine(int amount, Resource type, int maxCol){
        resourceAmount = amount;
        resourceType = type;
        maxCollectors = maxCol;
    }

    public void setMineLevel(){
        switch (level){
            case 0:
                resourceAmount = 5;
                break;
            case 1:
                resourceAmount = 10;
                break;
            case 2:
                resourceAmount = 20;
                break;
            case 3:
                resourceAmount = 30;
                break;
        }
    }
}
