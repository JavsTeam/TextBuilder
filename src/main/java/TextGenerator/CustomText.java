package TextGenerator;

import TextGenerator.handlers.Files;
import lombok.extern.jbosslog.JBossLog;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

@JBossLog
@Path("/api")
public class CustomText {
    private static String s = "";
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response file(@Valid String file) {
        s = file;
        File CUSTOMTEXT = Files.getFile(s);
        return Response.ok(new TextBuilder(1, CUSTOMTEXT).invalidateCache().getText(10)).build();
    }

    @Path("/param")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response fileWithParameteres(@Valid String file) {
        s = file;
        String[] ar = file.split(" ");
        File CUSTOMTEXT = Files.getFile(ar[0]);
        int depth = Integer.parseInt(ar[2]);
        int len = Integer.parseInt(ar[1]);
        if (depth > 2) {
            depth = 4;
        }
        if (len > 100) {
            len = 100;
        }
        return Response.ok(new TextBuilder(depth, CUSTOMTEXT).invalidateCache().getText(len)).build();
    }
}
