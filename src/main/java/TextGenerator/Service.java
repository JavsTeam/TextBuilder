package TextGenerator;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;

@ApplicationScoped
public class Service {
    public String source(File source, int len, int depth) {
        return new TextBuilder(depth, source).getText(len);
    }
}
