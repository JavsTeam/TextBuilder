package TextGenerator;

import TextGenerator.handlers.Reader;
import TextGenerator.handlergit add -As.TxtCleaner;
import TextGenerator.parsers.XslxParser;

public class Tester {
    private static final String PATH_JUMORESKI_TXT = "src/TextGenerator/data/lists/jumoreski.txt";

    public static void main(String[] args) {
        /*
        XslxParser.toTxt(
                "src/TextGenerator/data/sources/jumoreski.xlsx",
                "src/TextGenerator/data/lists/jumoreski.txt");

        TxtCleaner.deleteIfContains(
                TxtCleaner.defaultList,
                "src/TextGenerator/data/lists/jumoreski.txt");

         */

        System.out.println(Reader.readTxt(PATH_JUMORESKI_TXT));
    }
}
