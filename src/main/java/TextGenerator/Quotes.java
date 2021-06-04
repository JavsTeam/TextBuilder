package TextGenerator;

import TextGenerator.handlers.Files;
import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;

@JBossLog
@Path("/quotes")
public class Quotes {
    public static final File QUOTES = Files.getFile("quotes.txt");

    @Inject
    protected Service service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{length}/{depth}")
    public Uni<String> quotes(@PathParam("length") int length,
                                 @PathParam("depth") int depth) {
        if (depth > 2) {
            depth = 4;
        }
        if (length > 100) {
            length = 100;
        }
        log.debug("Current depth of quotes is " + depth);
        log.debug("Current length of quotes is " + length);
        return service.source(QUOTES, length, depth);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> quotes() {
        log.debug("No Params quotes");
        return service.source(QUOTES, 10, 1);
    }
}
