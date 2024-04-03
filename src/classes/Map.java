package classes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Map {
    public Field loadFromFile(String filename) {
        return null;
    }

    public void saveToFile(Field field, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(field.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save field to file", e);
        }
    }
}