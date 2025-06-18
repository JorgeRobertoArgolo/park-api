package com.jorgeroberto.park_api;

import com.jorgeroberto.park_api.web.dto.ParkingSpaceCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/parking/parking-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/parking/parking-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ParkingIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createParking_WithValidData_ReturnsLocationWithStatus201() {
        testClient
                .post()
                .uri("api/v1/parking")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication
                        .getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new ParkingSpaceCreateDto("A-05", "FREE"))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION);
    }

    @Test
    public void createParking_WithCodeAlreadyExisting_ReturnsErrorMessageWithStatus409() {
        testClient
                .post()
                .uri("/api/v1/parking")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication
                        .getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new ParkingSpaceCreateDto("A-01", "FREE"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("status").isEqualTo(409)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/parking");
    }

    @Test
    public void createParking_WithInvalidData_ReturnsErrorMessageWithStatus422() {
        testClient
                .post()
                .uri("/api/v1/parking")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication
                        .getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new ParkingSpaceCreateDto("", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/parking");

        testClient
                .post()
                .uri("/api/v1/parking")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication
                        .getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new ParkingSpaceCreateDto("AAAAA", "FREE"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/parking");

        testClient
                .post()
                .uri("/api/v1/parking")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication
                        .getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new ParkingSpaceCreateDto("A-06", "LIVRE"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/parking");
    }

    @Test
    public void findByCode_WithValidData_ReturnsParkingSpaceWithStatus200() {
        testClient
                .get()
                .uri("/api/v1/parking/{code}", "A-01")
                .headers(JwtAuthentication
                        .getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(10)
                .jsonPath("code").isEqualTo("A-01")
                .jsonPath("status").isEqualTo("FREE");
    }

    @Test
    public void findByCode_WithNonExistingParkingSpace_ReturnsErrorMessageWithStatus200() {
        testClient
                .get()
                .uri("/api/v1/parking/{code}", "A-06")
                .headers(JwtAuthentication
                        .getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("method").isEqualTo("GET")
                .jsonPath("path").isEqualTo("/api/v1/parking/A-06");
    }

    //401
    @Test
    public void findByCode_WithoutPermissionOfAccess_ReturnsErrorMessageWithStatus403() {
        testClient
                .get()
                .uri("/api/v1/parking/{code}", "A-01")
                .headers(JwtAuthentication
                        .getHeaderAuthorization(testClient, "jorge@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo(403)
                .jsonPath("method").isEqualTo("GET")
                .jsonPath("path").isEqualTo("/api/v1/parking/A-01");
    }

    //401
    @Test
    public void createParking_WithoutPermissionOfAccess_ReturnsErrorMessageWithStatus403() {
        testClient
                .post()
                .uri("/api/v1/parking")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication
                        .getHeaderAuthorization(testClient, "jorge@email.com", "123456"))
                .bodyValue(new ParkingSpaceCreateDto("A-06", "BUSY"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo(403)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/parking");
    }
}
