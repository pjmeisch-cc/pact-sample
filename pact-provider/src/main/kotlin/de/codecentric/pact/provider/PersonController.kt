package de.codecentric.pact.provider

import com.devskiller.jfairy.producer.person.Person
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/person")
class PersonController(private val personService: PersonService) {

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Int): Person? {
        return personService.findById(id)
    }
}
