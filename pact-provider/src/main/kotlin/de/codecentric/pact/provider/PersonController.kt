package de.codecentric.pact.provider

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/person")
class PersonController(private val personService: PersonService) {

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Int): ResponseEntity<MyPerson> {
        val myPerson = personService.findById(id)
        println("found $myPerson")
        return myPerson?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
    }
}
