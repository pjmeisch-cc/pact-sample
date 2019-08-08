package de.codecentric.pact.provider

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct

@SpringBootApplication
class PactProviderApplication(private val personService: PersonService) {

    @PostConstruct
    fun setup() {
        val person = personService.findById(7)
        System.err.println("${person?.firstName} ${person?.lastName}")
    }
}
fun main(args: Array<String>) {
    runApplication<PactProviderApplication>(*args)
}
