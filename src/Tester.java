import TextBuilder.TextBuilder;
import TextBuilder.handlers.Cleaner;
import TextBuilder.handlers.Files;
import TextBuilder.handlers.Reader;

import java.io.File;

public class Tester {
    private static final File JUMORESKI = Files.getFile("jumoreski.txt");
    public static final File BUGURTS = Files.getFile("bugurts.txt");

    public static void main(String[] args) {

        TextBuilder textBuilder = new TextBuilder(2, JUMORESKI).invalidateCache();
        textBuilder.printText(10);
    }
}
