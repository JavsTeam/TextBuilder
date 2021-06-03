package test.java.org.acme.getting.started;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class QuotesTest {
    @Test
    public void testQuotesDefaultEndpoint() {
        given()
                .when().get("/quotes")
                .then()
                .statusCode(200)
                .contentType(MediaType.TEXT_PLAIN);
    }
    @Test
    public void testQuotesEndpoint() {
        given()
                .when().get("/quotes/300/2")
                .then()
                .statusCode(200)
                .contentType(MediaType.TEXT_PLAIN);
    }
}
