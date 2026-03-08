package Unit;

public abstract class ArmyMember extends Inhabitant {
    private int attackDamage;
    private float attackRange;
    private DamageType attackType;
    private int hitPoints;

    /**
     * Initialize values for the ArmyMember
     * @param damage amount of damage dealt
     * @param range range of attack
     * @param type type of damage dealt
     * @param hp health
     */
    public void setDamage(int damage, float range, DamageType type, int hp){
        attackDamage = damage;
        attackRange = range;
        attackType = type;
        hitPoints = hp;
    }

    /**
     * @return the attack strength of the given army member
     */
    public int getAttack() {
        //returns the attack strength of the ArmyMember
        return (int)((attackDamage * attackRange) + hitPoints);
    }

    /**
     * Sends the unit into battle
     */
    public void deployUnit() {
        //unit is deployed to battle
        System.out.println("Deploying unit");
    }
}
