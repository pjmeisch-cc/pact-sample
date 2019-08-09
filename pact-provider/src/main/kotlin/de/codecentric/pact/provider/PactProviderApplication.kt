package de.codecentric.pact.provider

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct

@SpringBootApplication
class PactProviderApplication(private val personService: PersonService)

fun main(args: Array<String>) {
    runApplication<PactProviderApplication>(*args)
}
