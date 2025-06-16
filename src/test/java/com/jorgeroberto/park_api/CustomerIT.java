package com.jorgeroberto.park_api;

import com.jorgeroberto.park_api.web.dto.CustomerCreateDto;
import com.jorgeroberto.park_api.web.dto.CustomerResponseDto;
import com.jorgeroberto.park_api.web.dto.PageableDto;
import com.jorgeroberto.park_api.web.exceptions.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/customers/customers-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/customers/customers-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CustomerIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createCustomer_WithValidData_ReturnsCustomerWithStatus201() {
        CustomerResponseDto responseBody = testClient
                .post()
                .uri("api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@email.com", "123456"))
                .bodyValue(new CustomerCreateDto("Toby Oliveira", "61334522006"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CustomerResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId()).isNotNull();
        Assertions.assertThat(responseBody.getName()).isEqualTo("Toby Oliveira");
        Assertions.assertThat(responseBody.getCpf()).isEqualTo("61334522006");
    }

    @Test
    public void createCustomer_WithCpfAlreadyRegistered_ReturnsErrorMessageWithStatus409() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@email.com", "123456"))
                .bodyValue(new CustomerCreateDto("Toby Oliveira", "68788268020"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);

    }

    @Test
    public void createCustomer_WithInvalidData_ReturnsErrorMessageWithStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@email.com", "123456"))
                .bodyValue(new CustomerCreateDto("", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@email.com", "123456"))
                .bodyValue(new CustomerCreateDto("Toby Oliveira", "00000000000"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@email.com", "123456"))
                .bodyValue(new CustomerCreateDto("Toby", "915.049.300-02"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    //Está retornando 401 no lugar de 403
    @Test
    public void createCustomer_WithUserNotAllowed_ReturnsErrorMessageWithStatus403() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new CustomerCreateDto("Ana Sheila Argolo", "91504930002"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

    }

    @Test
    public void findCustomerById_WithExistingIdByAdmin_ReturnsCustomerWithStatus200() {
        CustomerResponseDto responseBody = testClient
                .get()
                .uri("api/v1/customers/20")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId()).isEqualTo(20);
    }

    //Está retornando 401 no lugar de 404
    @Test
    public void findCustomerById_WithNoExistentIdByAdmin_ReturnsErrorMessageWithStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("api/v1/customers/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    //Está retornando 401 no lugar de 403
    @Test
    public void findCustomerById_WithExistingIdByCustomer_ReturnsErrorMessageWithStatus403() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("api/v1/customers/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void findCustomerById_WithPaginationByAdmin_ReturnCustomersWithStatus200() {
        PageableDto responseBody = testClient
                .get()
                .uri("api/v1/customers")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "argolo@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getContent().size()).isEqualTo(2);
        Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
        Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(1);

        responseBody = testClient
                .get()
                .uri("api/v1/customers?size=1&page=1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "argolo@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
        Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
    }

    /*Retornou 401*/
    @Test
    public void findCustomerById_WithPaginationByCustomer_ReturnErrorMessageWithStatus403() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("api/v1/customers")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "test@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void findCustomer_WithCustomerTokenData_ReturnCustomerWithStatus200() {
        CustomerResponseDto responseBody = testClient
                .get()
                .uri("api/v1/customers/details")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getCpf()).isEqualTo("63332538047");
        Assertions.assertThat(responseBody.getName()).isEqualTo("Roberto Argolo");
        Assertions.assertThat(responseBody.getId()).isEqualTo(50);
    }

    //Retornou 401
    @Test
    public void findCustomer_WithAdminTokenData_ReturnCustomerErrorMessageWith403(){
        ErrorMessage responseBody = testClient
                .get()
                .uri("api/v1/customers/details")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }
}
