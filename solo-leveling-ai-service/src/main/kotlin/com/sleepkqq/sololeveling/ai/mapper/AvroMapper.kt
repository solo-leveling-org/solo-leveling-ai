package com.sleepkqq.sololeveling.ai.mapper

import com.sleepkqq.sololeveling.ai.extensions.toByteBuffer
import com.sleepkqq.sololeveling.ai.model.GenerateTaskResponse
import com.sleepkqq.sololeveling.avro.task.GenerateTask
import com.sleepkqq.sololeveling.avro.task.SaveTask
import org.mapstruct.CollectionMappingStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named
import org.mapstruct.ReportingPolicy
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.nio.ByteBuffer


@Component
@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
abstract class AvroMapper {

	@Named("toByteBuffer")
	fun map(input: BigDecimal): ByteBuffer = input.toByteBuffer()

	@Mapping(
		target = "currencyReward",
		source = "response.currencyReward",
		qualifiedByName = ["toByteBuffer"]
	)
	abstract fun map(response: GenerateTaskResponse, input: GenerateTask): SaveTask
}
