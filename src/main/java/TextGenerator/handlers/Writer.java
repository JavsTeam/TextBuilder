package TextGenerator.handlers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Writer {
    private static final Logger logger =  LogManager.getLogger(Writer.class);
    public static void writeTextTo(String text, String pathToTxt) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToTxt))) {
            logger.trace("Trying to write text...");
            writer.write(text);
        } catch (IOException e) {
            logger.error(e.getMessage() + "\nError happen in the writeTextTo attempt");
        }
    }

    public static void writeTextTo(String text, File txt) {
        writeTextTo(text, txt.getPath());
    }
}
