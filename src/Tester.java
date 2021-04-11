import TextBuilder.TextBuilder;
import TextBuilder.handlers.Files;

import java.io.File;

public class Tester {
    private static final File JUMORESKI = Files.getFile("jumoreski.txt");
    public static final  File BUGURTS = Files.getFile("bugurts.txt");

    public static void main(String[] args) {
       TextBuilder textBuilder = new TextBuilder(1, JUMORESKI);
       textBuilder.printText(10);
    }
}
