package com.sleepkqq.sololeveling.ai.mapper

import com.sleepkqq.sololeveling.ai.model.GenerateTaskResponse
import com.sleepkqq.sololeveling.avro.task.Task
import org.mapstruct.CollectionMappingStrategy
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.mapstruct.NullValueCheckStrategy
import org.mapstruct.NullValueMappingStrategy
import org.mapstruct.ReportingPolicy
import org.springframework.stereotype.Component

@Component
@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
	nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
	nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
abstract class AvroMapper {

	abstract fun map(response: GenerateTaskResponse, @MappingTarget task: Task): Task
}
