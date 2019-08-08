package de.codecentric.pact.consumer

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

@ConfigurationProperties("consumer.config")
class ConsumerConfiguration {
    /** the host to connect to woth protocol and port */
    var host = "http://unknown.host:1234"
}

@Component
@EnableConfigurationProperties(ConsumerConfiguration::class)
class Consumer(
        @Suppress("SpringJavaInjectionPointsAutowiringInspection")
        private val configuration: ConsumerConfiguration
) {
    fun getPerson(id: Int): Person? {
        return try {
            RestTemplate().getForEntity<Person>("${configuration.host}/person/$id", Person::class).body
        } catch (e: RestClientException) {
            when (e) {
                is HttpClientErrorException.NotFound -> null
                else -> throw e
            }
        }
    }
}
