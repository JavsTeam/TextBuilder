import TextGenerator.TextGenerator;
import TextGenerator.handlers.Files;

import java.io.File;

public class Tester {
    private static final File JUMORESKI = Files.getFile("jumoreski.txt");
    private static final File BUGURTS = Files.getFile("bugurts.txt");

    public static void main(String[] args) {
       TextGenerator textGenerator = new TextGenerator(1, JUMORESKI);
       textGenerator.printText(1000);
    }
}
