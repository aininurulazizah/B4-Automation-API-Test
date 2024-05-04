import org.testng.annotations.Test;
import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class APITest {

    @Test
    void test1(){
        // Set header
        RequestSpecification request = RestAssured.given();
        request.header("app-id", "6627151a6cae036629dee799");

        // Send request
        Response response = request.get("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109fb");

        // Get status code and response body
        int status_code = response.getStatusCode();
        String body = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code : " + status_code);
        System.out.println("Response Body : " + body);

        // Assert status code
        Assert.assertEquals(status_code, 200);

        // Validate JSON Schema
        String jsonSchemaPath = "schema.json";
        response.then().assertThat().body(matchesJsonSchemaInClasspath(jsonSchemaPath));
    }

    @Test
    void test2(){
        // Set header
        RequestSpecification request = RestAssured.given();
        request.header("app-id", "6627151a6cae036629dee799");

        // Send request
        Response response = request.get("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109fc");

        // Get status code and response body
        int status_code = response.getStatusCode();
        String body = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code : " + status_code);
        System.out.println("Response Body : " + body);

        // Assert status code
        Assert.assertEquals(status_code, 200);

        // Validate JSON Schema
        String jsonSchemaPath = "schema.json";
        response.then().assertThat().body(matchesJsonSchemaInClasspath(jsonSchemaPath));
    }
}
