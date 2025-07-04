package com.sleepkqq.sololeveling.ai.mapper

import com.sleepkqq.sololeveling.ai.model.GenerateTaskResponse
import com.sleepkqq.sololeveling.avro.task.GenerateTask
import com.sleepkqq.sololeveling.avro.task.SaveTask
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class DtoMapper(
	private val modelMapper: ModelMapper
) {

	fun map(response: GenerateTaskResponse, input: GenerateTask): SaveTask {
		val output = modelMapper.map(response, SaveTask::class.java)
		modelMapper.map(input, output)
		return output
	}
}