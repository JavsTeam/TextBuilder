package TextGenerator;

import TextGenerator.handlers.Files;
import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;

@JBossLog
@Path("/jumoreski")
public class Jumoreski {
    public static final File JUMORESKI = Files.getFile("jumoreski.txt");
    @Inject
    protected Service service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{length}/{depth}")
    public Uni<String> jumoreski(@PathParam("length") int length,
                                 @PathParam("depth") int depth) {
        if (depth > 2) {
            depth = 4;
        }
        log.debug("Current depth of jumoreski is " + depth);
        log.debug("Current length of jumoreski is " + length);
        return service.source(JUMORESKI, length, depth);
    }
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> jumoreski() {
        log.debug("No Params jumoreski");
        return service.source(JUMORESKI, 10, 1);
    }
}
