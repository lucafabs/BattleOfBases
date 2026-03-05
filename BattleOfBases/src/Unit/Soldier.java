package Unit;

import Structure.Resource;

public class Soldier extends ArmyMember{
    public Soldier (){
        setCost(1, 2, Resource.WOOD);
        setDamage(2, 1, DamageType.ARROW, 15);
    }
}
