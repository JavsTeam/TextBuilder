package TextGenerator;

import TextGenerator.handlers.Files;
import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;

@JBossLog
@Path("/story")
public class Story {
    public static final File STORY = Files.getFile("story.txt");

    @Inject
    protected Service service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{length}/{depth}")
    public Uni<String> story(@PathParam("length") int length,
                                 @PathParam("depth") int depth) {
        if (depth > 2) {
            depth = 4;
        }
        if (length > 100000) {
            length = 100000;
        }
        log.debug("Current depth of story is " + depth);
        log.debug("Current length of story is " + length);
        return service.source(STORY, length, depth);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> story() {
        log.debug("No Params story");
        return service.source(STORY, 10, 1);
    }
}
