package Unit;

import Structure.Resource;

public class Catapult extends ArmyMember {
    public Catapult (){
        name = "catapult";
        setCost(5, 4, Resource.GOLD);
        setDamage(10, 10, DamageType.CATAPULT, 25);
    }
}
