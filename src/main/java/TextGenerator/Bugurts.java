package TextGenerator;

import TextGenerator.handlers.Files;
import io.smallrye.mutiny.Uni;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;

@Log
@Path("/bugurts")
public class Bugurts {
    private static final File BUGURTS = Files.getFile("bugurts.txt");

    @Inject
    Service service;

    @SneakyThrows
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{length}/{depth}")
    public Uni<String> bugurts(@PathParam("length") int length,
                               @PathParam("depth") int depth) {
        if (depth > 2) {
            depth = 4;
        }
        log.info("Current depth of bugurts is " + depth);
        log.info("Current length of bugurts is " + length);
        return service.source(BUGURTS, length, depth);
    }
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> bugurts() {
        log.info("No Params bugurts");
        return service.source(BUGURTS, 10, 1);
    }
}
