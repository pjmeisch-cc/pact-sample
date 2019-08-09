package de.codecentric.pact.consumer

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.Pact
import au.com.dius.pact.consumer.PactFolder
import au.com.dius.pact.consumer.dsl.PactDslJsonBody
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.model.RequestResponsePact
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(PactConsumerTestExt::class, SpringExtension::class)
@ContextConfiguration(classes = [Consumer::class])
@PactFolder("mypacts")
@PactTestFor(providerName = "MyTestProvider")
@DisplayName("a Consumer")
class ConsumerPactTests {

    @MockBean
    lateinit var configuration: ConsumerConfiguration

    @Autowired
    lateinit var consumer: Consumer

    @BeforeEach
    fun beforeEach(mockServer: MockServer) {
        whenever(configuration.host).thenReturn(mockServer.getUrl())
    }

    @Pact(consumer = "MyTestConsumer")
    fun `person 42 exists`(builder: PactDslWithProvider): RequestResponsePact {
        val body = PactDslJsonBody()
                .integerType("id", 42)
                .stringType("firstName")
                .stringType("lastName")
                .asBody()

        return builder
                .given("person found")
                .uponReceiving("retrieve existing person")
                .matchPath("/person/[0-9]+", "/person/42")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(body)
                .toPact()
    }

    @Test
    @PactTestFor(pactMethod = "person 42 exists")
    fun `should retrieve existing Persons`() {
        val person = consumer.getPerson(42)

        assertThat(person).isNotNull
    }

    @Pact(consumer = "MyTestConsumer")
    fun `person 7 does not exist`(builder: PactDslWithProvider): RequestResponsePact {
        return builder
                .given("no person found")
                .uponReceiving("person does not exist")
                .matchPath("/person/[0-9]+", "/person/7")
                .method("GET")
                .willRespondWith()
                .status(404)
                .toPact()
    }

    @Test
    @PactTestFor(pactMethod = "person 7 does not exist")
    fun `should return null on non existing person`() {
        val person = consumer.getPerson(7)

        assertThat(person).isNull()
    }
}
