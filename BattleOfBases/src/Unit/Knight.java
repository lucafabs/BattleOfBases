package Unit;

import Structure.Resource;

public class Knight extends ArmyMember {
    public Knight(){
        name = "knight";
        setCost(5, 4, Resource.IRON);
        setDamage(5, 1, DamageType.SWORD, 20);
    }
}
