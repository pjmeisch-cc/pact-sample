package de.codecentric.pact.provider

import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person
import org.springframework.stereotype.Service

@Service
class PersonService {
    private val fairy = Fairy.create()

    fun findById(id: Int): MyPerson? {
        throw IllegalArgumentException("I do not want to be called")
        return MyPerson(id, fairy.person())
    }
}

class MyPerson(val id: Int, private val person: Person) {

    val firstName: String get() = person.firstName
    val lastName: String get() = person.lastName
}
