package com.example.tddfpcrud;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PersonService {

    public Mono<Void> insert(Mono<Person> person) {
        return Mono.empty();
    }
}
