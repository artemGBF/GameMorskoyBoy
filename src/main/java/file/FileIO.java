package file;

import java.io.*;
import java.util.Properties;

public class FileIO {
    private String fileName;

    public FileIO(String fileName) {
        this.fileName = fileName;
    }

    public Properties read() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(this.fileName));
        return properties;
    }

    public void write(boolean f, int scorePlayer, int scoreComp) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(this.fileName));
        if (!f) {
            bw.write("Computer wins. Final Score Player (" + scorePlayer + ") and Computer (" + scoreComp + ")");
        }
        else{
            bw.write("Player wins. Final Score Player (" + scorePlayer + ") and Computer (" + scoreComp + ")");
        }
        bw.close();
    }
}
