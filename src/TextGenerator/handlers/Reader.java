package TextGenerator.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    public static String readTxt(String pathToTxt) {
        StringBuilder text = new StringBuilder();
        try (BufferedReader buf = new BufferedReader(new FileReader(pathToTxt))) {
            String line;
            while ((line = buf.readLine()) != null) {
                String[] words = line.split(" ");
                for (String word : words) {
                    text.append(word + " ");
                }
                text.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    public static String readTxt(File txtFile) {
        return readTxt(txtFile.getPath());
    }
}
