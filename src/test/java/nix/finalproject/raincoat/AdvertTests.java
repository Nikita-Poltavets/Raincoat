package nix.finalproject.raincoat;

import nix.finalproject.raincoat.domain.Advert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@TestPropertySource(properties = { "spring.profiles.active=dev" })
class AdvertTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    @Test
    void testContextLoads() {
        assertNotNull(rest);
        assertNotEquals(0, port);
    }


    @Test
    void testGetAdvertByAdvert() {
        String title = "new message";
        String details = "new details";
        String description = "new description";
        boolean job = false;

        var advert = createAdvert(title, details, description, job).getBody();
        assertNotNull(advert);

        long id = advert.getId();

        var advertUrl = baseUrl(id);

        ResponseEntity<Advert> advertResponseEntity = rest
                .getForEntity(advertUrl, Advert.class);
        assertEquals(HttpStatus.OK, advertResponseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, advertResponseEntity.getHeaders().getContentType());

        Advert responseBody = advertResponseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(title, responseBody.getTitle());
        assertEquals(details, responseBody.getDetails());
        assertEquals(description, responseBody.getDescription());
        assertEquals(job, responseBody.isJob());
        assertEquals(id, responseBody.getId());

        assertEquals(responseBody, rest.getForEntity(advertUrl, Advert.class).getBody());
    }

    @Test
    void testCreateInvalidAdvert() {
        ResponseEntity<?> blankTitleResponse = createAdvert("", "details", "description", false);
        assertEquals(HttpStatus.BAD_REQUEST, blankTitleResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, blankTitleResponse.getHeaders().getContentType());

        ResponseEntity<?> blankDetailsResponse = createAdvert("title", "", "description", false);
        assertEquals(HttpStatus.BAD_REQUEST, blankDetailsResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, blankDetailsResponse.getHeaders().getContentType());

        ResponseEntity<?> noTitleAndDetailsAndDescriptionResponse = createAdvert(null, null, null, false);
        assertEquals(HttpStatus.BAD_REQUEST, noTitleAndDetailsAndDescriptionResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, noTitleAndDetailsAndDescriptionResponse.getHeaders().getContentType());
    }

    @Test
    void testUpdateAdvert() {
        String title = "new title";
        String details = "new details";
        String description = "new description";
        boolean job = false;

        var advert = createAdvert(title, details, description, job).getBody();
        assertNotNull(advert);

        long id = advert.getId();

        var advertUrl = baseUrl(id);

        Advert updatedAdvert = new Advert();
        updatedAdvert.setTitle("updated title");
        updatedAdvert.setDetails("updated details");
        updatedAdvert.setDescription("updated description");
        updatedAdvert.setJob(true);

        rest.put(advertUrl, updatedAdvert);

        ResponseEntity<Advert> advertResponseEntity = rest.getForEntity(advertUrl, Advert.class);
        assertEquals(HttpStatus.OK, advertResponseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, advertResponseEntity.getHeaders().getContentType());

        Advert responseBody = advertResponseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals("updated title", responseBody.getTitle());
        assertEquals("updated details", responseBody.getDetails());
        assertEquals("updated description", responseBody.getDescription());
        assertEquals(true, responseBody.isJob());
        assertEquals(id, responseBody.getId());
    }

    @Test
    void testCreateAdvert() {
        var title = "test title";
        var details = "test details";
        var description = "test description";
        var job = false;
        ResponseEntity<Advert> advertResponseEntity = createAdvert(title, details, description, job);

        assertEquals(HttpStatus.CREATED, advertResponseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, advertResponseEntity.getHeaders().getContentType());

        Advert responseBody = advertResponseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(title, responseBody.getTitle());
        assertEquals(details, responseBody.getDetails());
        assertEquals(description, responseBody.getDescription());
        assertEquals(job, responseBody.isJob());

        assertNotNull(responseBody.getId());
    }


    @Test
    public void testDeleteAdvert() {
        var title = "test title";
        var details = "test details";
        var description = "test description";
        var job = false;
        ResponseEntity<Advert> advertResponseEntity = createAdvert(title, details, description, job);

        long id = advertResponseEntity.getBody().getId();

        Advert advert = rest.getForObject(baseUrl(id), Advert.class);
        assertNotNull(advert);
        rest.delete(baseUrl(id));
        try {
            advert = rest.getForObject(baseUrl(id), Advert.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<Advert> createAdvert(String title, String details, String description, boolean job) {
        var url = baseUrl();
        var requestBody = new Advert();
        requestBody.setTitle(title);
        requestBody.setDescription(description);
        requestBody.setDetails(details);
        requestBody.setJob(job);

        return rest.postForEntity(url, requestBody, Advert.class);
    }

    private String baseUrl() {
        return "http://localhost:" + port + "/advert";
    }

    private String baseUrl(Long id) {
        return baseUrl() + '/' + id;
    }

}
