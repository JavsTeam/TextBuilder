package TextGenerator;

import TextGenerator.handlers.FilesHandler;

import java.io.File;

public class Tester {
    File jumoreski = FilesHandler.getFile("jumoreski.txt");

    public static void main(String[] args) {

        TextGenerator.Word w = new TextGenerator.Word("a");
        w.addNextWord("b");
        w.addNextWord("b");
        w.addNextWord("b");
        w.addNextWord("c");
        w.addNextWord("c");
        w.addNextWord("e");
        w.addNextWord("e");
        w.addNextWord("f");



        /*
        XslxParser.parseToTxt(
                "src/TextGenerator/data/sources/jumoreski.xlsx",
                "src/TextGenerator/data/lists/jumoreski.txt");

        TxtCleaner.deleteIfContains(
                TxtCleaner.defaultList,
                "src/TextGenerator/data/lists/jumoreski.txt");

        System.out.println(Reader.readTxt(PATH_JUMORESKI_TXT));
         */
    }
}
