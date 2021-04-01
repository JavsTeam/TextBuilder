package TextGenerator.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    private static final Logger logger =  LogManager.getLogger(Reader.class);
    public static String readTxt(String pathToTxt) {
        StringBuilder text = new StringBuilder();
        try (BufferedReader buf = new BufferedReader(new FileReader(pathToTxt))) {
            String line;
            logger.trace("Trying to readTxt...");
            while ((line = buf.readLine()) != null) {
                String[] words = line.split(" ");
                for (String word : words) {
                    text.append(word).append(" ");
                }
                text.append("\n");
            }
        } catch (IOException e) {
            logger.error(e.getMessage() + "\nError happened while trying to readTxt!");
        }
        logger.info("Successfully read!");
        return text.toString();
    }

    public static String readTxt(File txtFile) {
        return readTxt(txtFile.getPath());
    }
}
