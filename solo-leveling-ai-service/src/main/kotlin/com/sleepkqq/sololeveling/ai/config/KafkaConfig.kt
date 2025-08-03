package com.sleepkqq.sololeveling.ai.config

import com.sleepkqq.sololeveling.avro.config.DefaultKafkaConfig
import com.sleepkqq.sololeveling.avro.constants.KafkaGroupIds
import com.sleepkqq.sololeveling.avro.task.GenerateTasksEvent
import com.sleepkqq.sololeveling.avro.task.SaveTasksEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Suppress("unused")
@EnableKafka
@Configuration
class KafkaConfig(
	@Value($$"${spring.kafka.bootstrap-servers}") bootstrapServers: String,
	@Value($$"${spring.kafka.properties.schema.registry.url}") schemaRegistryUrl: String
) : DefaultKafkaConfig(bootstrapServers, schemaRegistryUrl) {

	@Bean
	fun producerFactorySaveTasksEvent(): ProducerFactory<String, SaveTasksEvent> =
		createProducerFactory()

	@Bean
	fun kafkaTemplateSaveTasksEvent(
		producerFactory: ProducerFactory<String, SaveTasksEvent>
	): KafkaTemplate<String, SaveTasksEvent> = createKafkaTemplate(producerFactory)

	@Bean
	fun consumerFactoryGenerateTasksEvent(): ConsumerFactory<String, GenerateTasksEvent> =
		createConsumerFactory(KafkaGroupIds.AI_GROUP_ID)

	@Bean
	fun kafkaListenerContainerFactory(
		consumerFactory: ConsumerFactory<String, GenerateTasksEvent>
	): ConcurrentKafkaListenerContainerFactory<String, GenerateTasksEvent> =
		createKafkaListenerContainerFactory(consumerFactory)
}
