package TextGenerator.handlers;

import lombok.extern.jbosslog.JBossLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@JBossLog
public class Reader {
    public static String readTxt(String pathToTxt) {
        StringBuilder text = new StringBuilder();
        try (BufferedReader buf = new BufferedReader(new FileReader(pathToTxt))) {
            String line;
            log.info("Trying to readTxt...");
            while ((line = buf.readLine()) != null) {
                String[] words = line.split(" ");
                for (String word : words) {
                    text.append(word).append(" ");
                }
                text.append("\n");
            }
        } catch (IOException e) {
            log.warn(e.getMessage() + "\nError happened while trying to readTxt!");
        }
        log.trace("Successfully read!");
        return text.toString();
    }

    public static String readTxt(File txtFile) {
        return readTxt(txtFile.getPath());
    }
}
