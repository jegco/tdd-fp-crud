package com.example.tddfpcrud;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PersonService {

    public Mono<Void> insert(Mono<Person> person) {
        return Mono.empty();
    }

    public Flux<Person> list() {
        return Flux.fromIterable(List.of(new Person("1", "jorge caro")));
    }

    public Mono<Person> get(String id) {
        return Mono.just(new Person(id, ""));
    }

    public Mono<Void> delete(String id) {
        return Mono.empty();
    }

    public Mono<Void> put(Mono<Person> person) {
        return Mono.empty();
    }
}
