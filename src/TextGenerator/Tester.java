package TextGenerator;

import TextGenerator.parsers.XslxParser;

public class Tester {
    public static void main(String[] args) {
        XslxParser.toTxt(
                "src/TextGenerator/data/sources/jumoreski.xlsx",
                "src/TextGenerator/data/lists/jumoreski.txt");
    }
}
