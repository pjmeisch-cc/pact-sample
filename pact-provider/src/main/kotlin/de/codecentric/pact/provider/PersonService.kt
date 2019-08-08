package de.codecentric.pact.provider

import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person
import org.springframework.stereotype.Service

@Service
class PersonService {
    private val fairy = Fairy.create()

    fun findById(id: Int): Person? {
        return fairy.person()
    }
}
