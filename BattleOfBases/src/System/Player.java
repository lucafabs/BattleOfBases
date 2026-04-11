package System;
public class Player {
    int playerID;
    Village village;
    public Player(int playerID, Village village) {
        this.playerID = playerID;
        this.village = village;
    }
    public int getPlayerID() {
        return playerID;
    }
    public Village getVillage() {
        return village;
    }
}
