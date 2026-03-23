package System;

import ChallengeDecision.ChallengeResource;

import java.util.List;

public interface IAttack {
    /**
     * calculate amount of loot to reward for a successful attack
     *
     * @param attacker village performing the attack
     * @param defender village being attacked
     * @param loot the list containing the loot payout
     * @return List<Double> loot paid out (wood, gold, iron)
     */
    public List<Double> performLootPayout(Village attacker, Village defender, List<ChallengeResource<Double, Double>> loot);
}
