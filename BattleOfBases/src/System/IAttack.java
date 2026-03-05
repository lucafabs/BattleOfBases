package System;

public interface IAttack {
    /**
     * measure strength of given village's army
     * @param v
     * @return army strength
     */
    public int calculateAttack(Village v);

    /**
     * measure defense of given village's defenses
     * @param v
     * @return army defense
     */
    public int calculateDefense(Village v);

    /**
     * calculate amount of loot to reward for a successful attack
     * @param attacker village performing the attack
     * @param defender village being attacked
     * @return int[] paid out (wood, gold, iron)
     */
    public int[] performLootPayout(Village attacker, Village defender);
}
