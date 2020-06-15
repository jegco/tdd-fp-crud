package com.example.tddfpcrud;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("api/person")
public class PersonController {

    @GetMapping
    public Flux<Person> list() {
        return Flux.fromIterable(List.of(new Person("1", "jorge caro")));
    }

    @GetMapping("/{id}")
    public Mono<Person> get(@PathVariable String id) {
        return Mono.just(new Person(id, ""));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return Mono.empty();
    }

    @PutMapping
    public Mono<Void> put(@RequestBody Mono<Person> person) {
        return Mono.empty();
    }

    @PostMapping
    public Mono<Void> post(@RequestBody Mono<Person> person) {
        return Mono.empty();
    }
}
