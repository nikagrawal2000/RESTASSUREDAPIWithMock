package api.actions;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import api.pojo.User;
public class UserActions {
	
	public Response createUser(User user) {
        return given().contentType("application/json")
                .body(user).post("/users");
    }

    public Response getUser(int id) {
        return get("/users/" + id);
    }

    public Response updateUser(int id, UserActions user) {
        return given().contentType("application/json")
                .body(user).put("/users/" + id);
    }

    public Response deleteUser(int id) {
        return delete("/users/" + id);
    }

}
