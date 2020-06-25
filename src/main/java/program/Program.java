package program;

import game.Game;

import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        try {
            Game g = new Game();
            g.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
