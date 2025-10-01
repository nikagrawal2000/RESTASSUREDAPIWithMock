package api.tests;

import api.actions.UserActions;
import api.mock.MockServerExample;
import api.pojo.User;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.JsonUtils;
import static io.restassured.RestAssured.given;

public class UserAPITest {

	
	private UserActions userActions;
	private static final Logger logger = LoggerFactory.getLogger(UserAPITest.class);

	@BeforeClass
	public void setup() {
		System.out.println("before class setup");
		MockServerExample.startServer(); // Start the mock server
		RestAssured.baseURI = "http://localhost:1080";
		userActions = new UserActions();
		logger.info("Setup complete. Base URI: " + RestAssured.baseURI);

	}

	@AfterClass
	public void teardown() {
		System.out.println("after class teardown");
		MockServerExample.stopServer();
	}



	
	@Test
	public void testValidUserFromJson() throws Exception {

		// 🔽 Log the request and response
    	RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

		logger.info("Starting testValidUserFromJson...");
		User user = JsonUtils.readJsonAsObject("src/test/resources/testdata/valid-user.json", User.class);
		Response response = userActions.createUser(user);

		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertEquals(response.jsonPath().getString("name"), "Valid User");
	}

	@Test
	public void testEmptyUserPayload() throws Exception {
		System.out.println("⚡ Testing testEmptyUserPayload...");
		String payload = JsonUtils.readJsonAsString("src/test/resources/testdata/empty-user.json");
		System.out.println("Payload: " + payload);
		Response response = given().contentType("application/json").body(payload).post("/users");

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertTrue(response.asString().contains("Invalid input"));
	}

	@Test(expectedExceptions = com.fasterxml.jackson.core.JsonParseException.class)
	public void testMalformedUserPayload() throws Exception {
		JsonUtils.readJsonAsObject("src/test/resources/testdata/malformed-user.json", User.class);
	}
}
