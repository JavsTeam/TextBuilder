package TextGenerator;


import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;

@ApplicationScoped

public class Service {
    public Uni<String> source(File source, int len, int depth) { return Uni.createFrom().item(new TextBuilder(depth, source).getText(len)).emitOn(Infrastructure.getDefaultExecutor()); }
}
