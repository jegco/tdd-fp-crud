package com.example.tddfpcrud;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/person")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<Person> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public Mono<Person> get(@PathVariable String id) {
        return service.get(id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return service.delete(id);
    }

    @PutMapping
    public Mono<Void> put(@RequestBody Mono<Person> person) {
        return service.put(person);
    }

    @PostMapping
    public Mono<Void> post(@RequestBody Mono<Person> person) {
        return service.insert(person);
    }
}
