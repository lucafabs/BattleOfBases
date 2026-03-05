package Unit;
import Structure.Resource;

public class Archer extends ArmyMember {
    public Archer (){
        setCost(1, 2, Resource.WOOD);
        setDamage(3, 5, DamageType.ARROW, 10);
    }
}
