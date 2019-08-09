package de.codecentric.pact.provider

import au.com.dius.pact.provider.junit.Provider
import au.com.dius.pact.provider.junit.State
import au.com.dius.pact.provider.junit.loader.PactFolder
import au.com.dius.pact.provider.junit5.HttpTestTarget
import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider
import com.devskiller.jfairy.Fairy
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.stubbing.Answer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.MockReset
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PactFolder("../pact-consumer/mypacts")
@Provider("MyTestProvider")
class ContractTests {

    private val fairy = Fairy.create()

    @MockBean(reset = MockReset.AFTER)
    lateinit var personService: PersonService

    // examples often show:
    // @TestTarget
    // val target: Target = SpringBootHttpTarget()
    // but this only works with the JUnit4 Runner, not with JUnit5, so we setup the target ourselves
    @BeforeEach
    fun setupTestTarget(context: PactVerificationContext, @LocalServerPort localServerPort: Int) {
        context.target = HttpTestTarget(port = localServerPort)
    }

    @State("person found")
    fun statePersonFound() {
        whenever(personService.findById(any())).then {
            val id = it.getArgument(0) as Int
            MyPerson(id, fairy.person())
        }
    }

    @State("no person found")
    fun stateNoPersonFound() {
        whenever(personService.findById(any())).thenReturn(null)
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider::class)
    fun `pact verification tests`(context: PactVerificationContext) {
        context.verifyInteraction()
    }
}
