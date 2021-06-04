package TextGenerator;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import lombok.extern.jbosslog.JBossLog;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.time.Duration;

@ApplicationScoped
@JBossLog
public class Service {
    public Uni<String> source(File source, int len, int depth) {
        log.debug(Service.class.getName() + " is being executed");
        return Uni.createFrom().item(new TextBuilder(depth, source).getText(len)).emitOn(Infrastructure.getDefaultExecutor());
    }
    @Deprecated
    public Multi<String> nonstopJumoreski() {
        log.debug("Non stop jumoreski rolling up!!");
        return Multi.createFrom().ticks().every(Duration.ofSeconds(15))
                .onOverflow().drop()
                .map(x -> new TextBuilder(1, Jumoreski.JUMORESKI).getText(10));
    }
    @Deprecated
    public Multi<String> nonstopBugurts() {
        log.debug("Non stop bugurts rolling up!!");
        return Multi.createFrom().ticks().every(Duration.ofSeconds(15))
                .onOverflow().drop()
                .map(x -> new TextBuilder(1, Bugurts.BUGURTS).getText(10));
    }
}
