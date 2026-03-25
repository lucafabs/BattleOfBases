package FactoryPattern;
import System.Village;
import Unit.Inhabitant;

public class InhabitantFactory {

    // These are all players would need to interface with, so I believe it's all we need?
    public void createInhabitant(Inhabitant unit, Village v){
        unit.addUnit(v);
    }

    public void upgradeInhabitant(Inhabitant unit){
        unit.upgrade();
    }

    public void deleteInhabitant(Inhabitant unit){
        unit.deleteUnit();
    }
}
