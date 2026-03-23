package System;
import ModelViewController.*;

import java.util.Scanner;
public class Player {
    int playerID;
    Village village;

    private Scanner scanner;
    private Controller controller;
    private String input;
    public Player(Village village) {
        this.village = village;
        controller = new Controller(this.village);
        controller.start();
    }
}
