package test.java.org.acme.getting.started;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class BugurtsTest {
    @Test
    public void testBugurtsDefaultEndpoint() {
        given()
                .when().get("/bugurts")
                .then()
                .statusCode(200)
                .contentType(MediaType.TEXT_PLAIN);
    }
    @Test
    public void testBugurtsEndpoint() {
        given()
                .when().get("/bugurts/300/2")
                .then()
                .statusCode(200)
                .contentType(MediaType.TEXT_PLAIN);
    }

}
