package com.example.tddfpcrud;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PersonController.class)
@AutoConfigureWebTestClient
public class PersonControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @SpyBean
    private PersonService service;

    @MockBean
    private PersonRepository repository;

    @Captor
    private ArgumentCaptor<Mono<Person>> captor;

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

    @ParameterizedTest
    @CsvSource({"0", "1"})
    public void put(Integer times) {
        Mono<Person> request;
        when(repository.save(any())).thenReturn(Mono.empty());
        if(times == 0){
           request = Mono.just(new Person("1", "jorge caro"));
            webTestClient.put()
                    .uri("/api/person")
                    .body(request, Person.class)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody().isEmpty();
        }

        else {
            request = Mono.just(new Person( "jorge caro"));
            webTestClient.put()
                    .uri("/api/person")
                    .body(request, Person.class)
                    .exchange()
                    .expectStatus().isBadRequest();
        }
    }

    @ParameterizedTest
    @CsvSource({"jorge caro, 0", "jorge caro2, 1"})
    public void post(String name, Integer times) {
        var request = Mono.just(new Person(name));
        if(times == 0){
            when(repository.findByNombre(name)).thenReturn(Mono.just(new Person(name)));
        }

        if(times == 1){
            when(repository.findByNombre(name)).thenReturn(Mono.empty());
        }

        webTestClient.post()
                .uri("/api/person")
                .body(request, Person.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

        verify(service).insert(captor.capture());
        verify(repository, times(times)).save(any());
        var person = captor.getValue().block();
        Assertions.assertEquals(name, person.getNombre());
    }
}
