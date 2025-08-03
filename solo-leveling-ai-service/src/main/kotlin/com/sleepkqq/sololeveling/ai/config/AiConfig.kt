package com.sleepkqq.sololeveling.ai.config

import com.sleepkqq.sololeveling.ai.prompt.TaskPrompts
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.ChatOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Suppress("unused")
@Configuration
class AiConfig {

	@Bean
	fun chatClient(builder: ChatClient.Builder): ChatClient = builder
		.defaultOptions(
			ChatOptions.builder()
				.temperature(1.2)
				.build()
		)
		.defaultSystem(TaskPrompts.GENERATE_TASK_SYSTEM_PROMPT)
		.build()
}
