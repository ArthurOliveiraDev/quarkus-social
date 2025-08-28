package io.github.arthurdev.rest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.arthurdev.rest.dto.CreateUserRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class UserResourceTest {

    @Test
    @DisplayName("should create an user successfully")
    public void createUserTest() {
        var user = new CreateUserRequest();
        user.setName("usuario");
        user.setAge(30);

        var response = given()
                .contentType(ContentType.JSON).body(user)
                .when()
                .post("/users")
                .then()
                .extract().response();

                assertEquals(201, response.statusCode());
                assertNotNull(response.jsonPath().getString("id"));
    }
}
