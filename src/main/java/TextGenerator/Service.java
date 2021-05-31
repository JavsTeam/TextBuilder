package TextGenerator;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import lombok.extern.jbosslog.JBossLog;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;

@ApplicationScoped
@JBossLog
public class Service {

    public Uni<String> source(File source, int len, int depth) {
        log.debug(Service.class.getName() + " is being executed");
        return Uni.createFrom().item(new TextBuilder(depth, source).getText(len)).emitOn(Infrastructure.getDefaultExecutor());

    }
}
