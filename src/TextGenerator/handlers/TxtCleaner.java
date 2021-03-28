package TextGenerator.handlers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TxtCleaner {
    public static final String[] defaultArray = {" ", "\n", "http", "#"};
    public static final ArrayList<String> defaultList = new ArrayList<>(Arrays.asList(defaultArray));

    public static void deleteIfContains(List<String> forbidden, String pathToTxt) {
        StringBuilder text = new StringBuilder();

        try (BufferedReader buf = new BufferedReader(new FileReader(pathToTxt))) {
            String line;
            while ((line = buf.readLine()) != null) {
                String[] words = line.split(" ");

                outer:
                for (String word : words) {
                    for (String string : forbidden) {
                        if (word.contains(string)) {
                            continue outer;
                        }
                    }
                    text.append(word + " ");
                }
                text.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Writer.writeTextTo(text.toString(), pathToTxt);
    }

    public static void deleteIfContains(String[] forbidden, String pathToTxt) {
        deleteIfContains(Arrays.asList(forbidden), pathToTxt);
    }
}
