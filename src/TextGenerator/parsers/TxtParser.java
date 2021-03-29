package TextGenerator.parsers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TxtParser {
    public static String getText(String pathToTxt) {
        StringBuilder text = new StringBuilder();
        try (BufferedReader buf = new BufferedReader(new FileReader(pathToTxt))) {
            String line;
            while ((line = buf.readLine()) != null) {
               text.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }
}
