package com.sleepkqq.sololeveling.ai

import org.springframework.ai.chat.client.ChatClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.kafka.ConfluentKafkaContainer

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class BaseTestClass {

	@MockitoBean
	lateinit var chatClient: ChatClient

	companion object {
		@JvmStatic
		@Container
		val kafkaContainer = ConfluentKafkaContainer("confluentinc/cp-kafka:latest")
	}
}
