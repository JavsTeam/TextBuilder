package TextGenerator;

import TextGenerator.handlers.Files;
import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;

@JBossLog
@Path("/news")
public class News {
    public static final File NEWS = Files.getFile("news.txt");

    @Inject
    protected Service service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{length}/{depth}")
    public Uni<String> news(@PathParam("length") int length,
                              @PathParam("depth") int depth) {
        if (depth > 2) {
            depth = 4;
        }
        if (length > 100) {
            length = 100;
        }
        log.debug("Current depth of news is " + depth);
        log.debug("Current length of news is " + length);
        return service.source(NEWS, length, depth);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> news() {
        log.debug("No Params news");
        return service.source(NEWS, 10, 1);
    }
}
