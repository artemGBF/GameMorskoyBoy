package util;

import java.util.Random;

public class CoordinateGenerator {
    private int minValue;
    private int maxValue;

    public CoordinateGenerator(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public int generate(){
        Random r = new Random();
        return r.nextInt(this.maxValue-this.minValue)+this.minValue;
    }
}
