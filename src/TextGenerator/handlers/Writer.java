package TextGenerator.handlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    public static void writeTextTo(String text, String pathToTxt) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToTxt))) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}