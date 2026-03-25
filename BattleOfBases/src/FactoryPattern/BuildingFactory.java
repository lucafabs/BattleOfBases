package FactoryPattern;

import System.Village;
import Structure.Building;
import Structure.Resource;
import Unit.Worker;

public class BuildingFactory {
    // These are all players would need to interface with, so I believe it's all we need?
    public void buildBuilding(Building build, Village v){
        build.build(v);
    }

    public void upgradeBuilding(Building build){
        build.upgrade();
    }

    public void repairBuilding(Building build, Resource type, Worker builder, int cost, float time){
        build.repair(type, builder, cost, time);
    }

}
