package TextGenerator;

import TextGenerator.handlers.Cleaner;
import TextGenerator.handlers.FilesHandler;
import TextGenerator.parsers.XslxParser;

import java.io.File;

public class Tester {
    private static final File JUMORESKI = FilesHandler.getFile("jumoreski.txt");
    private static final File BUGURTS = FilesHandler.getFile("bugurts.txt");


    public static void main(String[] args) {
        TextGenerator textGenerator = new TextGenerator(JUMORESKI);
        textGenerator.printText(10);
    }
}
