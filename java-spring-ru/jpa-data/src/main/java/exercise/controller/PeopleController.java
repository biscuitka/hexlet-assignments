package exercise.controller;

import exercise.model.Person;
import exercise.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path = "/{id}")
    public Person show(@PathVariable long id) {
        return personRepository.findById(id).get();
    }

    // BEGIN
    @GetMapping()
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteById(@PathVariable Long id) {
        if (personRepository.findById(id).isPresent()) {
            personRepository.deleteById(id);
        }
    }
    // END
}
