package com.example.tddfpcrud;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.BiFunction;

@Service
public class PersonService {

    private final BiFunction<PersonRepository, Person, Mono<Person>> validInsert = (repo, person) -> repo.findByNombre(person.getNombre());

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> insert(Mono<Person> person) {
        return person
                .flatMap(value -> validInsert.apply(repository, value))
                .switchIfEmpty(Mono.defer( () -> person.doOnNext(repository::save)))
                .then();
    }

    public Flux<Person> list() {
        return repository.findAll();
    }

    public Mono<Person> get(String id) {
        return repository.findById(id);
    }

    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }

    public Mono<Void> put(Mono<Person> person) {
        return person.map(repository::save).then();
    }
}
