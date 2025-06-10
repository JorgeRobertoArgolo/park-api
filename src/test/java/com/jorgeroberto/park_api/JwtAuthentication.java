package com.jorgeroberto.park_api;

import com.jorgeroberto.park_api.jwt.JwtToken;
import com.jorgeroberto.park_api.web.dto.UserLoginDto;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

//Méthod de autenticação para testes
public class JwtAuthentication {
    public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String username, String password) {
        String token = client
                .post()
                .uri("/api/v1/auth")
                .bodyValue(new UserLoginDto(username, password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody().getToken();
        return header -> header.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}
