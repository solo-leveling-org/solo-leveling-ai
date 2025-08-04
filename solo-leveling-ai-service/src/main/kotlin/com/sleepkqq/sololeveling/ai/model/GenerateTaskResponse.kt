package com.sleepkqq.sololeveling.ai.model

data class GenerateTaskResponse(
	val title: String,
	val description: String,
	val experience: Int,
	val currencyReward: Int,
	val agility: String,
	val strength: String,
	val intelligence: String
)
