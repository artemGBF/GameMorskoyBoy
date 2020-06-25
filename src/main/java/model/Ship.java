package model;

import util.CoordinateGenerator;

public class Ship {
    private String shipName;
    private int xPos;
    private int yPos;
    private int noOfHitsMade;
    private int noOfHitsNedeed;

    public Ship(String shipName, int size) {
        this.shipName = shipName;
        this.xPos = new CoordinateGenerator(0,size).generate();
        this.yPos = new CoordinateGenerator(0,size).generate();
        this.noOfHitsMade = 0;
        this.noOfHitsNedeed = new CoordinateGenerator(1,5).generate();
    }

    public int getCurrentHP(){
        return this.noOfHitsNedeed-this.noOfHitsMade;
    }

    public Ship() {
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getNoOfHitsMade() {
        return noOfHitsMade;
    }

    public void setNoOfHitsMade(int noOfHitsMade) {
        this.noOfHitsMade = noOfHitsMade;
    }

    public int getNoOfHitsNedeed() {
        return noOfHitsNedeed;
    }

    public void setNoOfHitsNedeed(int noOfHitsNedeed) {
        this.noOfHitsNedeed = noOfHitsNedeed;
    }
}
