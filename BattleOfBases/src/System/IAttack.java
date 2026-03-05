package System;
import Structure.*;
import Unit.*;

public interface IAttack {
    /**
     * measure strength of given army
     * @param army
     * @return army strength
     */
    public int calculateAttack(ArmyMember[] army);

    /**
     * measure defense of given defenses
     * @param defense
     * @return army defense
     */
    public int calculateDefense(DefenseBuilding[] defense);

    /**
     * calculate amount of loot to reward for a successful attack
     * @param target village being attacked
     * @return resources[] paid out (wood, gold, iron)
     */
    public Resource[] calculateLootPayout(Village target);
}
