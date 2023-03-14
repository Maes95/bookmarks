package dev.maes.bookmarks.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.maes.bookmarks.services.DatabaseInitializer;
import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbtractControllerTest {

    @LocalServerPort
    int port;

    @MockBean
    private DatabaseInitializer databaseInitializer;

    @Autowired
    protected ObjectMapper mapper;

    @BeforeEach
    protected void setUpRestAssured() throws Exception {
        RestAssured.port = port;
    }

    protected String toJson(Object object) throws Exception {
        return mapper.writeValueAsString(object);
    }
    
}
