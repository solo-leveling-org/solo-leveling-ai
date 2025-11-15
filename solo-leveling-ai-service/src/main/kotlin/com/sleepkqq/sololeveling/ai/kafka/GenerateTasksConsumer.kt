package com.sleepkqq.sololeveling.ai.kafka

import com.sleepkqq.sololeveling.ai.service.ChatService
import com.sleepkqq.sololeveling.avro.config.consumer.AbstractKafkaConsumer
import com.sleepkqq.sololeveling.avro.constants.KafkaTaskTopics
import com.sleepkqq.sololeveling.avro.idempotency.IdempotencyService
import com.sleepkqq.sololeveling.avro.task.GenerateTasksEvent
import com.sleepkqq.sololeveling.avro.task.SaveTasksEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GenerateTasksConsumer(
	private val chatService: ChatService,
	private val saveTasksProducer: SaveTasksProducer,
	idempotencyService: IdempotencyService
) : AbstractKafkaConsumer<GenerateTasksEvent>(
	idempotencyService = idempotencyService,
	log = LoggerFactory.getLogger(GenerateTasksConsumer::class.java)
) {

	@Transactional
	@RetryableTopic
	@KafkaListener(
		topics = [KafkaTaskTopics.GENERATE_TASKS_TOPIC],
		groupId = $$"${spring.kafka.avro.group-id}"
	)
	fun listen(event: GenerateTasksEvent) {
		consumeWithIdempotency(event)
	}

	override fun getTxId(event: GenerateTasksEvent): String = event.txId

	override fun processEvent(event: GenerateTasksEvent) {
		log.info(">> Start tasks generation | txId={}", event.txId)
		val generatedTasks = event.inputs.map { chatService.generateTask(it) }

		log.info("<< Tasks successfully generated | txId={}", event.txId)
		saveTasksProducer.send(
			SaveTasksEvent(
				event.txId,
				event.playerId,
				generatedTasks
			)
		)
	}
}
