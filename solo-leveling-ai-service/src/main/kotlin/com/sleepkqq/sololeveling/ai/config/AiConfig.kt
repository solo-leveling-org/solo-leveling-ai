package com.sleepkqq.sololeveling.ai.config

import com.sleepkqq.sololeveling.ai.prompt.TaskPrompts
import org.modelmapper.ModelMapper
import org.springframework.ai.chat.client.ChatClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AiConfig {

	@Bean
	fun modelMapper(): ModelMapper = ModelMapper()

	@Bean
	fun chatClient(builder: ChatClient.Builder): ChatClient = builder
		.defaultSystem(TaskPrompts.GENERATE_TASK_SYSTEM_PROMPT)
		.build()
}
