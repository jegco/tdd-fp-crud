package com.example.tddfpcrud;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("api/person")
public class PersonController {

    @GetMapping
    public Flux<Person> list() {
        return Flux.fromIterable(List.of(new Person("1", "jorge caro")));
    }
}
