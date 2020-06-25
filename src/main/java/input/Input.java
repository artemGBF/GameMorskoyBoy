package input;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Input {

    public static int readCoordinate() throws FileNotFoundException {
        Scanner s = new Scanner(System.in);
        return s.nextInt();
    }
}
