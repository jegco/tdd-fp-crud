package com.example.tddfpcrud;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
public class PersonService {

    private final BiFunction<PersonRepository, Person, Mono<Person>> validInsert = (repo, person) -> repo.findByNombre(person.getNombre());

    private final Function<Person, Mono<Person>> validUpdate = (person) -> person.getId() == null || person.getId().isEmpty() ? Mono.error(new Exception()) : Mono.just(person);

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
        return person
                .flatMap(validUpdate)
                .doOnError(Mono::error)
                .map(repository::save).then();
    }
}
