package System;

import Unit.Upgradeable;

public interface ITimeSystem {
    /**
     * set an object to underconstruction for an amount of time
     * @param timeInSeconds the amount of time to wait
     * @param upgradeable the object being upgraded
     */
    public <T extends Upgradeable> void upgradeTimeout(float timeInSeconds, T upgradeable);

    /**
     * give the village a shield for a given amount of time
     * @param timeInSeconds
     * @param v
     */
    public void shieldTimeout(float timeInSeconds, Village v);
}