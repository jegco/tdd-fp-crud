package com.example.tddfpcrud;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    public void get() {
        webTestClient.get()
                .uri("/api/person/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Person.class).consumeWith(
                personEntityExchangeResult -> {
                    assert personEntityExchangeResult.getResponseBody() != null;
                }
        );
    }

    @Test
    public void delete() {
        webTestClient.delete()
                .uri("/api/person/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }

    @Test
    public void put() {
        var request = Mono.empty();
        webTestClient.put()
                .uri("/api/person")
                .body(request, Person.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }

    @Test
    public void post() {
        var request = Mono.just(new Person("1", "jorge caro"));
        webTestClient.post()
                .uri("/api/person")
                .body(request, Person.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }
}
