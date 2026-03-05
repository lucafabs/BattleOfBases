package Structure;

abstract public class DefenseBuilding extends Building {
    private int attackDamage;
    private float attackRange;

    public void setAttack(int attack, float range){
        attackDamage = attack;
        attackRange = range;
    }

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
