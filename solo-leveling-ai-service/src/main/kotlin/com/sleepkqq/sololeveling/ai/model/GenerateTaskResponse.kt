package com.sleepkqq.sololeveling.ai.model

data class GenerateTaskResponse(
	val title: String,
	val description: String,
	val experience: String,
	val agility: String,
	val strength: String,
	val intelligence: String
)