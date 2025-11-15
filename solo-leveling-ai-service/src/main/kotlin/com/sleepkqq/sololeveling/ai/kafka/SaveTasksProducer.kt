package com.sleepkqq.sololeveling.ai.kafka

import com.sleepkqq.sololeveling.avro.constants.KafkaTaskTopics
import com.sleepkqq.sololeveling.avro.task.SaveTasksEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class SaveTasksProducer(
	private val kafkaTemplate: KafkaTemplate<String, Any>
) {

	private val log = LoggerFactory.getLogger(javaClass)

	fun send(event: SaveTasksEvent) {
		kafkaTemplate.send(KafkaTaskTopics.SAVE_TASKS_TOPIC, event)
		log.info("<< Save tasks event sent | txId={}", event.txId)
	}
}
