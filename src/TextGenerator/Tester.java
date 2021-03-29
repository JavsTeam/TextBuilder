package TextGenerator;

import TextGenerator.handlers.FilesHandler;

import java.io.File;

public class Tester {
    private static final File JUMORESKI = FilesHandler.getFile("jumoreski.txt");

    public static void main(String[] args) {
        TextGenerator textGenerator = new TextGenerator(JUMORESKI);
        System.out.println(textGenerator.getText(100));
    }
}
