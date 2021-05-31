package TextGenerator.handlers;


import lombok.extern.jbosslog.JBossLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


@JBossLog
public class Writer {
    public static void writeTextTo(String text, String pathToTxt) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToTxt))) {
            log.info("Trying to write text...");
            writer.write(text);
        } catch (IOException e) {
            log.warn(e.getMessage() + "\nError happen in the writeTextTo attempt");
        }
    }

    public static void writeTextTo(String text, File txt) {
        writeTextTo(text, txt.getPath());
    }
}
