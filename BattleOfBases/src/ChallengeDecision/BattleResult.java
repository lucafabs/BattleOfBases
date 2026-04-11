package ChallengeDecision;

import java.util.List;

public class BattleResult {
    public boolean won;
    public List<Double> loot;
    public String message;

    public BattleResult(boolean won, List<Double> loot, String message) {
        this.won = won;
        this.loot = loot;
        this.message = message;
    }
}
