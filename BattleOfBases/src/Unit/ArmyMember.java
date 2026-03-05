package Unit;

public abstract class ArmyMember extends Inhabitant {
    private int attackDamage;
    private float attackRange;
    private DamageType attackType;
    private int hitPoints;

    public void setDamage(int damage, float range, DamageType type, int hp){
        attackDamage = damage;
        attackRange = range;
        attackType = type;
        hitPoints = hp;
    }

    public int getAttack() {
        //returns the attack strength of the ArmyMember
        return (int)((attackDamage * attackRange) + hitPoints);
    }

    public void deployUnit() {
        //unit is deployed to battle
        System.out.println("Deploying unit");
    }
}
