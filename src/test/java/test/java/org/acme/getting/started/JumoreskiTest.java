package test.java.org.acme.getting.started;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class JumoreskiTest {

    @Test
    public void testJumoreskiDefaultEndpoint() {
        given()
          .when().get("/jumoreski")
          .then()
             .statusCode(200)
             .contentType(MediaType.TEXT_PLAIN);
    }
    @Test
    public void testJumoreskiEndpoint() {
        given()
          .when().get("/jumoreski/300/2")
          .then()
             .statusCode(200)
             .contentType(MediaType.TEXT_PLAIN);
    }

}