package TextGenerator;

import TextGenerator.handlers.Files;
import lombok.extern.java.Log;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;

@Log
@Path("/jumoreski")
public class Jumoreski {
    private static final File JUMORESKI = Files.getFile("jumoreski.txt");
    @Inject
    Service service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{length}/{depth}")
    public String jumoreski(@PathParam int length,
                            @PathParam int depth) {
        if (depth > 2) {
            depth = 4;
        }
        log.info("Current depth of jumoreski is " + depth);
        log.info("Current length of jumoreski is " + length);
        return service.source(JUMORESKI, length, depth);
    }
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String jumoreski() {
        log.info("No Params jumoreski");
        return service.source(JUMORESKI, 10, 1);
    }
}
