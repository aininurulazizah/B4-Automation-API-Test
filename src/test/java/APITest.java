import org.testng.annotations.Test;
import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class APITest {

    // ================= GET Method =================== //
    @Test
    void TC1UnauthorizedGET(){
        // Set header
        RequestSpecification request = RestAssured.given();

        // Send request
        Response response = request.get("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109ca");

        // Get status code and response body
        int status_code = response.getStatusCode();
        String body = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code : " + status_code);
        System.out.println("Response Body : " + body);

        // Assert status code and response body
        Assert.assertEquals(status_code, 403);
        Assert.assertEquals(body, "{\"error\":\"APP_ID_MISSING\"}");

    }

    @Test
    void TC2InvalidAuthorization(){
        // Set header
        RequestSpecification request = RestAssured.given();
        request.header("app-id", "662e5cd6bb70a70ee62595d6");

        // Send request
        Response response = request.get("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109ca");

        // Get status code and response body
        int status_code = response.getStatusCode();
        String body = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code : " + status_code);
        System.out.println("Response Body : " + body);

        // Assert status code and response body
        Assert.assertEquals(status_code, 403);
        Assert.assertEquals(body, "{\"error\":\"APP_ID_NOT_EXIST\"}");

    }

    @Test
    void TC3GetValidUserID(){
        // Set header
        RequestSpecification request = RestAssured.given();
        request.header("app-id", "662e5cd6bb70a70ee62595d5");

        // Send request
        Response response = request.get("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109ca");

        // Get status code and response body
        int status_code = response.getStatusCode();
        String body = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code : " + status_code);
        System.out.println("Response Body : " + body);

        // Assert status code and response body
        Assert.assertEquals(status_code, 200);

        // Validate JSON Schema
        String jsonSchemaPath = "schema.json";
        response.then().assertThat().body(matchesJsonSchemaInClasspath(jsonSchemaPath));

    }

    @Test
    void TC5GETInvalidFormatUserID(){
        // Set header
        RequestSpecification request = RestAssured.given();
        request.header("app-id", "662e5cd6bb70a70ee62595d5");

        // Send request
        Response response = request.get("https://dummyapi.io/data/v1/user/abc");

        // Get status code and response body
        int status_code = response.getStatusCode();
        String body = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code : " + status_code);
        System.out.println("Response Body : " + body);

        // Assert status code and response body
        Assert.assertEquals(status_code, 400);
        Assert.assertEquals(body, "{\"error\":\"PARAMS_NOT_VALID\"}");

    }

    @Test
    void TC6GETNonExistUserID(){
        // Set header
        RequestSpecification request = RestAssured.given();
        request.header("app-id", "662e5cd6bb70a70ee62595d5");

        // Send request
        Response response = request.get("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109cb");

        // Get status code and response body
        int status_code = response.getStatusCode();
        String body = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code : " + status_code);
        System.out.println("Response Body : " + body);

        // Assert status code and response body
        Assert.assertEquals(status_code, 404);
        Assert.assertEquals(body, "{\"error\":\"RESOURCE_NOT_FOUND\"}");

    }

    // ================= POST Method =================== //
    @Test
    void TC3CreateValidRequiredFieldOnly(){
        // Set header and body for POST request
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.header("app-id", "662715096cae038e24dee790");
        String requestBody = "{\"firstName\":\"Adipati\", " +
                "\"lastName\":\"Dolken\", " +
                "\"email\":\"adipati_dolken1234567@gmail.com\"}";

        // Send POST request
        Response response = request.body(requestBody).post("https://dummyapi.io/data/v1/user/create");

        // Get status code and response body
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code : " + statusCode);
        System.out.println("Response Body : " + responseBody);

        // Assert status code and response body
        Assert.assertEquals(statusCode, 200); // Example assertion, adjust as needed

        // Validate JSON Schema
        String jsonSchemaPath = "create-schema.json";
        response.then().assertThat().body(matchesJsonSchemaInClasspath(jsonSchemaPath));
    }

    @Test
    void TC5CreateFirstNameOnly(){
        // Set header and body for POST request
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.header("app-id", "662715096cae038e24dee790");
        String requestBody = "{\"firstName\":\"Adipati\"}";

        // Send POST request
        Response response = request.body(requestBody).post("https://dummyapi.io/data/v1/user/create");

        // Get status code and response body
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code : " + statusCode);
        System.out.println("Response Body : " + responseBody);

        // Assert status code and response body
        String expectedResponseBody = "{\"error\":\"BODY_NOT_VALID\"," +
                "\"data\":{\"lastName\":\"Path `lastName` is required.\"," +
                "\"email\":\"Path `email` is required.\"}}";

        Assert.assertEquals(statusCode, 400); // Example assertion, adjust as needed
        Assert.assertEquals(responseBody, expectedResponseBody);
    }

    @Test
    void TC8CreateAllFieldsValid(){
        // Set header and body for POST request
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.header("app-id", "662715096cae038e24dee790");
        String requestBody = "{\"title\":\"mr\", " +
                "\"firstName\":\"Ten\", " +
                "\"lastName\":\"Lee\", " +
                "\"picture\":\"https://randomuser.me/api/portraits/men/61.jpg\", " +
                "\"gender\":\"male\", " +
                "\"email\":\"ten_lee1234567@gmail.com\", " +
                "\"dateOfBirth\":\"1996-02-27\", " +
                "\"phone\":\"+613372837823\", " +
                "\"location\":{\"street\":\"Wutthakad Road\"," +
                "\"city\":\"Bangkok\", " +
                "\"state\":\"Chomthong\", " +
                "\"country\":\"Thailand\", " +
                "\"timezone\":\"GMT+7\"}}";

        // Send POST request
        Response response = request.body(requestBody).post("https://dummyapi.io/data/v1/user/create");

        // Get status code and response body
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code : " + statusCode);
        System.out.println("Response Body : " + responseBody);

        // Assert status code and response body
        Assert.assertEquals(statusCode, 200);

        // Validate JSON Schema
        String jsonSchemaPath = "create-schema.json";
        response.then().assertThat().body(matchesJsonSchemaInClasspath(jsonSchemaPath));
    }

    @Test
    void TC14CreateWithExistingData(){
        // Set header and body for POST request
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.header("app-id", "662715096cae038e24dee790");
        String requestBody = "{\"title\":\"mr\", " +
                "\"firstName\":\"Ten\", " +
                "\"lastName\":\"Lee\", " +
                "\"picture\":\"https://randomuser.me/api/portraits/men/61.jpg\", " +
                "\"gender\":\"male\", " +
                "\"email\":\"ten_lee123@gmail.com\", " +
                "\"dateOfBirth\":\"1996-02-27\", " +
                "\"phone\":\"+613372837823\", " +
                "\"location\":{\"street\":\"Wutthakad Road\"," +
                "\"city\":\"Bangkok\", " +
                "\"state\":\"Chomthong\", " +
                "\"country\":\"Thailand\", " +
                "\"timezone\":\"GMT+7\"}}";

        // Send POST request
        Response response = request.body(requestBody).post("https://dummyapi.io/data/v1/user/create");

        // Get status code and response body
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code : " + statusCode);
        System.out.println("Response Body : " + responseBody);

        // Assert status code and response body
        String expectedResponseBody = "{\"error\":\"BODY_NOT_VALID\"," +
                "\"data\":{\"email\":\"Email already used\"}}";

        Assert.assertEquals(statusCode, 400);
        Assert.assertEquals(responseBody, expectedResponseBody);
    }

    @Test
    void TC52CreateTimezoneInvalid(){
        // Set header and body for POST request
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.header("app-id", "662715096cae038e24dee790");
        String requestBody = "{\"title\":\"mrs\", " +
                "\"firstName\":\"Angelina\", " +
                "\"lastName\":\"Jolie\", " +
                "\"picture\":\"https://randomuser.me/api/portraits/women/60.jpg\", " +
                "\"gender\":\"female\", " +
                "\"email\":\"angelina_jolie234@gmail.com\", " +
                "\"dateOfBirth\":\"1975-06-04T08:26:49.610Z\", " +
                "\"phone\":\"+91091212292\", " +
                "\"location\":{\"street\":\"S. Roberts Road\"," +
                "\"city\":\"Palos Hills\", " +
                "\"state\":\"Illinois\", " +
                "\"country\":\"United States of America\", " +
                "\"timezone\":\"4\"}}";

        // Send POST request
        Response response = request.body(requestBody).post("https://dummyapi.io/data/v1/user/create");

        // Get status code and response body
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code : " + statusCode);
        System.out.println("Response Body : " + responseBody);

        // Assert status code and response body
        String expectedResponseBody = "{\"error\":\"BODY_NOT_VALID\"}";

        Assert.assertEquals(statusCode, 400);
        Assert.assertEquals(responseBody, expectedResponseBody);
    }

    // ================= UPDATE Method =================== //
    @Test
    public void TC04UpdateIDUserTidakValid() {
        // Prepare request
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.header("app-id", "6627151a6cae036629dee799");

        // Send request to update user with invalid ID
        Response response = request.put("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109dc");

        // Get status code and response body
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);

        // Assert status code and response body
        Assert.assertEquals(statusCode, 404);
        Assert.assertEquals(responseBody, "{\"error\":\"RESOURCE_NOT_FOUND\"}");

        // Validate response against JSON schema
        String jsonSchemaPath = "schema.json";
        response.then().assertThat().body(matchesJsonSchemaInClasspath(jsonSchemaPath));
    }

    @Test
    public void TC06UpdatefirstNameValid() {
        // Prepare request
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.header("app-id", "6627151a6cae036629dee799");

        // Prepare data for update
        String updateData = "{\"firstName\":\"Rachel\"}";

        // Send request to update user with valid ID
        Response response = request.body(updateData)
                .put("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109cc");

        // Get status code and response body
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);

        // Assert status code
        Assert.assertEquals(statusCode, 200);

        // Validate response against JSON schema
        String jsonSchemaPath = "schema.json";
        response.then().assertThat().body(matchesJsonSchemaInClasspath(jsonSchemaPath));
    }

    @Test
    public void TC18UpdatefirstNameInvalid() {
        // Prepare request
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.header("app-id", "6627151a6cae036629dee799");

        // Prepare data for update
        String updateData = "{\"firstName\":123}";

        // Send request to update user with valid ID
        Response response = request.body(updateData)
                .put("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109cc");

        // Get status code and response body
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);

        // Assert status code
        Assert.assertEquals(statusCode, 200);

        // Validate response against JSON schema
        String jsonSchemaPath = "schema.json";
        response.then().assertThat().body(matchesJsonSchemaInClasspath(jsonSchemaPath));
    }

    @Test
    public void TC19UpdatefirstNameKurangDari2Karakter() {
        // Prepare request
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.header("app-id", "6627151a6cae036629dee799");

        // Prepare data for update
        String updateData = "{\"firstName\":\"a\"}";

        // Send request to update user with valid ID
        Response response = request.body(updateData)
                .put("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109cc");

        // Get status code and response body
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);

        // Assert status code
        Assert.assertEquals(statusCode, 200);

        // Validate response against JSON schema
        String jsonSchemaPath = "schema.json";
        response.then().assertThat().body(matchesJsonSchemaInClasspath(jsonSchemaPath));
    }

    @Test
    public void TC48UpdateemailKosong() {
        // Prepare request
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.header("app-id", "6627151a6cae036629dee799");

        // Prepare data for update
        String updateData = "{\"email\":\"\",\"firstName\":\"Rachel\"}";

        // Send request to update user with valid ID
        Response response = request.body(updateData)
                .put("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109cc");

        // Get status code and response body
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);

        // Assert status code
        Assert.assertEquals(statusCode, 200);

        // Validate response against JSON schema
        String jsonSchemaPath = "schema.json";
        response.then().assertThat().body(matchesJsonSchemaInClasspath(jsonSchemaPath));
    }

    // ================= DELETE Method =================== //

    @Test
    void TC1UnauthorizedDelete(){
        // Set header
        RequestSpecification request = RestAssured.given();

        // Send request
        Response response = request.delete("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109cb");

        // Get status code and response body
        int status_code = response.getStatusCode();
        String body = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code : " + status_code);
        System.out.println("Response Body : " + body);

        // Assert status code and response body
        Assert.assertEquals(status_code, 403);
        Assert.assertEquals(body, "{\"error\":\"APP_ID_MISSING\"}");

    }

    @Test
    void TC3DeleteValidUserID(){
        // Set header
        RequestSpecification request = RestAssured.given();
        request.header("app-id", "662e5cd6bb70a70ee62595d5");

        // Send request
        // !! ID User harus diganti dengan id user lain yang belum terhapus di database !!
        Response response = request.delete("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109de");

        // Get status code and response body
        int status_code = response.getStatusCode();
        String body = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code : " + status_code);
        System.out.println("Response Body : " + body);

        // Assert status code and response body
        Assert.assertEquals(status_code, 200);

        // Validate JSON Schema
        String jsonSchemaPath = "schema_id_only.json";
        response.then().assertThat().body(matchesJsonSchemaInClasspath(jsonSchemaPath));

    }

    @Test
    void TC5DeleteInvalidFormatUserID(){
        // Set header
        RequestSpecification request = RestAssured.given();
        request.header("app-id", "662e5cd6bb70a70ee62595d5");

        // Send request
        Response response = request.delete("https://dummyapi.io/data/v1/user/abc");

        // Get status code and response body
        int status_code = response.getStatusCode();
        String body = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code : " + status_code);
        System.out.println("Response Body : " + body);

        // Assert status code and response body
        Assert.assertEquals(status_code, 400);
        Assert.assertEquals(body, "{\"error\":\"PARAMS_NOT_VALID\"}");

    }

    @Test
    void TC6DeleteNonExistUserID(){
        // Set header
        RequestSpecification request = RestAssured.given();
        request.header("app-id", "662e5cd6bb70a70ee62595d5");

        // Send request
        Response response = request.delete("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109cb");

        // Get status code and response body
        int status_code = response.getStatusCode();
        String body = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code : " + status_code);
        System.out.println("Response Body : " + body);

        // Assert status code and response body
        Assert.assertEquals(status_code, 404);
        Assert.assertEquals(body, "{\"error\":\"RESOURCE_NOT_FOUND\"}");

    }

    @Test
    void TC7DeleteEmptyUserID(){
        // Set header
        RequestSpecification request = RestAssured.given();
        request.header("app-id", "662e5cd6bb70a70ee62595d5");

        // Send request
        Response response = request.delete("https://dummyapi.io/data/v1/user/");

        // Get status code and response body
        int status_code = response.getStatusCode();
        String body = response.getBody().asString();

        // Print status code and response body
        System.out.println("Status Code : " + status_code);
        System.out.println("Response Body : " + body);

        // Assert status code and response body
        Assert.assertEquals(status_code, 404);
        Assert.assertEquals(body, "{\"error\":\"PATH_NOT_FOUND\"}");

    }

}
