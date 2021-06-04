package test.java.org.acme.getting.started;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class StoryTest {

    @Test
    public void testStoryDefaultEndpoint() {
        given()
                .when().get("/story")
                .then()
                .statusCode(200)
                .contentType(MediaType.TEXT_PLAIN);
    }
    @Test
    public void testStoryEndpoint() {
        given()
                .when().get("/story/300/2")
                .then()
                .statusCode(200)
                .contentType(MediaType.TEXT_PLAIN);
    }

}