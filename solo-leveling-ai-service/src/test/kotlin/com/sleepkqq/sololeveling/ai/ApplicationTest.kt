package com.sleepkqq.sololeveling.ai

import org.junit.jupiter.api.Test
import org.springframework.ai.chat.client.ChatClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ApplicationTest @Autowired constructor(
	private val chatClient: ChatClient
) {

	@Test
	fun contextLoads() {
		println(chatClient.prompt("Привет! Кто ты и что ты умеешь?").call().content())
	}
}