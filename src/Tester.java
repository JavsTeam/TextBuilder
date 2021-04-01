import TextGenerator.TextGenerator;
import TextGenerator.handlers.Files;

import java.io.File;

public class Tester {
    private static final File JUMORESKI = Files.getFile("jumoreski.txt");

    public static void main(String[] args) {
        TextGenerator textGenerator = new TextGenerator(JUMORESKI);
        System.out.println(textGenerator.getText(10));
    }
}
