package TextGenerator.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Cleaner {
    public static final String[] defaultArray = {" ", "\n", "http", "#"};

    public static void deleteFromFileIfContains(String[] forbidden, String pathToTxt) {
        StringBuilder text = new StringBuilder();

        try (BufferedReader buf = new BufferedReader(new FileReader(pathToTxt))) {
            String line;
            while ((line = buf.readLine()) != null) {
                String[] words = line.split(" ");

                outer:
                for (String word : words) {
                    for (String string : forbidden) {
                        if (word.contains(string) || word.isEmpty()) {
                            continue outer;
                        }
                    }
                    text.append(word + " ");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Writer.writeTextTo(text.toString(), pathToTxt);
    }

    public static void deleteFromFileIfContains(String[] forbidden, File txt) {
        deleteFromFileIfContains(forbidden, txt.getPath());
    }

    public static String deleteFromTextIfContains(String[] forbidden, String text) {
        StringBuilder result = new StringBuilder();
        String[] words = text.split(" ");

        outer:
        for (String word : words) {
            for (String string : forbidden) {
                if (word.contains(string) || word.isEmpty()) {
                    continue outer;
                }
            }
            result.append(word + " ");
        }
        return result.toString();
    }
}

