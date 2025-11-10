package com.sleepkqq.sololeveling.ai.service

import com.sleepkqq.sololeveling.ai.mapper.AvroMapper
import com.sleepkqq.sololeveling.ai.model.GenerateTaskResponse
import com.sleepkqq.sololeveling.ai.prompt.TaskPrompts
import com.sleepkqq.sololeveling.avro.task.Task
import org.springframework.ai.chat.client.ChatClient
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service

@Service
class ChatService(
	private val chatClient: ChatClient,
	private val avroMapper: AvroMapper
) {

	@Retryable(maxAttempts = 5, backoff = Backoff(delay = 1000))
	fun generateTask(task: Task): Task {
		val response = chatClient.prompt()
			.user(TaskPrompts.GENERATE_TASK_USER_PROMPT.format(task.topics, task.rarity))
			.call()
			.entity(GenerateTaskResponse::class.java)
			?: throw IllegalArgumentException("GenerateTaskResponse is null")

		return avroMapper.map(response, task)
	}
}
