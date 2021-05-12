package TextGenerator;

import TextGenerator.handlers.Files;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;


@RestController
@Log
public class Tester {
    private static final File JUMORESKI = Files.getFile("jumoreski.txt");
    private static final File BUGURTS = Files.getFile("bugurts.txt");

    @GetMapping("/jumoreski")
    public static String jumoreski(@RequestParam(value = "length", defaultValue = "1000") String length,
                                   @RequestParam(value = "depth", defaultValue = "1") String depth) {
        if (Integer.parseInt(depth) > 2) {
            depth = "2";
        }
        TextGenerator textGenerator = new TextGenerator(Integer.parseInt(depth), JUMORESKI);
        log.info("Current depth of jumoreski is " + depth);
        log.info("Current length of jumoreski is " + length);
        return textGenerator.getText(Integer.parseInt(length));
    }
    @GetMapping("/bugurts")
    public static String bugurts(@RequestParam(value = "length", defaultValue = "1000") String length,
                                 @RequestParam(value = "depth", defaultValue = "1") String depth) {
        if (Integer.parseInt(depth) > 2) {
            depth = "2";
        }
        TextGenerator textGenerator = new TextGenerator(Integer.parseInt(depth), BUGURTS);
        log.info("Current depth of bugurts is " + depth);
        log.info("Current length of bugurts is " + length);
        return textGenerator.getText(Integer.parseInt(length));
    }
}
