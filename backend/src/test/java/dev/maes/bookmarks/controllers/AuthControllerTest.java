package dev.maes.bookmarks.controllers;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.maes.bookmarks.repositories.UserRepository;
import dev.maes.bookmarks.security.payload.request.LoginRequest;
import dev.maes.bookmarks.security.payload.request.SignupRequest;
import dev.maes.bookmarks.security.payload.response.JwtResponse;

public class AuthControllerTest extends AbtractControllerTest{

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PasswordEncoder encoder;

    @Test
    @DisplayName("A user can register, login and access to himself")
    public void testRegisterUser() throws Exception {

        String username = "John";
        String password = "password";
        String email = "john@doe.com";

        // SIGNUP
        
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername(username);
        signupRequest.setPassword(password);
        signupRequest.setEmail(email);

        given()
            .contentType("application/json")
            .body(toJson(signupRequest)).
        when()
            .post("/api/auth/signup").
        then()
            .assertThat()
                .statusCode(201);

        // LOGIN

        LoginRequest loginRequest = LoginRequest.builder()
            .username(username)
            .password(password)
            .build();

        JwtResponse response = given()
                .contentType("application/json")
                .body(toJson(loginRequest)).
            when()
                .post("/api/auth/signin")
            .then()
                .assertThat()
                    .body("type", equalTo("Bearer"))
                    .body("username", equalTo(username))
                    .body("email", equalTo(email))
                    .body("roles", hasItem("ROLE_USER"))
                    .statusCode(200)
            .extract()
                .as(JwtResponse.class);
        
        // GET USER
    
        given()
            .header("Authorization", "Bearer " + response.getToken()).
        when()
            .get("/api/users/me").
        then()
            .assertThat()
                .body("username", equalTo(username))
                .body("email", equalTo(email));
    }


    @Test
    @DisplayName("A non-authenticate user can't access to himself")
    public void testNonAuthenticateUser() {
        when()
            .get("/api/users/me").
        then()
            .assertThat()
                .statusCode(401);
    }

    

}
