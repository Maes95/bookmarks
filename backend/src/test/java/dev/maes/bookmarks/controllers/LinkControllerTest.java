package dev.maes.bookmarks.controllers;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.stream.IntStream;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.maes.bookmarks.configurations.exceptions.UnsavedEntityException;
import dev.maes.bookmarks.entities.Link;
import dev.maes.bookmarks.repositories.LinkRepository;


@DisplayName("LinkController Test")
public class LinkControllerTest extends AbtractControllerTest{

    @Autowired
	public LinkRepository linkRepository;

    JSONObject dummyLink;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() throws Exception {
        dummyLink = new JSONObject().put("url", "http://www.dummy.com/"); 
    }

    @AfterEach
    public void tearDown() throws Exception {
        linkRepository.deleteAll();
    }

    @Nested
    @DisplayName("Test get links")
    class LinkControllerTest_GET {

        @BeforeEach
        public void setUp() throws Exception {
            IntStream.range(0,50).forEach(i -> {
                try {
                    Link link = new Link();
                    link.setUrl("http://www.dummy.com/" + i);
                    linkRepository.save(link);
                } catch (UnsavedEntityException e) {
                    e.printStackTrace();
                }
            });
        }
        
        @Test
        @DisplayName("Test GET /api/links/")
        public void testGetAllLinks() {
            when()
                .get("/api/links/")
            .then()
                .assertThat()
                    .body("content.size()", is(50))
                    .statusCode(200);
        }   

        @Test
        @DisplayName("Test GET /api/links/ Pageable")
        public void testGetAllLinksPageable() {
            when()
                .get("/api/links/?page=0&size=10")
            .then()
                .assertThat()
                    .body("content.size()", is(10))
                    .statusCode(200);
        }   

        @Test
        @DisplayName("Test GET /api/links/{id} - EntityNotFoundException")
        public void testGetLinkById_fail() {
            when()
                .get("/api/links/0")
            .then()
                .statusCode(404);
        }
        
    }

    @Nested
    @DisplayName("Test create links")
    class LinkControllerTest_POST {

        @Test
        @DisplayName("Test POST /api/links/")
        public void testSaveLink() {

            Link link = given().
                contentType("application/json").
                body(dummyLink.toString()).
            when().
                post("/api/links/").
            then().
                assertThat().
                    body("url", equalTo("http://www.dummy.com/")).
                    statusCode(201)
                .extract().as(Link.class);
            
            int id = link.getId();

            when().
                get("/api/links/{id}",id).
            then().
                statusCode(200);
        }

        @Test
        @DisplayName("Test POST /api/links/ - FAIL")
        public void testSaveLink_fail() {

            given().
                contentType("application/json").
                body("{\"url\":\"\"}").
            when().
                post("/api/links/").
            then().
                assertThat().
                    body("message", equalTo("Url may not be empty")).
                    statusCode(400).extract();
        }
    
    }

    @Nested
    @DisplayName("Test update links")
    class LinkControllerTest_PUT {
        
        @Test
        @DisplayName("Test PUT /api/links/{id}")
        public void testSaveLink_fail() throws JsonProcessingException {

            Link link = given().
                contentType("application/json").
                body(dummyLink.toString()).
            when().
                post("/api/links/").
            then()
                .extract().as(Link.class);
            
            link.setUrl("http://www.dummy.com/updated");

            Link linkUpdated = given().
                contentType("application/json").
                body(mapper.writeValueAsString(link)).
            when().
                put("/api/links/").
            then().
                assertThat().
                    body("url", equalTo("http://www.dummy.com/updated")).
                    statusCode(200).
                extract().as(Link.class);
            
            assertEquals(link.getCreated_at(), linkUpdated.getCreated_at());
            assertNotEquals(link.getUpdated_at(), linkUpdated.getUpdated_at());
                    
        }

    }

    @Nested
    @DisplayName("Test delete links")
    class LinkControllerTest_DELETE {

        @Test
        @DisplayName("Test DELETE /api/links/{id}")
        public void testSaveLink() {

            Link link = given().
                contentType("application/json").
                body(dummyLink.toString()).
            when().
                post("/api/links/").
            then()
                .extract().as(Link.class);

            int id = link.getId();

            when()
                .delete("/api/links/" + id).
            then()
                .statusCode(204);

            when()
                .get("/api/links/{id}",id).
            then()
                .statusCode(404);
        }

    }
    
}
