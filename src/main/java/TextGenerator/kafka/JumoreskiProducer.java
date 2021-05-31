package TextGenerator.kafka;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JumoreskiProducer {
    /*
    @Outgoing("jumoreski-out")
    public Multi<String> generate() {
        return Multi.createFrom().ticks().every(Duration.ofSeconds(5))
                .onOverflow().drop()
                .map(x -> new TextBuilder(1, Jumoreski.JUMORESKI).getText(10));
    }

     */
}
