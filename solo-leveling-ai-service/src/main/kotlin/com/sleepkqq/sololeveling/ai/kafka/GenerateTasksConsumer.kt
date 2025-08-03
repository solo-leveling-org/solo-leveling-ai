package com.sleepkqq.sololeveling.ai.kafka

import com.sleepkqq.sololeveling.ai.service.ChatService
import com.sleepkqq.sololeveling.avro.constants.KafkaGroupIds
import com.sleepkqq.sololeveling.avro.constants.KafkaTaskTopics
import com.sleepkqq.sololeveling.avro.task.GenerateTasksEvent
import com.sleepkqq.sololeveling.avro.task.SaveTasksEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Suppress("unused")
@Service
class GenerateTasksConsumer(
	private val chatService: ChatService,
	private val saveTasksProducer: SaveTasksProducer
) {

	private val log = LoggerFactory.getLogger(GenerateTasksConsumer::class.java)

	@KafkaListener(
		topics = [KafkaTaskTopics.GENERATE_TASKS_TOPIC],
		groupId = KafkaGroupIds.AI_GROUP_ID
	)
	fun listen(event: GenerateTasksEvent) {
		log.info(">> Start tasks generation | transactionId={}", event.transactionId)
		val generatedTasks = event.inputs.map { chatService.generateTask(it) }

		log.info("<< Tasks successfully generated | transactionId={}", event.transactionId)
		saveTasksProducer.send(
			SaveTasksEvent(
				event.transactionId,
				event.playerId,
				generatedTasks
			)
		)
	}
}
