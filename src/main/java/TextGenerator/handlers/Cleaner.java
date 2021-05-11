package TextGenerator.handlers;


import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Log
public class Cleaner {
    public static final String[] defaultArray = {" ", "\n", "http", "#"};

    public static void deleteFromFileIfContains(String[] forbidden, String pathToTxt) {
        StringBuilder text = new StringBuilder();

        try (BufferedReader buf = new BufferedReader(new FileReader(pathToTxt))) {
            String line;
            log.info("Trying to deleteFromFileIfContains...");
            while ((line = buf.readLine()) != null) {
                splitter(forbidden, text, line);
            }
        } catch (IOException e) {
            log.warning(e.getMessage() + "\nError happened while trying to deleteFromFileIfContains!");
        }

        Writer.writeTextTo(text.toString(), pathToTxt);
    }

    private static void splitter(String[] forbidden, StringBuilder text, String line) {
        String[] words = line.split(" ");

        outer:
        for (String word : words) {
            for (String string : forbidden) {
                if (word.contains(string) || word.isEmpty()) {
                    continue outer;
                }
            }
            text.append(word).append(" ");
        }
    }

    public static void deleteFromFileIfContains(String[] forbidden, File txt) {
        deleteFromFileIfContains(forbidden, txt.getPath());
    }

    public static String deleteFromTextIfContains(String[] forbidden, String text) {
        StringBuilder result = new StringBuilder();
        splitter(forbidden, result, text);
        return result.toString();
    }
}

