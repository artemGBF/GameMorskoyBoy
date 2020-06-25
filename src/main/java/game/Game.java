package game;

import file.FileIO;
import input.Input;
import javafx.util.Pair;
import model.Ship;
import model.ShipList;
import util.CoordinateGenerator;
import validation.Valid;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class Game {
    private ShipList playerShips;
    private ShipList computerShips;

    private String[][] playerGrid;
    private String[][] compGrid;

    private int playerScore = 0;
    private int compScore = 0;

    private int size=0;

    private Set<Pair<Integer, Integer>> compShots = new HashSet<Pair<Integer, Integer>>();

    public static String[][] initField(int size, ShipList a, boolean f) {
        String[][] s = new String[size*a.getLength()][size*a.getLength()];
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[0].length; j++) {
                s[i][j] = "~";
            }
        }
        if(f) {
            for (int i = 0; i < a.getLength(); i++) {
                Ship ship = a.getShip(i);
                s[ship.getxPos()][ship.getyPos()] = "0";
            }
        }
        return s;
    }

    public void init() throws IOException {
        FileIO fileIO = new FileIO("gamesettings.properties");
        Properties read = fileIO.read();
        this.size = Integer.parseInt(read.getProperty("size"))*Integer.parseInt(read.getProperty("count"));
        this.playerShips = new ShipList(Integer.parseInt(read.getProperty("count")),this.size);
        this.computerShips = new ShipList(Integer.parseInt(read.getProperty("count")),this.size);
        this.playerGrid = initField(Integer.parseInt(read.getProperty("size")), this.playerShips,
                Boolean.parseBoolean(read.getProperty("multH")));
        this.compGrid = initField(Integer.parseInt(read.getProperty("size")), this.computerShips,
                Boolean.parseBoolean(read.getProperty("visible")));
        System.out.println("----------------------------------------------");
        System.out.println("Welcome to the BattleShip game -- with a twist");
        System.out.println("----------------------------------------------\n");
        System.out.println("The game will use a grid defined in the setting file");
        System.out.println("Playing grid size set as "+size+"x"+size );
        System.out.println("Maximum number of ships allowed as " + read.getProperty("count"));
        System.out.println("Multiply hits allowed per ship set as " + read.getProperty("multH"));
        System.out.println("Computer ships visible : ON\n");
    }

    public void print(String[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.print("\n");
        }
    }

    public void start() throws IOException {
        init();
        int i = 1;
        while (true) {
            System.out.println("\nBeginning round " + i++);
            System.out.println("Player score: " + playerScore);
            System.out.println("Computer score:" + compScore + "\n");
            System.out.println("Displaying the player grid");
            print(this.playerGrid);
            System.err.println("------------------------------");
            System.out.println("Displaying the computer grid");
            print(this.compGrid);
            if (makeMove()) {
                break;
            }
        }
    }


    public boolean makeMove() throws IOException {
        boolean ind = true;
        int x, y;
        while (ind) {
            System.out.println("Player to make a guess");
            while (true) {
                System.out.println("Ship x position (0-"+(this.size-1)+"): ");
                x = Input.readCoordinate();
                if (Valid.check(x)) {
                    break;
                }
                System.out.println("Incorrect!!!");
            }
            while (true) {
                System.out.println("Ship y position (0-"+(this.size-1)+"): ");
                y = Input.readCoordinate();
                if (Valid.check(y)) {
                    break;
                }
                System.out.println("Incorrect!!!");
            }
            Ship byCoordinates = this.computerShips.getByCoordinates(x, y);
            if (byCoordinates != null) {
                System.out.println("!!!!!PLAYER MAKE A HIT!!!!!");
                playerScore += 10;
                if (byCoordinates.getCurrentHP() > 1) {
                    byCoordinates.setNoOfHitsMade(byCoordinates.getNoOfHitsMade() + 1);
                    this.compGrid[x][y] = "D";
                    ind = true;
                } else {
                    System.out.println("Computer's ship is destroyed");
                    this.compGrid[x][y] = "X";
                    this.computerShips.removeShip(x, y);
                    ind = true;
                }
                if (computerShips.isEmpty()) {
                    System.out.println("\n\nCongratulations!  Player wins");
                    FileIO fileIO = new FileIO("gameoutcome.txt");
                    fileIO.write(true, this.playerScore, this.compScore);
                    return true;
                }
            } else {
                System.out.println("PLAYER MISS");
                this.compGrid[x][y] = "#";
                ind = false;
            }
            if(ind){
                print(this.compGrid);
            }
        }
        ind = true; int x2, y2;
        boolean ind2=true;
        while(true) {
             x2 = new CoordinateGenerator(0, this.size).generate();
             y2 = new CoordinateGenerator(0, this.size).generate();
             Pair<Integer, Integer> p = new Pair<Integer, Integer>(x2,y2);
            if(!this.compShots.contains(p)){
                this.compShots.add(p);
                break;
            }
        }
        while(ind) {
            if(!ind2){
                while(true) {
                    x2 = new CoordinateGenerator(0, this.size).generate();
                    y2 = new CoordinateGenerator(0, this.size).generate();
                    if(!this.compShots.contains(new Pair<Integer, Integer>(x2,y2))){
                        break;
                    }
                }
            }
            System.out.println("Computer to make a guess");
            System.out.println("Computer x position :" + x2);
            System.out.println("Computer y position :" + y2);
            Ship byCoordinates = this.playerShips.getByCoordinates(x2, y2);
            if (byCoordinates != null) {
                System.out.println("!!!!!COMPUTER MAKE A HIT!!!!!");
                compScore += 10;
                if (byCoordinates.getCurrentHP() > 1) {
                    byCoordinates.setNoOfHitsMade(byCoordinates.getNoOfHitsMade() + 1);
                    this.playerGrid[x2][y2] = "D";
                    ind=true;
                } else {
                    this.playerGrid[x2][y2] = "X";
                    this.playerShips.removeShip(x2, y2);
                    ind=true;
                    ind2=false;
                }
                if (playerShips.isEmpty()) {
                    System.out.println("\n\nCongratulations!  Computer wins");
                    FileIO fileIO = new FileIO("gameoutcome.txt");
                    fileIO.write(false, this.playerScore, this.compScore);
                    return true;
                }
            } else {
                System.out.println("COMPUTER MISS");
                this.playerGrid[x2][y2] = "#";
                ind=false;
            }
        }
        return false;
    }

}
