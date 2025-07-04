package com.sleepkqq.sololeveling.ai.service

import com.sleepkqq.sololeveling.ai.mapper.DtoMapper
import com.sleepkqq.sololeveling.ai.model.GenerateTaskResponse
import com.sleepkqq.sololeveling.ai.prompt.TaskPrompts
import com.sleepkqq.sololeveling.avro.task.GenerateTask
import com.sleepkqq.sololeveling.avro.task.SaveTask
import org.springframework.ai.chat.client.ChatClient
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service

@Service
class ChatService(
	private val chatClient: ChatClient,
	private val dtoMapper: DtoMapper
) {

	@Retryable(maxAttempts = 5, backoff = Backoff(delay = 1000))
	fun generateTask(generateTask: GenerateTask): SaveTask {
		val response = chatClient.prompt()
			.user(TaskPrompts.GENERATE_TASK_USER_PROMPT.format(generateTask.topics, generateTask.rarity))
			.call()
			.entity(GenerateTaskResponse::class.java)
			?: throw IllegalArgumentException("GenerateTaskResponse is null")

		return dtoMapper.map(response, generateTask)
	}
}