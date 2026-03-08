package Structure;

abstract public class DefenseBuilding extends Building {
    private int attackDamage;
    private float attackRange;

    /**
     * Initialize values for attack & range of the building
     * @param attack the attack stat of the building
     * @param range the range of the building
     */
    public void setAttack(int attack, float range){
        attackDamage = attack;
        attackRange = range;
    }

    /**
     * Returns the defense of the given building
     */
    public int getDefense() {
        return (int)(attackDamage * attackRange);
    }

    /**
     * Upgrade stats for the defense upon level up
     */
    public void setDefenseLevel(){
        switch(level){
            case 0:
                // No need to upgrade default stats
                break;

            case 1:
                attackDamage += 5;
                attackRange += 0.5f;
                break;

            case 2:
                attackDamage += 6;
                attackRange += 0.6f;
                break;

            case 3:
                attackDamage += 9;
                attackRange += 0.9f;
                break;
        }
    }
}
