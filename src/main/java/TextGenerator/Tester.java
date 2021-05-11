package TextGenerator;

import TextGenerator.handlers.Files;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;


@RestController
public class Tester {
    private static final File JUMORESKI = Files.getFile("jumoreski.txt");
    private static final File BUGURTS = Files.getFile("bugurts.txt");

    @GetMapping("/jumoreski")
    public static String jumoreski() {
       TextGenerator textGenerator = new TextGenerator(1, JUMORESKI);
       return textGenerator.getText(1000);
    }
    @GetMapping("/bugurts")
    public static String bugurts() {
        TextGenerator textGenerator = new TextGenerator(1, BUGURTS);
        return textGenerator.getText(1000);
    }
}
