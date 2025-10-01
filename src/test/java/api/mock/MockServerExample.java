package api.mock;

import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.mockserver.matchers.MatchType;


import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import org.mockserver.model.RegexBody;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class MockServerExample {


    private static ClientAndServer mockClientServer;  // MockServer object
    private static final int PORT = 1080;       // Port for MockServer

    // 🔒 Private constructor - prevents external instantiation
    private MockServerExample() {
      
    }


   public static synchronized void startServer() {
    if (mockClientServer == null || !mockClientServer.isRunning()) {
        mockClientServer = ClientAndServer.startClientAndServer(1080);
        System.out.println("Starting MockServer on port 1080...");
        createStubs();
    }
    else{
         System.out.println("⚡ MockServer already running on port " + PORT);
    }
}

    private static void createStubs() {
        // Stub for valid user creation
        mockClientServer
            .when(
                HttpRequest.request()
                    .withMethod("POST")
                    .withPath("/users")
                    .withBody(JsonBody.json(
                        "{ \"id\": 1, \"name\": \"Valid User\", \"email\": \"valid@example.com\", \"address\": \"123 Valid St\", \"phone\": \"123-456-7890\" }",
                        MatchType.ONLY_MATCHING_FIELDS
                    ))
            )
            .respond(
                HttpResponse.response()
                    .withStatusCode(201)
                    .withHeader("Content-Type", "application/json")
                    .withBody("{ \"message\": \"User created successfully\" }")
            );

        // Stub for empty payload - returns 400
		mockClientServer.when(
		        HttpRequest.request()
		                .withMethod("POST")
		                .withPath("/users")
		                .withBody(JsonBody.json("{}", MatchType.STRICT))
		).respond(
		        HttpResponse.response()
		                .withStatusCode(400)
		                .withHeader("Content-Type", "application/json")
		                .withBody("{ \"error\": \"Invalid input\" }")
		);

        // invalid email format stub
        mockClientServer.when(
            request()
                .withMethod("POST")
                .withPath("/users")
                .withBody(
                    new RegexBody(".*\"email\"\\s*:\\s*\"[^\"]+[^@][^\"]*\".*")
                )
        ).respond(
            response()
                .withStatusCode(422)
                .withHeader("Content-Type", "application/json")
                .withBody("{ \"error\": \"Invalid email\" }")
        );

        System.out.println("✅ MockServer running on port 1080...");
    }

    public static void stopServer() {
        if (mockClientServer != null && mockClientServer.isRunning()) {
            System.out.println("attempting to stop MockServer...");
            mockClientServer.stop();
            mockClientServer.close();  // forcefully close Netty resources
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }  // give OS time to release port
            mockClientServer = null;
            System.out.println("MockServer stopped on port 1080.");
            
        }
        else {
            System.out.println("MockServer is not running currently, hence nothing is stopped.");
        }
    }

    public static void main(String[] args) {
        // Start and stop the MockServer for demonstration
        MockServerExample.startServer();

         // Now send a test POST request to see if it matches the expectation
    System.out.println("Sending test request to /users...");

    String jsonBody = "{\n" +
            "  \"id\": 1,\n" +
            "  \"name\": \"Valid User\",\n" +
            "  \"email\": \"valid@example.com\",\n" +
            "  \"address\": \"123 Valid St\",\n" +
            "  \"phone\": \"123-456-7890\"\n" +
            "}";

    Response response = RestAssured
            .given()
                .baseUri("http://localhost")
                .port(1080)
                .contentType(ContentType.JSON)
                .body(jsonBody)
            .when()
                .post("/users");

    System.out.println("Status Code: " + response.getStatusCode());
    System.out.println("Response Body: " + response.getBody().asString());

       MockServerExample.stopServer();
    }

}
