package com.example.tddfpcrud;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PersonController.class)
@AutoConfigureWebTestClient
public class PersonControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void list() {
        webTestClient.get()
                .uri("/api/person")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].name", "jorge caro");
    }
}