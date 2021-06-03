package test.java.org.acme.getting.started;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class NewsTest {

    @Test
    public void testNewsDefaultEndpoint() {
        given()
                .when().get("/news")
                .then()
                .statusCode(200)
                .contentType(MediaType.TEXT_PLAIN);
    }
    @Test
    public void testNewsEndpoint() {
        given()
                .when().get("/news/300/2")
                .then()
                .statusCode(200)
                .contentType(MediaType.TEXT_PLAIN);
    }


}